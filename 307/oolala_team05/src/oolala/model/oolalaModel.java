package oolala.model;

import java.awt.dnd.DragGestureListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import static java.lang.Integer.parseInt;

public class oolalaModel {

  protected static final Logger LOGGER = Logger.getLogger(oolalaModel.class.getName());

  //turtle variables
  private static final double START_ANGLE = 0;
  private static final double START_THICKNESS = 1;
  private final double START_HOME_X = 400;
  private final double START_HOME_Y = 300;

  protected double TurtleX;
  protected double TurtleY;
  protected double homeX;
  protected double homeY;
  protected double TurtleHeading;
  protected double TurtleThickness;
  protected boolean TurtlePen = true;
  protected boolean TurtleVisible = true;
  protected boolean goHome;
  protected boolean stamped;
  protected boolean told;
  protected boolean tellCommand;
  protected boolean moveCommand;
  protected boolean rotateCommand;

  protected ResourceBundle myResources;
  protected String errorStatement;

  public oolalaModel(ResourceBundle resources) {
    homeX = START_HOME_X;
    homeY = START_HOME_Y;
    sendHome();
    myResources = resources;
    errorStatement = myResources.getString("CommandError");
  }

  public void sendHome() {
    TurtleX = homeX;
    TurtleY = homeY;
    TurtleHeading = START_ANGLE;
    TurtleThickness = START_THICKNESS;
    goHome = false;
  }

  public void runCommand(String[] inputParts)
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, MissingResourceException {}

  public void importTurtle(double x, double y, double heading, String description){
    TurtleX = x;
    TurtleY = y;
    TurtleHeading = heading;
    reset();
  }

  public void reset() {
    moveCommand = false;
    tellCommand = false;
    rotateCommand = false;
  }

  private void home(String[] input) {
    goHome = true;
    moveCommand = true;
  }

  /**
   * commands that are common to all models
   */
  protected void fd(String[] input) throws ModelException {
    /**
     * Calculates the destination that the turtle will arrive at.
     * This value will be used by view.moveTurtle() to gradually move the turtle.
     */
    try {
      int pixels = parseInt(input[1].strip());

      double radians = (TurtleHeading * Math.PI) / 180;
      double delta_x = Math.cos(radians) * pixels;
      double delta_y = Math.sin(radians) * pixels;

      TurtleX =  TurtleX + Math.round(delta_x);
      TurtleY =  TurtleY + Math.round(delta_y);
      moveCommand = true;
    } catch (Exception e) {
      LOGGER.info(errorStatement);
      throw new ModelException(errorStatement);
    }
  }

  protected void bk(String[] input) throws ModelException {
    /**
     * Calculates the destination that the turtle will arrive at. Same as fd() but reverse.
     * This value will be used by view.moveTurtle() to gradually move the turtle.
     */
    if (!input[1].strip().contains("-")) {
      input[1] = "-" + input[1];
    }
    fd(input);
    moveCommand = true;
  }

  protected void lt(String[] input) throws ModelException {
    /**
     * Subtracts from TurtleFacing.
     */
    try {
      input[1] = '-'+input[1];
      rt(input);
    } catch (Exception e) {
      LOGGER.info(errorStatement);
      throw new ModelException(errorStatement);
    }
  }

  protected void rt(String[] input) throws ModelException {
    /**
     * Adds to TurtleFacing.
     */

    try {
      rotateCommand = true;
      int degrees = parseInt(input[1].strip());
      TurtleHeading += degrees;
      TurtleHeading = TurtleHeading % 360;
    } catch (Exception e) {
      LOGGER.info(errorStatement);
      throw new ModelException(errorStatement);
    }
  }

  public void setHomeOnClick(double x, double y) {
    homeX = x;
    homeY = y;
  }

  /**
   * methods used by view.moveturtle() to figure out the destination.
   */
  public double getX() {
    return TurtleX;
  }

  public double getY() {
    return TurtleY;
  }

  public double getHeading() {
    return TurtleHeading % 360;
  }

  public double getHomeX() {
    return homeX;
  }

  public double getHomeY() {
    return homeY;
  }

  public double getThickness() {
    return TurtleThickness;
  }

  public boolean getPenStatus() {
    return TurtlePen;
  }

  public boolean getTurtleVisible() {
    return TurtleVisible;
  }

  public boolean getGoHome() {
    return goHome;
  }

  public boolean getStamped() {
    return stamped;
  }

  public void setStamped(boolean stamp) {
    stamped = stamp;
  }

  public boolean isTellCommand() {
    return tellCommand;
  }

  public boolean isRotateCommand() {
    return rotateCommand;
  }

  //Lsystem overrides
  public String[] getArrayOfCommands(String letter) {return new String[0];}
  public String getNextRule(String rules){return "";}
  public double getBaseLength() {return 0;}
  public double getBaseAngle() {return 0;}
  public Map<String, List<String>> getSymbolProps() {return new HashMap();}
  public String getStartSymbol(){return "";}
  public boolean getChangeColor(){return false;}
  public void setCommandForLetter() {}
  public void addDefaultCommands() {}
  public void resetAlphabet() {}
  public void setBaseLength(double baseLength) {}
  public void setBaseAngle(double baseAngle) {}
  public void setChangeColor(boolean changed) {}
  //Darwin overrides
  public boolean getConditionalState(){
    return true;
  }
  public String getSpecies(){
    return "";
  }
  public int getJumpTo() {
    return 0;
  }
  public boolean didAction(){
    return false;
  }
  public List<Double> getInfectedCreatures(){
    return new ArrayList<>();
  }
}
