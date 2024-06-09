package org.example;

import org.example.netstore.hashingstorage.writer.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    static int size = 10;
    public static void main(String[] args) throws IOException {
        Path p = Path.of("storage/3.txt");
        ChunkedStorageWriter storageWriter = new HashingStorageWriter(new TempWriterImpl(), new HashingWriterImpl(), 1L);
        storageWriter.requestWrite(p.toString(), Files.size(p));
        try (FileInputStream fis = new FileInputStream(p.toFile())){
            int wr = 0;
            while (fis.available() != 0){
                storageWriter.write(p.toString(), wr, fis.readNBytes(size));
                wr += size;
            }
        }

    }
}