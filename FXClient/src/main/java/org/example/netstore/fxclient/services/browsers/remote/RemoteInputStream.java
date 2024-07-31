package org.example.netstore.fxclient.services.browsers.remote;

import org.example.netstore.fxclient.netclient.NetClient;

import java.io.*;
import java.util.Arrays;


public class RemoteInputStream extends InputStream {
    private final Downloader downloader;
    private byte[] currentChunk;
    private int bytesRead;

    public RemoteInputStream(File remoteFile, NetClient client) throws Exception {
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
    public byte[] readNBytes(int len) throws IOException {
        checkChunk();
        if(len == currentChunk.length){
            return getSameSizeChunk();
        }
        return getOffSizeChunk(len);
    }

    private byte[] getOffSizeChunk(int len) {
        if(len <= currentChunk.length - bytesRead){
            byte[] chunk = Arrays.copyOfRange(currentChunk, bytesRead, bytesRead + len);
            bytesRead += len;
            return chunk;
        } else {
            return accumulateChunk(len);
        }
    }

    private byte[] accumulateChunk(int len) {
        byte[] chunk;
        if(len <= currentChunk.length - bytesRead){
            chunk = Arrays.copyOfRange(currentChunk, bytesRead, bytesRead + len);
            bytesRead += len;
        } else {
            chunk = new byte[(int) Math.min(currentChunk.length - bytesRead + downloader.getAvailable(), len)];
            int index = 0;
            while (index < chunk.length){
                int length = Math.min(chunk.length - index, currentChunk.length - bytesRead);
                System.arraycopy(currentChunk, bytesRead, chunk, index, length);
                index += length;
                bytesRead += length;
                checkChunk();
            }
        }
        return chunk;
    }

    private void checkChunk(){
        if((currentChunk == null || bytesRead == currentChunk.length) && downloader.getAvailable() > 0){
            currentChunk = downloader.nextChunk();
            bytesRead = 0;
        }
    }
    private byte[] getSameSizeChunk(){
        byte[] chunk = currentChunk;
        currentChunk = null;
        bytesRead = 0;
        return chunk;
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
