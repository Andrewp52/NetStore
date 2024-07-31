package org.example.netstore.fxclient.services.browsers;

import java.io.*;
import java.util.List;

public interface FileBrowser {
    List<? extends File> dir(String path, boolean recursive);

    List<? extends File> dir();

    void copy(String from, String to);
    void move(String from, String to);
    void delete(String path);
    File mkDir(String path) throws IOException;

    List<? extends File> back();

    InputStream getInputStream(File localFile) throws Exception;

    OutputStream getOutputStream(File remoteFile) throws IOException;

    long size(String path);

    boolean isSizeWritable(long size);
    int chunkSize();
}
