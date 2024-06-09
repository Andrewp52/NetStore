package org.example.netstore.common.protocol.responses.storage;

import org.example.netstore.common.dto.storage.FileInfoDto;

import java.io.File;
import java.util.List;
import java.util.UUID;

public class DirResponse extends StorageResponse{
    private List<FileInfoDto> dirList;

    public DirResponse(List<FileInfoDto> dirList, UUID requestUUID) {
        super(requestUUID);
        this.dirList = dirList;
    }

    public List<FileInfoDto> getDirList() {
        return dirList;
    }
}
