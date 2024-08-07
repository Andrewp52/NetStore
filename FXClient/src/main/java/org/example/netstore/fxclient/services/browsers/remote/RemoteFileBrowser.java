package org.example.netstore.fxclient.services.browsers.remote;

import org.example.netstore.common.protocol.requests.Request;
import org.example.netstore.common.protocol.requests.storage.DirRequest;
import org.example.netstore.common.protocol.requests.storage.GetSizeRequest;
import org.example.netstore.common.protocol.requests.storage.MkdirRequest;
import org.example.netstore.common.protocol.requests.storage.PostSizeCheckRequest;
import org.example.netstore.common.protocol.responses.BooleanResponse;
import org.example.netstore.common.protocol.responses.LongResponse;
import org.example.netstore.common.protocol.responses.Response;
import org.example.netstore.common.protocol.responses.ResponseType;
import org.example.netstore.common.protocol.responses.error.ErrorResponse;
import org.example.netstore.common.protocol.responses.storage.DirResponse;
import org.example.netstore.common.protocol.responses.storage.MkdirResponse;
import org.example.netstore.fxclient.netclient.NetClient;
import org.example.netstore.fxclient.services.browsers.FileBrowser;

import java.io.*;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class RemoteFileBrowser implements FileBrowser {
    private final NetClient client;
    private String actualPath = "";
    public RemoteFileBrowser(NetClient client) {
        this.client = client;
    }

    @Override
    public List<? extends File> dir(String path, boolean recursive) {
        actualPath = path;
        Request request = new DirRequest(path);
        DirResponse response = (DirResponse) sendAndWait(request).get();
        return response.getDirList();
    }

    @Override
    public List<? extends File> dir() {
        Request request = new DirRequest(actualPath);
        DirResponse response = (DirResponse) sendAndWait(request).get();
        return response.getDirList();
    }

    @Override
    public void copy(String from, String to) {

    }

    @Override
    public void move(String from, String to) {

    }

    @Override
    public void delete(String path) {

    }

    @Override
    public File mkDir(String path) {
        Request request = new MkdirRequest(path);
        MkdirResponse response = (MkdirResponse) sendAndWait(request).get();
        return response.getFileInfo();
    }

    @Override
    public List<? extends File> back() {
        if(actualPath.isBlank()){
            return dir("", true);
        } else {
            Path parent = Path.of(actualPath).getParent();
            return parent == null ? dir("", true) : dir(parent.toString(), true);
        }
    }

    @Override
    public long size(String path) {
        AtomicReference<Response> response = sendAndWait(new GetSizeRequest(path));
        return ((LongResponse) response.get()).getValue();
    }

    @Override
    public boolean isSizeWritable(long size) {
        AtomicReference<Response> response = sendAndWait(new PostSizeCheckRequest(size));
        return ((BooleanResponse) response.get()).isValue();
    }

    @Override
    public int chunkSize() {
        try {
            return client.getChunkSize();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public InputStream getInputStream(File remoteFile) throws Exception {
        return new RemoteInputStream(remoteFile, client);
    }
    private AtomicReference<Response> sendAndWait(Request request){
        AtomicReference<Response> ref = new AtomicReference<>();
        try {
            client.sendRequestAndWait(request, response -> {
                if(response.getResponseType().equals(ResponseType.ERROR)){
                    throw new RuntimeException(((ErrorResponse)response).getMessage());
                }
                ref.set(response);
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ref;
    }

    @Override
    public OutputStream getOutputStream(File file) {
        return new RemoteOutputStream(file, Path.of(actualPath), client);
    }


}
