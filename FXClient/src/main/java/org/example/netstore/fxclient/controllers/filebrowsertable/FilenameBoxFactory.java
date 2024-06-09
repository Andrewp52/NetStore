package org.example.netstore.fxclient.controllers.filebrowsertable;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.File;

/***
 * Generates Hbox with image, depends on if file is directory or not, and file name string
 */
public class FilenameBoxFactory {
    private static final String FILE_ICON = "icons/File_16x16.png";
    private static final String FOLDER_ICON = "icons/Folder_16x16.png";

    private static final int ITEMS_MARGIN = 5;
    public static ObservableValue<HBox> getBox(File file, double width, double height){
        HBox box = new HBox();
        box.setPadding(new Insets(0, 0, 0, ITEMS_MARGIN));
        box.setSpacing(ITEMS_MARGIN);
        ImageView imageView = getImage(file, width, height);
        box.getChildren().add(imageView);
        if(file.getName().isBlank()){
            box.getChildren().add(new Label(file.getPath().replace(":\\", "")));
        } else {
            box.getChildren().add(new Label(file.getName()));
        }
        return new SimpleObjectProperty<>(box);
    }

    private static ImageView getImage(File file, double width, double height){
        Image image = file.getName().isBlank() || file.isDirectory()?
                new Image(FOLDER_ICON, width, height, false, true) :
                new Image(FILE_ICON, width, height, false, true);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);

        return imageView;
    }


}
