package org.example.netstore.fxclient.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.netstore.fxclient.stages.NotificationType;
import org.example.netstore.fxclient.stages.notifications.NotificationStage;

public class NotificationWindowController {
    @FXML public Label messageLabel;
    @FXML public ImageView typeImageView;


    public void setType(NotificationType type) {
        typeImageView.setImage(new Image(getClass().getClassLoader().getResourceAsStream(type.getIconPath())));
    }

    public void setMessage(String message) {
        this.messageLabel.setText(message);
    }

    public void closePressed(ActionEvent actionEvent) {
        ((NotificationStage) this.messageLabel.getScene().getWindow()).close();
    }
}
