package org.example.netstore.hashingstorage.writer;

import java.io.IOException;
import java.nio.file.Path;

public interface HashingWriter {
    void write(Path source, Path linkDest) throws IOException;
}
