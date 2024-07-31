package org.example.netstore.fxclient.services.exchange;

import java.io.File;
import java.util.function.Consumer;

public interface ProgressMonitoringExchanger {
    void setCurrentFileProgressCallback(Consumer<Double> currentFileProgressCallback);

    void setDirTotalProgressCallback(Consumer<Double> dirTotalProgressCallback);

    void upload(File file);
    void download(File file);
    void stop();
}
