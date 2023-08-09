package oolala.view;


import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import oolala.model.ModelException;
import oolala.model.oolalaModelLsystem;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.MissingResourceException;


public class oolalaViewLsystem extends oolalaView{

  private int numIterations;
  private CommandLine enterIterations;
  private CommandLine userLength;
  private CommandLine userAngle;
  private Color lineColor;

  public oolalaViewLsystem(String language){
    super(language);
    myModel = new oolalaModelLsystem(myResources);
  }

  /**
   * creates the control panel at the bottom of the screen for the user to interact with
   */
  @Override
  protected HBox makeControlPanel() {
    HBox controlPanel = new HBox();
    controlPanel.setId("controlPanel");
    enterIterations = new CommandLine(myLayout.getString("CommandLineX"),
            myLayout.getString("CommandLineY"),
            myLayout.getString("CommandLineSizeLSystem"), event -> {
      numIterations = Integer.parseInt(enterIterations.getText());
      enterIterations.setText("");
    }, myResources.getString("NumIterations"));
    enterIterations.setId("enterIterations");

    userLength = new CommandLine(myLayout.getString("CommandLineX"),
            myLayout.getString("CommandLineY"),
            myLayout.getString("CommandLineSizeLSystem"), event -> {
      myModel.setBaseLength(Integer.parseInt(userLength.getText()));
      userLength.setText("");
    }, myResources.getString("MoveLength"));
    userLength.setId("userLength");

    userAngle = new CommandLine(myLayout.getString("CommandLineX"),
            myLayout.getString("CommandLineY"),
            myLayout.getString("CommandLineSizeLSystem"), event -> {
      myModel.setBaseAngle(Integer.parseInt(userAngle.getText()));
      userAngle.setText("");
    }, myResources.getString("MoveAngle"));
    userAngle.setId("userAngle");

    Button inputFileButton = makeButton(myResources.getString("FileUpload"), value -> {
      fileReader.addToQueue(instructionQueue);
      handleFileUpload();
    });

    Button resetButton = makeButton(myResources.getString("Reset"), value -> resetCanvas());

    controlPanel.getChildren()
            .addAll(enterIterations, userLength, userAngle, inputFileButton, resetButton);
    return controlPanel;
  }

  /**
   * after a file has been uploaded, runs each command in file and then updates the symbolProps variable in the L System model
   */
  private void handleFileUpload() {
    for (String instruction : instructionQueue) {
      String[] inputParts = instruction.split("\\s+");
      tryCommand(inputParts);
    }
    instructionQueue.clear();
    myModel.addDefaultCommands();
    myModel.setCommandForLetter();
    drawSystem(myModel.getStartSymbol(), numIterations);
  }

  /**
   * overrides the superclass to empty out variables that correspond to L System
   */
  @Override
  protected void resetCanvas() {
    super.resetCanvas();
    myModel.getSymbolProps().clear();
    myModel.resetAlphabet();
  }

  /**
   * executes a command by updating the state of the turtle and running the animation on the turtle
   * @param input represents the command to be executed
   */
  @Override
  protected void executeLine(String input) {
    /**
     * executes the input by translating through model and then starting the animation.
     * if the input is invalid, the instructionQueue is cleared and no animation is run
     */
    String[] inputParts = input.split("\\s+");

    myModel.importTurtle(myTurtle.getCenterX(), myTurtle.getCenterY(),
            myTurtle.getHeading(), "description");

    tryCommand(inputParts);
    updateStates(myTurtle, input);

    int maxFrames = (int) Math.max(Math.abs(nextSpotX - myTurtle.getCenterX()),
            Math.abs(nextSpotY - myTurtle.getCenterY()));
    startAnimation(maxFrames, myTurtle);

    myModel.reset();
  }

  /**
   * recursive method to add all the instructions to the queue and subsequently animate them on the base case
   * this is the main logic behind L System - the recursive nature of the program
   * @param rule represents the rule that is currently in place from the file
   * @param iterations represents the number of recursive iterations
   */
  private void drawSystem(String rule, int iterations) {
    if (iterations == 0) {
      readInstrStack();
    }
    else {
      for (String letter : rule.split("")) {
        String[] commands = myModel.getArrayOfCommands(letter);
        instructionQueue.addAll(Arrays.asList(commands));
      }
      instructionQueue.add(myResources.getString("stop"));
      String nextRule = myModel.getNextRule(rule);
      drawSystem(nextRule, iterations-1);
    }
  }

  /**
   * checks to see if we are on a new iteration and thus changes the line color if we are
   */
  private void checkNewIteration() {
    if (myModel.getChangeColor()) {
      lineColor = Color.color(Math.random(), Math.random(), Math.random());
      if(!(currLine == null)) {
        currLine.setStroke(lineColor);
      }
    }
    myModel.setChangeColor(false);
  }

  /**
   * creates the line that the turtle will "draw"
   * @param turtle represents the current turtle in the canvas
   */
  @Override
  public void makeLine(Turtle turtle) {
    currLine = new Line(turtle.getCenterX(), turtle.getCenterY(), turtle.getCenterX(),
            turtle.getCenterY());
    currLine.setStroke(lineColor);
    currLine.setStrokeWidth(myModel.getThickness());
    myCanvas.getChildren().add(currLine);
  }

  /**
   * reads the stack of instructions in the queue
   * overrides the superclass to create a pop-up when the program is done running
   */
  @Override
  protected void readInstrStack() {
    /**
     * selects the next command in the instructionQueue.
     */
    if (instructionQueue.isEmpty()) {
      doneIterating(myResources.getString("DoneIterating"));
      return;
    }
    String instruction = instructionQueue.remove();
    executeLine(instruction);
  }

  /**
   * updates the state of the turtle based on an inputted command
   * called in executeLine()
   * @param currentTurtle the current turtle whose states are to be checked
   * @param input the command
   */
  @Override
  protected void updateStates(Turtle currentTurtle, String input) {
    super.updateStates(currentTurtle, input);
    checkNewIteration();
  }

  /**
   * creates a pop-up for the user once program has finished
   * @param message message to be shown in the pop-up
   */
  private void doneIterating(String message) {
    /**
     * Alert after program is finished.
     */
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(myResources.getString("Completed"));
    alert.setContentText(message);
    alert.show();
  }
}
