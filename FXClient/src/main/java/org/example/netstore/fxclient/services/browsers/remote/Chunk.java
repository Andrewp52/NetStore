package org.example.netstore.fxclient.services.browsers.remote;

import java.io.File;
import java.util.ArrayDeque;
import java.util.Queue;

class Chunk {
    long offset;
    String path;

    public Chunk(long offset, String path) {
        this.offset = offset;
        this.path = path;
    }

    public static Queue<Chunk> queueFromFile(File file, int chunkSize) {
        int count = (int) (file.length() % chunkSize == 0 ? file.length() / chunkSize : file.length() / chunkSize + 1);
        Queue<Chunk> queue = new ArrayDeque<>(count);
        for (int i = 0; i < count; i++) {
            queue.add(new Chunk((long) chunkSize * i, file.getPath()));
        }
        return queue;
    }
}
