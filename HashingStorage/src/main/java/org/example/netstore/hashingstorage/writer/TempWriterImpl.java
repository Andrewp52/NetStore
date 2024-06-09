package org.example.netstore.hashingstorage.writer;

import org.example.netstore.hashingstorage.AbstractStorage;
import org.example.netstore.hashingstorage.TempFile;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;

public class TempWriterImpl extends AbstractStorage implements TempWriter{
    @Override
    public TempFile CreateTempFile(Path tempDir, Path path, long size) throws IOException {
        checkFolder(tempDir);
        return new TempFile(
                path,
                size,
                Files.createTempFile(tempDir, "", "")
        );
    }

    @Override
    public void write(TempFile tempFile, long offset, byte[] chunk) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(tempFile.getLocation().toString(), "rw")){
            raf.seek(offset);
            raf.write(chunk);
            tempFile.raiseSize(chunk.length);
        }
    }
}
