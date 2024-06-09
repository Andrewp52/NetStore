package org.example.netstore.fxclient.services.exchange;

import org.example.netstore.fxclient.netclient.NetClient;

import java.io.File;
import java.util.function.Consumer;

public class DownloaderImpl extends NetService implements Downloader{
    private long remoteSize;
    private String localPath;
    private String remotePath;
    private Consumer<Double> progressCallback;

    public DownloaderImpl(NetClient client, File remoteFile, String localPath, Consumer<Double> progressCallback) {
        super(client);
        this.remoteSize = remoteFile.length();
        this.localPath = localPath;
        this.remotePath = remoteFile.getPath();
        this.progressCallback = progressCallback;
    }

    @Override
    public void download(Consumer<File> resultCallback) {

    }
}
