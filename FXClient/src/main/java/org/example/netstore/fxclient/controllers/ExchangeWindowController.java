package org.example.netstore.fxclient.controllers;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import org.example.netstore.fxclient.services.exchange.ProgressMonitoringExchanger;

import java.io.File;
import java.util.List;


public class ExchangeWindowController {
    @FXML public Label fileOperationLabel;
    @FXML public Label fileNameLabel;
    @FXML public Label dirOperationLabel;
    @FXML public Label dirNameLabel;
    @FXML public Button cancelButton;
    @FXML public ProgressBar fileExchangeProgressBar;
    @FXML public ProgressBar dirExchangeProgressBar;

    private ProgressMonitoringExchanger exchanger;


    public void setExchanger(ProgressMonitoringExchanger exchanger){
        this.exchanger = exchanger;
        this.exchanger.setCurrentFileProgressCallback(d -> fileExchangeProgressBar.setProgress(d));
        this.exchanger.setDirTotalProgressCallback(d -> dirExchangeProgressBar.setProgress(d));
    }

    public void download(List<File> files){
        files.forEach(exchanger::download);
    }

    public void upload(List<File> files){
        files.forEach(exchanger::upload);
    }

    public void stop(){
        this.exchanger.stop();
    }


}
