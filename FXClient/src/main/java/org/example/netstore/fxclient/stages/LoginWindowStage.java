package org.example.netstore.fxclient.stages;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.netstore.common.dto.UserDto;
import org.example.netstore.fxclient.controllers.LoginWindowController;


import java.io.IOException;

public class LoginWindowStage extends Stage {
    UserDto dto;
    private boolean cancelled;
    public LoginWindowStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(FXMLLocation.LOGIN.toString()));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        setScene(scene);
        setTitle("NetStore client login");
        initModality(Modality.APPLICATION_MODAL);
        LoginWindowController c = loader.getController();
        this.setOnCloseRequest(e -> {
            this.cancelled = true;
        });

    }

    public UserDto display(){
        dto = null;
        this.showAndWait();
        return dto;
    }

    public void setDto(UserDto dto) {
        this.dto = dto;
    }

    public boolean isCancelled(){
        return this.cancelled;
    }

}
