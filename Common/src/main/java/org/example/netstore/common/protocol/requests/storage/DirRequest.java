package org.example.netstore.common.protocol.requests.storage;

import org.example.netstore.common.protocol.requests.StorageCommand;

public class DirRequest extends StorageRequest{
    private final String path;
    private boolean recursive = false;
    public DirRequest(String path) {
        super(StorageCommand.DIR);
        this.path = path;
    }
    public DirRequest(String path, boolean recursive) {
        this(path);
        this.recursive = recursive;
    }

    public String getPath() {
        return path;
    }

    public boolean isRecursive() {
        return recursive;
    }


}
