package oolala.view;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.util.ResourceBundle;

public class TurtleStateDisplay extends VBox {

  Label currLocation;
  Label currHeading;
  ResourceBundle myResources;

  public TurtleStateDisplay(ResourceBundle resource, double initialX, double initialY,
      double initalDirection) {
    myResources = resource;
    currLocation = new Label(String.format("%s: (%.2f, %.2f)",
        myResources.getString("CurrentLocation"), initialX, initialY));
    currHeading = new Label(String.format("%s: %.2f",
        myResources.getString("CurrentHeading"), initalDirection));
    getChildren().addAll(currLocation, currHeading);
  }

  public void updateDisplay(double newX, double newY, double newHeading) {
    /**
     * updates the display text with the turtle's new location and heading
     */
    currLocation.setText(String.format("%s: (%.2f, %.2f)",
        myResources.getString("CurrentLocation"), newX, newY));
    currHeading.setText(String.format("%s: %.2f",
        myResources.getString("CurrentHeading"), newHeading));
  }

}
