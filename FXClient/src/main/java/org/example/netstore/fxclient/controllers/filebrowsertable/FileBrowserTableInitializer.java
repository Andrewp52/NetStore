package org.example.netstore.fxclient.controllers.filebrowsertable;


import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import org.example.netstore.fxclient.services.browsers.FileBrowser;

import java.io.File;
import java.util.function.Consumer;

public class FileBrowserTableInitializer {

    public static void init(TableView<File> tableView, FileBrowser browser, Consumer<File> viewer){
        NameColumn nameColumn = new NameColumn();
        tableView.getColumns().add(nameColumn);
        tableView.getColumns().add(new TypeColumn());
        tableView.getColumns().add(new SizeColumn());
        tableView.getSortOrder().add(nameColumn);
        addMouseClickListener(tableView, browser, viewer);
    }

    public static void addMouseClickListener(TableView<File> tableView, FileBrowser browser, Consumer<File> viewer){
        tableView.setOnMouseClicked(evt -> {
            if(evt.getButton().equals(MouseButton.PRIMARY) && evt.getClickCount() > 1){
                File f = tableView.getSelectionModel().getSelectedItem();
                if(f.getName().isBlank() || f.isDirectory()){
                    tableView.getItems().setAll(browser.dir(f.getPath()));
                    tableView.sort();
                } else if(!f.getName().isBlank() && !f.isDirectory()){
                    viewer.accept(f);
                }
            } else if(evt.getButton().equals(MouseButton.BACK)){
                tableView.getItems().setAll(browser.back());
                tableView.sort();
            }
        });
    }

}
