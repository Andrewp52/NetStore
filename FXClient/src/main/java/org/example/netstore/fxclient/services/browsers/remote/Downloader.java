package org.example.netstore.fxclient.services.browsers.remote;

import org.example.netstore.common.dto.FileChunk;
import org.example.netstore.common.protocol.requests.storage.GetChunkRequest;
import org.example.netstore.common.protocol.responses.error.ErrorResponse;
import org.example.netstore.fxclient.netclient.NetClient;

import java.io.File;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * File downloader
 * Chunk by chunk, using read ahead buffer
 */
public class Downloader extends Thread {
    private static final int BUFFER_CAPACITY = 100;
    private final Queue<byte[]> buffer = new ArrayBlockingQueue<>(BUFFER_CAPACITY, true);
    private final Object bufferMonitor = new Object();
    private final AtomicInteger chunksInProgress = new AtomicInteger();
    private final NetClient client;
    private final Queue<Chunk> chunks;
    private final Object chunkWaitMonitor = new Object();
    private long available;

    Downloader(File file, NetClient client) throws Exception {
        this.client = client;
        this.chunks = Chunk.queueFromFile(file, client.getChunkSize());
        available = file.length();
    }

    /**
     * To avoid threads blocking in the net client (net client uses different thread(s)),
     * don't use buffer's queue block to stop download while buffer is full!
     * Method wait here, on bufferMonitor, until client will get chunk from buffer,
     * then continues to send requests into the net client.
     */
    @Override
    public void run() {
        while (!this.isInterrupted() && !chunks.isEmpty()){
            while (buffer.size() == BUFFER_CAPACITY || buffer.size() + chunksInProgress.get() == BUFFER_CAPACITY){
                synchronized (bufferMonitor){
                    try {
                        bufferMonitor.wait();
                    } catch (InterruptedException e) {
                        this.interrupt();
                        throw new RuntimeException(e);
                    }
                }
            }
            Chunk chunk = chunks.poll();
            chunksInProgress.incrementAndGet();
            try {
                client.sendRequest(new GetChunkRequest(chunk.path, chunk.offset), response -> {
                    switch (response.getResponseType()){
                        case OK -> {
                            this.buffer.add(((FileChunk)response).getChunk());
                            synchronized (chunkWaitMonitor){
                                this.chunkWaitMonitor.notifyAll();
                            }
                            this.chunksInProgress.decrementAndGet();
                        }
                        case ERROR -> throw new RuntimeException(((ErrorResponse)response).getMessage());
                    }
                });
            } catch (Exception e) {
                this.interrupt();
                throw new RuntimeException(e);
            }
        }
    }

    public long getAvailable(){
        return this.available;
    }

    public byte[] nextChunk(){
        if(buffer.isEmpty() && ((!chunks.isEmpty() || chunksInProgress.get() != 0))){
            try {
                synchronized (chunkWaitMonitor){
                    chunkWaitMonitor.wait();
                }
            } catch (InterruptedException e) {
                this.interrupt();
                throw new RuntimeException(e);
            }
        }
        byte[] chunk = buffer.poll();
        synchronized (bufferMonitor){
            bufferMonitor.notifyAll();
        }
        available -= chunk.length;
        return chunk;
    }

    private static class Chunk {
        long offset;
        String path;

        public Chunk( long offset, String path) {
            this.offset = offset;
            this.path = path;
        }

        public static Queue<Chunk> queueFromFile(File file, int chunkSize){
            int count = (int) (file.length() % chunkSize == 0 ? file.length() / chunkSize : file.length() / chunkSize + 1);
            Queue<Chunk> queue = new ArrayDeque<>(count);
            for (int i = 0; i < count; i++) {
                queue.add(new Chunk((long) chunkSize * i, file.getPath()));
            }
            return queue;
        }
    }

}
