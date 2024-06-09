package org.example.netstore.fxclient.services.exchange;

import org.example.netstore.common.protocol.responses.Response;

import java.nio.file.Path;
import java.util.function.Consumer;

public interface Uploader {
    void upload(Path path);
}
