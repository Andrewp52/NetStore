package org.example.netstore.fxclient.controllers.filebrowsertable;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;

import java.io.File;

public class SizeColumn extends TableColumn<File, String> {
    public SizeColumn() {
        super("Size");
        setCellValueFactory(file -> {
            if(file.getValue().getName().isBlank() || file.getValue().isDirectory()){
                return new SimpleStringProperty("");
            }
            long size = file.getValue().length();
            return new SimpleStringProperty(String.valueOf(size));
        });
    }
}
