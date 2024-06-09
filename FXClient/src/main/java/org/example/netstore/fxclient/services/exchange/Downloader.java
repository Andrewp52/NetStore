package org.example.netstore.fxclient.services.exchange;

import org.example.netstore.common.protocol.responses.Response;

import java.io.File;
import java.nio.file.Path;
import java.util.function.Consumer;

public interface Downloader {
    void download(Consumer<File> resultCallback);
}
