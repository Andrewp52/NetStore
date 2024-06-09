package org.example.netstore.fxclient.stages;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.netstore.fxclient.controllers.MainWindowController;
import org.example.netstore.fxclient.netclient.NetClientFactory;
import org.example.netstore.fxclient.services.TempHolder;

public class MainWindowStage extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(FXMLLocation.MAIN.toString()));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("NetStore client");
        stage.setWidth(1024);
        stage.setHeight(800);
        MainWindowController controller = loader.getController();
        stage.setOnCloseRequest(r -> {
            NetClientFactory.getClient().close();
            TempHolder.cleanup();
        });
        stage.setOnShown(eh -> {
            controller.updatePanelsOnShow();
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
