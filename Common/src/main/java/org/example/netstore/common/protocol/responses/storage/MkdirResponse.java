package org.example.netstore.common.protocol.responses.storage;

import org.example.netstore.common.dto.storage.FileInfoDto;

import java.util.UUID;

public class MkdirResponse extends StorageResponse{
    private final FileInfoDto fileInfo;
    public MkdirResponse(FileInfoDto fileInfo, UUID requestUUID) {
        super(requestUUID);
        this.fileInfo = fileInfo;
    }

    public FileInfoDto getFileInfo() {
        return fileInfo;
    }
}
