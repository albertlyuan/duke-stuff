package oolala.view;

import java.util.List;
import javafx.scene.shape.Circle;
import oolala.utils.ImageChooser;

public class Turtle extends Circle {

  private ImageChooser myImageChooser;
  private double TurtleHeading;
  private boolean TurtlePen = true;

  public Turtle(double initialX, double initialY, double initialSize) {
    myImageChooser = new ImageChooser();
    setCenterX(initialX);
    setCenterY(initialY);
    setRadius(initialSize);
    setHeading(0);
    setId("Turtle");
  }

  public void setImage() {
    setFill(myImageChooser.selectImage());
  }

  public void move(double modelX, double modelY) {
    /**
     * moves the turtle pixel by pixel if the turtle is not at the destination point.
     */
    if (getCenterX() != modelX) {
      if (getCenterX() < modelX) {
        setCenterX(getCenterX() + 1);
      } else {
        setCenterX(getCenterX() - 1);
      }
    }
    if (getCenterY() != modelY) {
      if (getCenterY() < modelY) {
        setCenterY(getCenterY() + 1);
      } else {
        setCenterY(getCenterY() - 1);
      }
    }
  }

  public void setHeading(double newHeading) {
    TurtleHeading = newHeading;
  }

  public double getHeading() {
    return TurtleHeading % 360;
  }

  public void setPenStatus(boolean status) {
    TurtlePen = status;
  }

  public boolean getPenStatus() {
    return TurtlePen;
  }

  /**
   * darwin turtle stuff
   */
  public String getNextCommand() throws IndexOutOfBoundsException {
    return "";
  }

  public void setBaseCode(List<String> baseCode) {
    //
  }
  public void setSpecies(String species) {
    //
  }

  public void setInstrIdx(int instrIdx) {
    //
  }

  public String getSpecies() {
    return "";
  }
}
