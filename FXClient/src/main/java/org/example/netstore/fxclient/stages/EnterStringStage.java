package org.example.netstore.fxclient.stages;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.netstore.fxclient.controllers.EnterStringWindowController;

import java.io.IOException;

public class EnterStringStage extends Stage {
    private String value;
    public EnterStringStage(String title, String labelText) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(FXMLLocation.ENTER_STRING.toString()));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        setScene(scene);
        setTitle(title);
        initModality(Modality.APPLICATION_MODAL);
        EnterStringWindowController c = loader.getController();
        c.setTextLabelText(labelText);
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String display(){
        super.showAndWait();
        return value;
    }
}
