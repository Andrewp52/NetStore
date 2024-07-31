package org.example.netstore.nettyserver.services.store;

import org.example.netstore.common.dto.storage.FileInfoDto;
import org.example.netstore.common.protocol.requests.storage.MkdirRequest;

import java.util.List;

public interface StorageService {
    List<FileInfoDto> dir(String path);


    boolean checkWriteSize(long storeSize);

    FileInfoDto mkDir(MkdirRequest request);

    int getChunkSize();

    byte[] getChunk(String path, long offset);

    long writeChunk(String path, long offset, byte[] chunk);
}
