package org.example.netstore.fxclient.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.example.netstore.fxclient.PropertiesHolder;
import org.example.netstore.fxclient.netclient.ConnectionSettings;
import org.example.netstore.fxclient.netclient.NetClient;
import org.example.netstore.fxclient.netclient.NetClientFactory;
import org.example.netstore.fxclient.stages.LoginWindowStage;
import org.example.netstore.fxclient.stages.NotificationType;
import org.example.netstore.fxclient.stages.notifications.ErrorWindowStage;
import org.example.netstore.fxclient.stages.notifications.NotificationStage;

import java.io.IOException;


public class LoginWindowController {
    @FXML public TextField usernameTextField;
    @FXML public TextField passwordPasswordField;
    @FXML public Button signinButton;

    public void startLogin() throws IOException {
        if(!isFieldsValid()){
            new ErrorWindowStage( "Error", "Fill login & password").showAndWait();
            return;
        }
        ConnectionSettings settings = prepareSettings();
        NetClient client = NetClientFactory.getClient(settings);
        try {
            client.connect();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        LoginWindowStage window = (LoginWindowStage) usernameTextField.getScene().getWindow();
        window.setDto(client.getConnectedUser());
        window.close();
    }

    private boolean isFieldsValid(){
        return !usernameTextField.getText().isBlank() && !passwordPasswordField.getText().isBlank();
    }
    private ConnectionSettings prepareSettings(){
        return new ConnectionSettings(
                PropertiesHolder.get("server"),
                Integer.parseInt(PropertiesHolder.get("port")),
                usernameTextField.getText(),
                passwordPasswordField.getText()
        );
    }


}
