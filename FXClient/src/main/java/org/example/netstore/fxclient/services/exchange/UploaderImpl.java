package org.example.netstore.fxclient.services.exchange;

import org.example.netstore.common.protocol.responses.Response;
import org.example.netstore.fxclient.netclient.NetClient;

import java.nio.file.Path;
import java.util.function.Consumer;

public class UploaderImpl extends NetService implements Uploader{
    public UploaderImpl(NetClient client) {
        super(client);
    }

    @Override
    public void upload(Path path) {

    }
}
