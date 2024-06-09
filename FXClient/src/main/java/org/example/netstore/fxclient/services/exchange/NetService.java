package org.example.netstore.fxclient.services.exchange;

import org.example.netstore.fxclient.netclient.NetClient;

public abstract class NetService {
    protected final NetClient client;

    protected NetService(NetClient client) {
        this.client = client;
    }
}
