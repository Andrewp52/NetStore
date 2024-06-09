package org.example.netstore.hashingstorage.collider;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollisionSolverImpl implements CollisionSolver{
    // TODO: Make annotation driven injection of configurable fields.
    private static final int bytePackSize = 64 * 1024; // TODO: Move to config
    private static final String fileRegEx = "%s_%s";
    @Override
    public Path solveCollision(Path target, Path candidate, String hash) throws IOException {
        if(!Files.exists(target)){
            Files.createDirectories(target);
            return Path.of(fileRegEx.formatted(target.resolve(hash).toString(), "_0"));
        } else if(isTargetDirEmpty(target)){
            return Path.of(fileRegEx.formatted(target.resolve(hash).toString(), "_0"));
        }
        return solve(target, candidate, hash);
    }

    private Path solve(Path target, Path candidate, String hash) throws IOException {
        List<Path> files;
        try (Stream<Path> ps = Files.list(target)){
            files = ps.collect(Collectors.toList());
        }
        for (Path path : files){
            if(compareFiles(path, candidate)){
                return path;
            }
        }
        return Path.of(fileRegEx.formatted(target.resolve(hash), files.size()));
    }

    private boolean compareFiles(Path hashed, Path candidate) throws IOException {
        if(Files.size(hashed) != Files.size(candidate)){
            return false;
        }
        try (InputStream candIs = new FileInputStream(candidate.toFile());
             InputStream hashIs = new FileInputStream(hashed.toFile())
        ){
            byte[] hBytes;
            byte[] cBytes;
            while (candIs.available() != 0){
                cBytes = candIs.readNBytes(bytePackSize);
                hBytes = hashIs.readNBytes(bytePackSize);
                if(!Arrays.equals(cBytes, hBytes)){
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isTargetDirEmpty(Path target) throws IOException {
        try (Stream<Path> ps = Files.list(target)){
            return ps.findAny().isEmpty();
        }
    }

}
