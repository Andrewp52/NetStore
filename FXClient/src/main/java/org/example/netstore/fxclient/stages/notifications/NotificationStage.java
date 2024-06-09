package org.example.netstore.fxclient.stages.notifications;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.netstore.fxclient.controllers.NotificationWindowController;
import org.example.netstore.fxclient.stages.FXMLLocation;
import org.example.netstore.fxclient.stages.NotificationType;

import java.io.IOException;

public abstract class NotificationStage extends Stage {
    NotificationStage(NotificationType type, String title, String message) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(FXMLLocation.NOTIFICATION.toString()));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        setScene(scene);
        setTitle(title);
        initModality(Modality.APPLICATION_MODAL);
        NotificationWindowController c = loader.getController();
        c.setType(type);
        c.setMessage(message);
    }
}
