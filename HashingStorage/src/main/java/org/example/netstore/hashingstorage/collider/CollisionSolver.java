package org.example.netstore.hashingstorage.collider;

import java.io.IOException;
import java.nio.file.Path;

public interface CollisionSolver {
    Path solveCollision(Path target, Path candidate, String hash) throws IOException;

}
