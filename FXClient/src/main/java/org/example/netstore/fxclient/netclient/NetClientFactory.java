package org.example.netstore.fxclient.netclient;

import org.example.netstore.fxclient.netclient.netty.ClientResponseHandler;
import org.example.netstore.fxclient.netclient.netty.NettyClient;
import org.example.netstore.fxclient.netclient.netty.NettyClientFasade;
import org.example.netstore.fxclient.netclient.netty.ResponseHandler;

public class NetClientFactory {
    private static final NetClient c = new NettyClientFasade(new ClientResponseHandler());
    public static NetClient getClient(ConnectionSettings connectionData){
        c.setConnectionSettings(connectionData);
        return c;
    }

    public static NetClient getClient(){
        return c;
    }

}
