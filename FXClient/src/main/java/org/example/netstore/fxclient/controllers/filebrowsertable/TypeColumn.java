package org.example.netstore.fxclient.controllers.filebrowsertable;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;

import java.io.File;

public class TypeColumn extends TableColumn<File, String> {
    public TypeColumn() {
        super("Type");
        setCellValueFactory(file -> {
            String fileName = file.getValue().getName();
            if(fileName.isBlank() || file.getValue().isDirectory()){
                return new SimpleStringProperty("");
            }
            int pointPos = fileName.lastIndexOf('.');
            String extension = pointPos >= 0 ? fileName.substring(pointPos) : "";
            return new SimpleStringProperty(extension);
        });
    }
}
