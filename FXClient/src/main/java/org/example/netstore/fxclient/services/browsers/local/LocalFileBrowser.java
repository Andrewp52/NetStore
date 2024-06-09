package org.example.netstore.fxclient.services.browsers.local;

import org.example.netstore.common.dto.storage.FileInfoDto;
import org.example.netstore.fxclient.services.browsers.FileBrowser;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class LocalFileBrowser implements FileBrowser {
    private String actualPath = "";

    /**
     * Returns directory list
     * Uses FileInfoDto as comparableWrapper
     * @param path
     * @return List<? extends File>
     */
    @Override
    public List<? extends File> dir(String path) {
        actualPath = path;
        if(path == null || path.isBlank()){
            return Arrays.stream(File.listRoots())
                    .map(FileInfoDto::new)
                    .toList();
        }
        try (Stream<Path> pathStream = Files.list(Path.of(path))) {
            return pathStream
                    .map(Path::toFile)
                    .map(FileInfoDto::new)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        return dir(path);
    }

}
