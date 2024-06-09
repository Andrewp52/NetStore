package org.example.netstore.hashingstorage;

import java.nio.file.Path;
import java.time.LocalDateTime;

public class TempFile {
    private final Path forFile;
    private final long targetSize;
    private long actualSize;
    private final Path location;
    private LocalDateTime lastAccess;

    public TempFile(Path forFile, long targetSize, Path location) {
        this.forFile = forFile;
        this.targetSize = targetSize;
        this.location = location;
        this.lastAccess = LocalDateTime.now();
    }

    public Path getForFile() {
        lastAccess = LocalDateTime.now();
        return forFile;
    }

    public long getTargetSize() {
        return targetSize;
    }

    public long getActualSize() {
        return actualSize;
    }

    public Path getLocation() {
        lastAccess = LocalDateTime.now();
        return location;
    }

    public LocalDateTime getLastAccess() {
        lastAccess = LocalDateTime.now();
        return lastAccess;
    }

    public void raiseSize(long amount){
        actualSize += amount;
        lastAccess = LocalDateTime.now();
    }

    public boolean isCompleted(){
        return targetSize == actualSize;
    }


}
