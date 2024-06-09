package org.example.netstore.hashingstorage.hashcalc;

import java.io.IOException;
import java.nio.file.Path;

public interface HashCalculator {
    String hash(Path path) throws IOException;
}
