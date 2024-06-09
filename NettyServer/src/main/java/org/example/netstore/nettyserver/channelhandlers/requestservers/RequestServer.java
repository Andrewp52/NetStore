package org.example.netstore.nettyserver.channelhandlers.requestservers;


import org.example.netstore.common.protocol.requests.Request;
import org.example.netstore.common.protocol.responses.Response;

public interface RequestServer {
    Response serve(Request request);
}
