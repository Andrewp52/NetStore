package org.example.netstore.hashingstorage.writer;

import org.example.netstore.hashingstorage.AbstractStorage;
import org.example.netstore.hashingstorage.collider.CollisionSolver;
import org.example.netstore.hashingstorage.collider.CollisionSolverImpl;
import org.example.netstore.hashingstorage.hashcalc.HashCalculator;
import org.example.netstore.hashingstorage.hashcalc.MD5HashCalculator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;


public class HashingWriterImpl extends AbstractStorage implements HashingWriter{
    // TODO: Make annotation driven injection of configurable fields.
    private static final HashCalculator hashCalculator = new MD5HashCalculator();
    private static final CollisionSolver collisionSolver = new CollisionSolverImpl();
    private static final Path hashed = root.resolve("hashed"); // TODO: Move to config

    static {
        try {
            checkFolder(hashed);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /***
     * Calculates hash of the given temp file with HashCalculator.
     * Finds path to store hashed file with CollisionSolver.
     * Moving temp file into the found hashed path
     * @param source source file
     * @param linkDest symlink path
     * @throws IOException
     */
    @Override
    public void write(Path source, Path linkDest) throws IOException {
        String hash = hashCalculator.hash(source);
        Path storeTo = generateDestPath(hash);
        storeTo = collisionSolver.solveCollision(storeTo, source, hash);
        Files.move(source, storeTo, StandardCopyOption.ATOMIC_MOVE);
        createUserSymLink(storeTo, linkDest);
    }
    /***
     * Creates a symbolic link to the given hashed file in user's directory
     * linkLocation must contain absolute path
     * @param hashedFile hashed file
     * @param linkLocation location of a new symlink in user's directory
     * @throws IOException
     */
    private void createUserSymLink(Path hashedFile, Path linkLocation) throws IOException {
        Path linkDir = linkLocation.getParent();
        if(!Files.exists(linkDir)){
            Files.createDirectories(linkDir);
        }
        Files.createSymbolicLink(linkLocation, hashedFile.toAbsolutePath());
    }

    /***
     * Creates directory tree splitting hash to groups of 2 symbols
     * @param hash calculated temp file hash
     * @return directory tree path
     */
    private static Path generateDestPath(String hash){
        Path p = hashed;
        for (int i = 0; i < 32; i+=2) {
            p = p.resolve(hash.substring(i, i+2));
        }
        return p;
    }


}
