package org.example.netstore.hashingstorage.writer;

import java.io.IOException;

public interface ChunkedStorageWriter {

    void write(String path, long offset, byte[] chunk) throws IOException;
    long requestWrite(String path, long size) throws IOException;

    void cancelWrite(String path) throws IOException;
}
