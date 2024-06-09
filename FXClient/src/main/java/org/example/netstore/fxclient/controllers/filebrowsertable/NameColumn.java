package org.example.netstore.fxclient.controllers.filebrowsertable;

import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.File;

public class NameColumn extends TableColumn<File, HBox> {
    private static final int imageWidth = 16;
    private static final int imageHeight = 16;

    public NameColumn() {
        super("Name");
        setCellValueFactory(file ->
                new SimpleObjectProperty<>(new FileNameHbox(file.getValue(), imageWidth, imageHeight))
        );
        this.setSortable(true);
    }

    private static class FileNameHbox extends HBox implements Comparable<FileNameHbox>{
        private static final int itemsMargin = 10;
        private final File file;
        public FileNameHbox(File file, int imageWidth, int imageHeight) {
            this.file = file;
            this.setPadding(new Insets(0, 0, 0, itemsMargin));
            this.setSpacing(itemsMargin);
            ImageView imageView = getImage(file, imageWidth, imageHeight);
            this.getChildren().add(imageView);
            if(file.getName().isBlank()){
                this.getChildren().add(new Label(file.getPath().replace(":\\", "")));
            } else {
                this.getChildren().add(new Label(file.getName()));
            }
        }

        private static ImageView getImage(File file, double width, double height){
            Image image = file.getName().isBlank() || file.isDirectory()?
                    new Image("icons/Folder_16x16.png", width, height, false, true) :
                    new Image("icons/File_16x16.png", width, height, false, true);
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(width);
            imageView.setFitHeight(height);

            return imageView;
        }


        @Override
        public int compareTo(FileNameHbox o) {
            return this.file.compareTo(o.file);
        }
    }


}
