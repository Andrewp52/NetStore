package org.example.netstore.fxclient.services.exchange;

import org.example.netstore.fxclient.services.browsers.FileBrowser;

import java.io.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class ProgressMonitoringExchangerImpl implements ProgressMonitoringExchanger {
    private Consumer<Double> currentFileProgressCallback;
    private Consumer<Double> dirTotalProgressCallback;
    private final FileBrowser localBrowser;
    private final FileBrowser remoteBrowser;
    private final int chunkSize;
    private boolean stopReceived = false;

    public ProgressMonitoringExchangerImpl(FileBrowser localBrowser, FileBrowser remoteBrowser) {
        this.localBrowser = localBrowser;
        this.remoteBrowser = remoteBrowser;
        this.chunkSize = remoteBrowser.chunkSize();
    }

    @Override
    public void setCurrentFileProgressCallback(Consumer<Double> currentFileProgressCallback) {
        this.currentFileProgressCallback = currentFileProgressCallback;
    }

    @Override
    public void setDirTotalProgressCallback(Consumer<Double> dirTotalProgressCallback) {
        this.dirTotalProgressCallback = dirTotalProgressCallback;
    }

    @Override
    public void upload(File file) {
        if(file.isDirectory()){
            AtomicReference<Double> progress = new AtomicReference<>(0D);
            if(remoteBrowser.isSizeWritable(localBrowser.size(file.getPath()))){
                List<? extends File> files = localBrowser.dir(file.getPath(), true);                 // Recursive dirlist
                double progressQuant = (double) 1 / files.size();
                files.forEach(f -> {
                    try {
                        uploadFile(f);
                        if(dirTotalProgressCallback != null){
                            dirTotalProgressCallback.accept(progress.updateAndGet(v -> v + progressQuant));
                        }
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        } else {
            try {
                uploadFile(file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }


    private void uploadFile(File f) throws FileNotFoundException {
        try (InputStream is = localBrowser.getInputStream(f);
             OutputStream os = remoteBrowser.getOutputStream(f)
        ){
            long written = 0L;
            while (!this.stopReceived && is.available() != 0){
                byte[] chunk = is.readNBytes(chunkSize);
                os.write(chunk);
                written += chunk.length;
                if(currentFileProgressCallback != null){
                    currentFileProgressCallback.accept((double) written / (double) f.length());              // Part of 1 ???
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void download(File file) {
        if(file.isDirectory()){
            AtomicReference<Double> progress = new AtomicReference<>(0D);
            List<? extends File> filesList = remoteBrowser.dir(file.getPath(), true);
            double progressQuant = (double) 1 / filesList.size();
            filesList.stream()
                    .filter(f -> !f.isDirectory())
                    .forEach(f -> {
                        downloadFile(f);
                        if(this.dirTotalProgressCallback != null){
                            dirTotalProgressCallback.accept(progress.updateAndGet(v -> v + progressQuant));
                        }
                    });
        } else {
            downloadFile(file);
        }
    }

    @Override
    public void stop() {
        stopReceived = true;
    }

    private void downloadFile(File file) {
        try (InputStream is = remoteBrowser.getInputStream(file);
             OutputStream os = localBrowser.getOutputStream(file)
        ){
            long written = 0L;
            while (!this.stopReceived && is.available() > 0){
                byte[] chunk = is.readNBytes(chunkSize);
                os.write(chunk);
                written += chunk.length;
                if(currentFileProgressCallback != null){
                    currentFileProgressCallback.accept((double) written / (double) file.length());
                }
            }
            if(this.stopReceived && written < file.length()){
                localBrowser.delete(file.getPath());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
