package org.example.netstore.common.protocol.requests;


import org.example.netstore.common.protocol.responses.Response;

public interface RequestServer {
    Response serve(Request request);
}
