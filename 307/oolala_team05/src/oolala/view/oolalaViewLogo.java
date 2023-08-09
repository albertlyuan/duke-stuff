package oolala.view;


import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Queue;
import java.util.ResourceBundle;
import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import oolala.model.ModelException;
import oolala.model.oolalaModel;
import oolala.model.oolalaModelLogo;
import oolala.utils.FileReader;


public class oolalaViewLogo extends oolalaView {

  public oolalaViewLogo(String language) {
    super(language);
    myModel = new oolalaModelLogo(myResources);
  }

  public Scene makeScene(int width, int height) {
    BorderPane root = new BorderPane();
    root.setId("BorderPane");

    makeCanvas();
    controlPanel = makeControlPanel();
    myCommandHistory = new CommandHistory(myResources);

    root.setCenter(myCanvas);
    root.setBottom(controlPanel);
    root.setOnMouseClicked(event -> {
      if (!clickedTurtle) {
        myModel.setHomeOnClick(event.getSceneX(), event.getSceneY());
      }
    });

    root.setRight(myCommandHistory);
    myScene = new Scene(root, width, height);
    myScene.getStylesheets()
        .add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + CSS_STYLE).toExternalForm());
    return myScene;
  }

  @Override
  protected HBox makeControlPanel() {
    /**
     * makes control panel which includes: commandline, file input button, set image button, reset
     * button, and turtlestateDisplay
     */
    HBox controlPanel = new HBox();
    controlPanel.setId("controlPanel");
    commandLine = new CommandLine(myLayout.getString("CommandLineX"),
        myLayout.getString("CommandLineY"),
        myLayout.getString("CommandLineSize"), event -> {
      String input = commandLine.getText();
      handleCommand(input);
    }, "Enter tell command to start");

    Button inputFileButton = makeButton(myResources.getString("FileUpload"), value -> {
      fileReader.addToQueue(instructionQueue);
      System.out.println(instructionQueue);
      readInstrStack();
    });

    Button turtleImage = makeButton(myResources.getString("SetImage"),
        value -> myTurtle.setImage());
    Button resetButton = makeButton(myResources.getString("Reset"), value -> resetCanvas());

    myTurtleStateDisplay = new TurtleStateDisplay(myResources, myTurtle.getCenterX(),
        myTurtle.getCenterY(), myTurtle.getHeading());

    controlPanel.getChildren()
        .addAll(commandLine, inputFileButton, turtleImage, resetButton);
    return controlPanel;
  }

  @Override
  protected void resetCanvas() {
    /**
     * clears the canvas of all turtles and drawn lines
     */
    super.resetCanvas();
    activeTurtles.clear();
    tellTurtles.clear();
  }

  @Override
  protected void step(Turtle turtle) {
    /**
     * step function. moves the turtle, updates the display, and 'draws' the line if pen is down
     */
    super.step(turtle);
    myTurtleStateDisplay.updateDisplay(turtle.getCenterX(), turtle.getCenterY(),
        turtle.getHeading());
  }

  @Override
  protected void readInstrStack() {
    /**
     * selects the next command in the instructionQueue.
     */
    if (instructionQueue.isEmpty()) {
      return;
    }
    String instruction;
    while (true) {
      instruction = instructionQueue.remove();
      if (!instruction.equals("") && instruction.charAt(0) != '#') {
        break;
      }
      if (instructionQueue.isEmpty()) {
        return;
      }
    }
    executeLine(instruction);
  }

  @Override
  protected void executeLine(String input) {
    /**
     * executes the input by translating through model and then starting the animation.
     * if the input is invalid, the instructionQueue is cleared and no animation is run
     */
    String[] inputParts = input.split("\\s+");

    //adds turtles if tell is used
    tryTellCommand(inputParts);
    if (myModel.isTellCommand()) {
      executingIfTell(input, inputParts);
    } else {
      Turtle currentTurtle = activeTurtles.get(turtleCount);
      myModel.importTurtle(currentTurtle.getCenterX(), currentTurtle.getCenterY(),
          currentTurtle.getHeading(), "");

      tryCommand(inputParts);
      updateStates(currentTurtle, input);

      nextTurtleIndex();

      int maxFrames = (int) Math.max(Math.abs(nextSpotX - currentTurtle.getCenterX()),
          Math.abs(nextSpotY - currentTurtle.getCenterY()));
      startAnimation(maxFrames, currentTurtle);
    }
    myModel.reset();
  }

  private void nextTurtleIndex() {
    /**
     * iterates to the next turtle to be used if tell was executed before
     */
    if (turtleCount == activeTurtles.size() - 1) {
      turtleCount = 0;
    } else {
      turtleCount++;
    }
  }

  private void executingIfTell(String input, String[] inputParts) {
    /**
     * adds turtles if tell is executed
     */
    if (myCommandHistory.add(input)) {
      updateCommandHistory();
    }
    activeTurtles.clear();
    for (int i = 1; i < inputParts.length; i++) {
      int turtleNum = Integer.parseInt(inputParts[i]);
      if (!tellTurtles.containsKey(turtleNum)) {
        addNewTurtle(turtleNum);
      }
      if (!activeTurtles.contains(tellTurtles.get(turtleNum))) {
        activeTurtles.add(tellTurtles.get(turtleNum));
      }
    }
    Turtle currentTurtle = activeTurtles.get(turtleCount);
    startAnimation(0, currentTurtle);
  }

  private void addNewTurtle(int turtleNum) {
    /**
     * creates turtles based on the model's current locations
     * turtles are added to canvas
     */
    Turtle newTurtle = new Turtle(myModel.getHomeX(), myModel.getHomeY(), oolalaView.START_SIZE);
    newTurtle.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
      clickedTurtle = true;
      myTurtleStateDisplay.updateDisplay(newTurtle.getCenterX(), newTurtle.getCenterY(),
          newTurtle.getHeading());
    });
    clickedTurtle = false;
    tellTurtles.put(turtleNum, newTurtle);
    myCanvas.getChildren().add(newTurtle);
  }

  private void tryTellCommand(String[] inputParts) {
    /**
     * runs command if it is 'tell'
     */
    try {
      if (inputParts[0].equals("tell")) {
        myModel.runCommand(inputParts);
      }
    } catch (MissingResourceException | NoSuchMethodException e) {
      showError(myResources.getString("CommandError"), String.join(" ", inputParts));
      instructionQueue.clear();
    } catch (IllegalAccessException | InvocationTargetException e) {

    } catch (ModelException e) {

    }
  }
}
