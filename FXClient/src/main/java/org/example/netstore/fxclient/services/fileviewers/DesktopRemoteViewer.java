package org.example.netstore.fxclient.services.fileviewers;

import org.example.netstore.fxclient.netclient.NetClient;
import org.example.netstore.fxclient.services.TempHolder;
import org.example.netstore.fxclient.services.browsers.remote.RemoteInputStream;


import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.util.function.Consumer;

public class DesktopRemoteViewer implements Consumer<File>{
    private final NetClient client;

    public DesktopRemoteViewer(NetClient client) {
        this.client = client;
    }

    @Override
    public void accept(File file) {
        File f = downloadToTemp(file);
        TempHolder.addTemp(f);
        try {
            Desktop desktop = Desktop.getDesktop();
            desktop.open(f);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private File downloadToTemp(File file){
        try {
            File tempFile = Files.createTempFile("", file.getName().substring(file.getName().lastIndexOf("."))).toFile();
            InputStream is = new RemoteInputStream(file, this.client);
            OutputStream os = new FileOutputStream(tempFile);
            while (is.available() > 0) {
                os.write(((byte) is.read()));
            }
            os.close();
            is.close();
            return tempFile;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
