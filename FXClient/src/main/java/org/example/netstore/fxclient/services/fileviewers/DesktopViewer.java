package org.example.netstore.fxclient.services.fileviewers;

import org.example.netstore.fxclient.stages.notifications.ErrorWindowStage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

public class DesktopViewer implements Consumer<File> {
    @Override
    public void accept(File file) {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().open(file);
            } catch (IOException e) {
                try {
                    new ErrorWindowStage("Open file error", e.getMessage());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}
