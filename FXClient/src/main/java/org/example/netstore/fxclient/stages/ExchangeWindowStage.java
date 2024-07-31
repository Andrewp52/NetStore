package org.example.netstore.fxclient.stages;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.netstore.fxclient.controllers.ExchangeWindowController;
import org.example.netstore.fxclient.services.exchange.ProgressMonitoringExchanger;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ExchangeWindowStage extends Stage {
    public ExchangeWindowStage(ProgressMonitoringExchanger exchanger, List<File> files, boolean download) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(FXMLLocation.EXCHANGE.toString()));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        setScene(scene);
        setTitle("Exchange");
        initModality(Modality.APPLICATION_MODAL);
        ExchangeWindowController c = loader.getController();
        c.setExchanger(exchanger);
        this.setOnShown(e -> {
            if(download){
                c.download(files);
            } else {
                c.upload(files);
            }
        });
    }
}
