package org.example.netstore.fxclient.services.browsers;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface FileBrowser {
    List<? extends File> dir(String path);
    void copy(String from, String to);
    void move(String from, String to);
    void delete(String path);
    File mkDir(String path) throws IOException;

    List<? extends File> back();

}
