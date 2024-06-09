package org.example.netstore.hashingstorage.writer;


import org.example.netstore.hashingstorage.TempFile;

import java.io.IOException;
import java.nio.file.Path;

public interface TempWriter {

    TempFile CreateTempFile(Path tempDir, Path path, long size) throws IOException;

    void write(TempFile tempFile, long offset, byte[] chunk) throws IOException;

}
