package org.example.netstore.fxclient.services.browsers.remote;


import org.example.netstore.common.protocol.requests.storage.PostChunkRequest;
import org.example.netstore.common.protocol.responses.error.ErrorResponse;
import org.example.netstore.fxclient.netclient.NetClient;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Arrays;

public class RemoteOutputStream extends OutputStream {
    private final File file;
    private final Path actual;
    private final NetClient client;
    private final int chunkSize;
    private long fileOffset = 0L;
    public RemoteOutputStream(File file, Path actual, NetClient client) {
        this.file = file;
        this.actual = actual;
        this.client = client;
        try {
            this.chunkSize = client.getChunkSize();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void write(int b) throws IOException {
        throw new RuntimeException("Single byte writing is not implemented");
    }

    @Override
    public void write(byte[] b) throws IOException {
        if(b.length <= chunkSize){
            try {
                writeChunk(b);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            int index = 0;
            int lastIndex = 0;
            while (index < b.length){
                lastIndex = Math.min(index + chunkSize, b.length);
                try {
                    writeChunk(Arrays.copyOfRange(b, index, lastIndex));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                index += chunkSize;
            }
        }
    }

    private void writeChunk(byte[] b) throws Exception {
        Path remotePath = actual.resolve(this.file.getPath());
        client.sendRequest(new PostChunkRequest(remotePath.toString(), b, this.fileOffset), r -> {
            switch (r.getResponseType()){
                case OK -> fileOffset += b.length;
                case ERROR -> throw new RuntimeException(((ErrorResponse)r).getMessage());  // Do informative exception
            }
        });
    }
}
