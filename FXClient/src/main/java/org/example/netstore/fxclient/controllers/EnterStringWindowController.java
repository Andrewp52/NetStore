package org.example.netstore.fxclient.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.netstore.fxclient.stages.EnterStringStage;

public class EnterStringWindowController {

    @FXML public Label textLabel;
    @FXML public TextField stringTextField;
    @FXML public Button okButton;



    public void okPressed(ActionEvent actionEvent) {
        EnterStringStage stage = (EnterStringStage) textLabel.getScene().getWindow();
        stage.setValue(stringTextField.getText());
        stage.close();
    }

    public void setTextLabelText(String text) {
        this.textLabel.setText(text);
    }

}
