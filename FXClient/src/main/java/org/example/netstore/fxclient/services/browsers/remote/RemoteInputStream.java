package org.example.netstore.fxclient.services.browsers.remote;

import org.example.netstore.fxclient.netclient.NetClient;

import java.io.*;


public class RemoteInputStream extends InputStream {
    private final NetClient client;
    private Downloader downloader;
    private byte[] currentChunk;
    private int bytesRead;

    public RemoteInputStream(File remoteFile, NetClient client) throws Exception {
        this.client = client;
        this.downloader = new Downloader(remoteFile, client);
        this.downloader.setDaemon(true);
        this.downloader.start();
    }

    @Override
    public int read() throws IOException {
        if(currentChunk != null && currentChunk.length > bytesRead){
            return currentChunk[bytesRead++] & 0xFF;
        }
        if((currentChunk = downloader.nextChunk()) != null){
            bytesRead = 0;
            return currentChunk[bytesRead++] & 0xFF;
        }
        return -1;
    }

    @Override
    public int available() throws IOException {
        if(this.currentChunk == null){
            if(downloader.getAvailable() == 0){
                return 0;
            } else {
                this.currentChunk = downloader.nextChunk();
                return (int) (this.currentChunk.length + downloader.getAvailable());
            }
        }
        return (int) (this.currentChunk.length - bytesRead + downloader.getAvailable());
    }


    @Override
    public long transferTo(OutputStream out) throws IOException {
        long counter = 0L;
        byte[] chunk;
        while (downloader.getAvailable() != 0){
            chunk = downloader.nextChunk();
            out.write(chunk);
            counter += chunk.length;
        }
        return counter;
    }

    @Override
    public void close() throws IOException {
        this.downloader.interrupt();
    }

}
