package org.example.netstore.nettyserver.services.store;

import org.example.netstore.common.dto.UserDto;
import org.example.netstore.common.dto.storage.FileInfoDto;
import org.example.netstore.common.protocol.requests.storage.MkdirRequest;
import org.example.netstore.nettyserver.PropertiesHolder;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class SimpleStorageService implements StorageService {
    private static final int chunkSize = 64 * 1024;
    private static final String ROOT;
    private static final Path USERS;
    private static final Path TEMP;

    private final Path USER_HOME;
    private final UserDto user;

    static {
        try {
            ROOT = PropertiesHolder.properties.getProperty("storage.root");
            USERS = Path.of(ROOT, "users");
            TEMP = Path.of(ROOT, "temp");
            checkRootFolders(USERS, TEMP);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static void checkRootFolders(Path... paths) throws IOException {
        for (Path p : paths) {
            if(!Files.exists(p)){
                Files.createDirectories(p);
            }
        }
    }
    public SimpleStorageService(UserDto user) throws IOException {
        this.user = user;
        this.USER_HOME = USERS.resolve(String.valueOf(user.getId()));
        checkUserFolders();
    }
    private void checkUserFolders() throws IOException {
        Path userTemp = TEMP.resolve(String.valueOf(this.user.getId()));
        if(!Files.exists(USER_HOME)){
            Files.createDirectories(USER_HOME);
        }
        if(!Files.exists(userTemp)){
            Files.createDirectories(userTemp);
        }
    }
    public List<FileInfoDto> dir(String path) {
        try (Stream<Path> pathStream = Files.list(USER_HOME.resolve(path))) {
            return pathStream
                    .map(p -> new FileInfoDto(
                            USER_HOME.relativize(p).toString(),
                            p.toFile().length(),
                            p.toFile().isDirectory(),
                            USER_HOME.relativize(p.getParent()).toString())
                    )
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public FileInfoDto mkDir(MkdirRequest request) {
        Path path = USER_HOME.resolve(request.getPath());   // TODO: Remove leading slashes (Avoid gobacks)
        if(Files.exists(path)){
            throw new RuntimeException("Directory is already exist.");
        }
        try {
            return new FileInfoDto(Files.createDirectories(path).toFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getChunkSize() {
        return chunkSize;
    }

    @Override
    public byte[] getChunk(String path, long offset) {
        Path p = USER_HOME.resolve(path);
        try (RandomAccessFile raf = new RandomAccessFile(p.toString(), "r")){
            long partSize = Files.size(p) - offset;
            byte[] bytes = new byte[partSize < chunkSize ? (int) partSize : chunkSize];
            raf.seek(offset);
            raf.read(bytes);
            return bytes;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long writeChunk(String path, long offset, byte[] chunk) {
        Path p = USER_HOME.resolve(path);
        if(!Files.exists(p.getParent())){
            try {
                Files.createDirectories(p);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try (RandomAccessFile raf = new RandomAccessFile(p.toFile(), "rw")){
            raf.seek(offset);
            raf.write(chunk);
            return chunk.length;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean checkWriteSize(long storeSize) {
        if(this.user.isQuotaEnabled() && this.user.getQuotaRemains() < storeSize){
            return false;
        }
        return new File(ROOT).getFreeSpace() >= storeSize;
    }


}
