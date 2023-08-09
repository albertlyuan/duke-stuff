package oolala.utils;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.stage.FileChooser;
import java.io.File;

public class ImageChooser {

  FileChooser fileChooser;

  public ImageChooser() {
    fileChooser = new FileChooser();
  }

  public ImagePattern selectImage() {
    /**
     * opens a file window to allow the user to select a png,jpg,or gif file
     * returns an imagepattern object
     */
    fileChooser.setTitle("Open Resource File");
    fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
    File selectedFile = fileChooser.showOpenDialog(null); //need to look into this
    Image image = new Image(selectedFile.toURI().toString());
    return new ImagePattern(image);
  }
}
