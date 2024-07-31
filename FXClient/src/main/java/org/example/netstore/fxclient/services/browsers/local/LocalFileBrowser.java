package org.example.netstore.fxclient.services.browsers.local;

import org.example.netstore.common.dto.storage.FileInfoDto;
import org.example.netstore.fxclient.services.browsers.FileBrowser;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

public class LocalFileBrowser implements FileBrowser {
    private String actualPath = "";

    /**
     * Returns directory list
     * Uses FileInfoDto as comparableWrapper
     *
     * @param path
     * @param recursive
     * @return List<? extends File>
     */
    @Override
    public List<? extends File> dir(String path, boolean recursive) {
        if((path == null || path.isBlank()) && actualPath.isBlank()){
            return Arrays.stream(File.listRoots())
                    .map(FileInfoDto::new)
                    .toList();
        }
        if (recursive) {
            return getRecursiveDir(path);
        } else {
            actualPath = Path.of(actualPath).resolve(path).toString();
            Path actual = Path.of(actualPath);
            try (Stream<Path> pathStream = Files.list(actual)) {
                return pathStream
                        .map(this::getRelativePathFileInfo)
                        .toList();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    /**
     * Dir from actual path.
     * If actual is blank, returns roots list from extended method
     */
    @Override
    public List<? extends File> dir() {
        if(actualPath.isBlank()){
            return dir("", false);
        }
        return dir(actualPath, false);
    }

    /**
     * Gets recursive files list from given directory.
     */
    private List<? extends File> getRecursiveDir(String path) {
        List<FileInfoDto> files = new ArrayList<>();
        try {
            Files.walkFileTree(Path.of(actualPath).resolve(Path.of(path)), new SimpleFileVisitor<>(){
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    files.add(getRelativePathFileInfo(file));
                    return super.visitFile(file, attrs);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return files;
    }


    @Override
    public void copy(String from, String to) {

    }

    @Override
    public void move(String from, String to) {

    }

    @Override
    public void delete(String path) {

    }

    /**
     * Makes directory relative to actual path
     * @param path new directory path
     * @return file represents created directory
     * @throws IOException
     */
    @Override
    public File mkDir(String path) throws IOException {
        if(path == null || path.isBlank()){
            return null;
        }
        Path dir = Path.of(actualPath, path);
        if(Files.exists(dir)){
            throw new FileAlreadyExistsException("Directory is already exist.");
        }
        return Files.createDirectories(dir).toFile();
    }

    @Override
    public List<? extends File> back() {
        String path = actualPath == null || actualPath.isBlank() || Path.of(actualPath).getParent() == null ?
                "" : Path.of(actualPath).getParent().toString();
        return dir(path, false);
    }

    @Override
    public InputStream getInputStream(File localFile) throws Exception {
        Path p = Path.of(actualPath).resolve(localFile.toPath());
        return new BufferedInputStream(new FileInputStream(p.toFile()));
    }

    @Override
    public OutputStream getOutputStream(File remoteFile) throws IOException {
        Path parentDir = Path.of(actualPath, remoteFile.getParent() == null ? "" : remoteFile.getParent());
        if(!Files.exists(parentDir)){
            Files.createDirectories(parentDir);
        }
        return new FileOutputStream(Path.of(actualPath).resolve(remoteFile.getPath()).toFile());
    }

    @Override
    public long size(String path) {
        Path p = Path.of(actualPath).resolve(path);
        try {
            if(Files.isDirectory(p)){
                return getDirSize(p);
            }
            return Files.size(p);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private long getDirSize(Path p) throws IOException {
        AtomicReference<Long> size = new AtomicReference<>(0L);
        Files.walkFileTree(p, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if(!Files.isDirectory(file)){
                    size.getAndUpdate(aLong -> {
                        try {
                            return aLong + Files.size(file);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
                return FileVisitResult.CONTINUE;
            }
        });
        return size.get();
    }

    private FileInfoDto getRelativePathFileInfo(Path path){
        Path actual = Path.of(actualPath);
        return new FileInfoDto(
                actual.relativize(path).toString(),
                path.toFile().length(),
                path.toFile().isDirectory(),
                actual.relativize(path.getParent()).toString());
    }
    @Override
    public boolean isSizeWritable(long size) {
        return false;
    }

    @Override                   // -1 Means any size for local storage
    public int chunkSize() {
        return -1;
    }
}
