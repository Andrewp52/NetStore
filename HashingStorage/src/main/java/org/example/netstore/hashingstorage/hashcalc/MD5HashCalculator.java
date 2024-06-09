package org.example.netstore.hashingstorage.hashcalc;



import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class MD5HashCalculator implements HashCalculator{

    @Override
    public String hash(Path path) throws IOException {
        try (InputStream is = Files.newInputStream(path)) {
            return DigestUtils.md5Hex(is);
        }

    }
}
