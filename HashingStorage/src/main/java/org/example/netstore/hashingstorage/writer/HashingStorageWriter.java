package org.example.netstore.hashingstorage.writer;

import org.example.netstore.hashingstorage.AbstractStorage;
import org.example.netstore.hashingstorage.TempFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HashingStorageWriter extends AbstractStorage implements ChunkedStorageWriter {
    // TODO: Make annotation driven injection of configurable fields.
    private static final Path temps = root.resolve("temps"); // TODO: Move to config
    private static final Path users = root.resolve("users"); // TODO: Move to config
    private static final Map<String, TempFile> tempFiles = new ConcurrentHashMap<>(); // TODO: Implement forced cleanup.
    private final TempWriter tempWriter;
    private final HashingWriter hashingWriter;
    private final long userId;
    private final Path userHome;

    static {
        try {
            checkFolder(temps);
            checkFolder(users);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /***
     * @param tempWriter Temp file writer
     * @param hashingWriter Hashing writer
     * @param userId User Id
     */
    public HashingStorageWriter(TempWriter tempWriter, HashingWriter hashingWriter, long userId) {
        this.userId = userId;
        this.userHome = users.resolve(String.valueOf(userId));
        this.tempWriter = tempWriter;
        this.hashingWriter = hashingWriter;
    }

    /***
     * Writes data chunk in existing temp file found by path from given offset.
     * If temp file is complete (actual size = target size) runs hashing writer
     * for calculate hash, move file and create link. Then removes completed temp from map.
     * @param path target path
     * @param offset file offset
     * @param chunk data chunk
     * @throws IOException
     */
    @Override
    public void write(String path, long offset, byte[] chunk) throws IOException {
        TempFile tempFile = tempFiles.get(path);
        if(tempFile == null){
            throw new FileNotFoundException("USE 'requestWrite' method first!!!");
        }
        tempWriter.write(tempFile, offset, chunk);
        if(tempFile.isCompleted()){
            hashingWriter.write(tempFile.getLocation(), tempFile.getForFile());
            tempFiles.remove(path);
        }
    }

    /***
     * Checks if temp file for target path exist, creates if not.
     * Returns current size of the temp file as write offset.
     * @param path target file path
     * @param size file size
     * @return current size of the temp file
     * @throws IOException
     */
    @Override
    public long requestWrite(String path, long size) throws IOException {
        TempFile t = tempFiles.get(path);
        if(t == null){
            t = tempWriter.CreateTempFile(temps.resolve(String.valueOf(userId)), userHome.resolve(path), size);
            tempFiles.put(path, t);
        }
        return t.getActualSize();
    }

    /***
     * Removes temp file from storage and map in order to cancel writing.
     * @param path target path
     * @throws IOException
     */
    @Override
    public void cancelWrite(String path) throws IOException {
        TempFile tempFile = tempFiles.get(path);
        if(tempFile == null){
            return;
        }
        Files.delete(tempFile.getLocation());
        tempFiles.remove(path);
    }

    /***
     * Deletes not completed temp files with last access time before param.
     * @param olderThan past datetime
     */
    public static void deleteLostTemps(LocalDateTime olderThan){
        tempFiles.forEach((key, value) -> {
            if (value.getLastAccess().isBefore(olderThan)) {
                try {
                    Files.delete(value.getLocation());
                    tempFiles.remove(key);
                } catch (IOException e) {
                    throw new RuntimeException("Cannot delete old temp file - %s".formatted(value.getLocation().toString()));
                }
            }
        });
    }
}
