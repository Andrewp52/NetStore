package org.example.netstore.hashingstorage;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class AbstractStorage {
    // TODO: Make annotation driven injection of configurable fields.
    protected static final Path root = Path.of("storage"); // TODO: Move to config

    protected static void checkFolder(Path path) throws IOException{
        if(!Files.exists(path)){
            Files.createDirectories(path);
        }
        if (!Files.exists(path)){
            Files.createDirectories(path);
        }
    }

}
