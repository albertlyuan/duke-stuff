package oolala.view;


import java.util.MissingResourceException;
import javafx.animation.Animation;
import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import oolala.model.ModelException;
import oolala.model.oolalaModelLogo;
import oolala.utils.FileReader;
import oolala.model.oolalaModel;

import java.lang.reflect.InvocationTargetException;
import java.util.*;


public abstract class oolalaView {

  //constants
  public static final double START_SIZE = 10;
  protected static final double FRAME_RATE = 0.01;
  public static final String DEFAULT_RESOURCE_PACKAGE = "oolala.resources.";
  public static final String DEFAULT_RESOURCE_FOLDER =
      "/" + DEFAULT_RESOURCE_PACKAGE.replace(".", "/");
  public static final String CSS_STYLE = "default.css";
  public static final String DEFAULT_VIEW_LAYOUT = "viewLayout";


  //display variables
  protected Scene myScene;
  protected Group myCanvas;
  protected HBox controlPanel;
  protected CommandLine commandLine;
  protected TurtleStateDisplay myTurtleStateDisplay;
  protected CommandHistory myCommandHistory;
  protected HashMap<Integer, Turtle> tellTurtles;
  protected List<Turtle> activeTurtles;

  //runtime variables
  protected Turtle myTurtle;
  protected Line currLine;
  protected double nextSpotX;
  protected double nextSpotY;
  protected int turtleCount;
  protected boolean clickedTurtle;

  protected Queue<String> instructionQueue;
  protected oolalaModel myModel;
  protected Timeline myAnimation;
  protected FileReader fileReader;
  protected ResourceBundle myResources;
  protected ResourceBundle myLayout;


  public oolalaView(String language) {
    myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
    //change app
    fileReader = new FileReader();
    instructionQueue = new LinkedList<>();
    myLayout = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + DEFAULT_VIEW_LAYOUT);
    tellTurtles = new HashMap<>();
    activeTurtles = new ArrayList<>();
    turtleCount = 0;
  }

  public Scene makeScene(int width, int height) {
    BorderPane root = new BorderPane();
    root.setId("BorderPane");

    makeCanvas();
    controlPanel = makeControlPanel();
    myCommandHistory = new CommandHistory(myResources);

    root.setCenter(myCanvas);
    root.setBottom(controlPanel);

    root.setRight(myCommandHistory);
    myScene = new Scene(root, width, height);
    myScene.getStylesheets()
        .add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + CSS_STYLE).toExternalForm());
    return myScene;
  }

  protected void updateCommandHistory() {
    /**
     * updates command history by remaking it to only include the 20 most recent unique commands
     */
    myCommandHistory.getChildren().removeIf(node -> node instanceof Button);
    for (String command : myCommandHistory.getCommandHistory()) {
      Button commandButton = new Button(command);
      commandButton.setOnAction(event -> executeLine(command));
      myCommandHistory.getChildren().add(commandButton);
    }
  }

  protected void makeCanvas() {
    myCanvas = new Group();
    myCanvas.setId("canvas");
    myTurtle = new Turtle(myModel.getX(), myModel.getY(), START_SIZE);
  }

  protected HBox makeControlPanel() {
    /**
     * makes control panel. includes commandline, reset button, and file upload button
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

    controlPanel.getChildren()
        .addAll(commandLine, inputFileButton, turtleImage, resetButton);
    return controlPanel;
  }

  protected void handleCommand(String input) {
    /**
     * handles commmand by cleaning up white spaces and adding it to the instructionQueue.
     * Then initiates calling readInstrStack
     */
    if (input.strip().equals("")) {
      commandLine.setText("");
      return;
    }
    String command = input.split("\\s+")[0];

    if (command.equals("tell")) {
      instructionQueue.add(input.strip());
    } else {
      for (Turtle turtle : activeTurtles) {
        instructionQueue.add(input.strip());
      }
    }
    commandLine.setText("");
    if (myAnimation == null || myAnimation.getStatus().equals(Status.STOPPED)) {
      readInstrStack();
    }

  }

  protected Button makeButton(String label, EventHandler<ActionEvent> event) {
    Button button = new Button(label);
    button.setOnAction(event);
    return button;
  }

  protected void resetCanvas() {
    myCanvas.getChildren().clear();
  }

  protected void step(Turtle turtle) {
    /**
     * step function for animation. moves the turtle and 'draws' the line by changing its endpoint
     */
    turtle.move(nextSpotX, nextSpotY);
    drawLine(turtle);
  }

  protected void readInstrStack() {

  }

  protected void executeLine(String input) {
  }

  protected void updateStates(Turtle currentTurtle, String input) {
    /**
     * updates the turtle's state from the model calculations
     */
    checkIfRotate(currentTurtle);
    currentTurtle.setPenStatus(myModel.getPenStatus());
    nextSpotX = myModel.getX();
    nextSpotY = myModel.getY();
    checkCmdHistory(input);
    checkPenStatus(currentTurtle);
    checkVisibility(currentTurtle);
    checkGoHome();
    checkStamped(currentTurtle);
  }

  protected void checkPenStatus(Turtle currentTurtle) {
    /**
     * checks if pen is down, then creates a new line to 'draw'
     */
    if (currentTurtle.getPenStatus()) {
      makeLine(currentTurtle);
    }
  }

  protected void checkCmdHistory(String input) {
    /**
     * checks that command history is updated when a new unique command is used
     */
    if (myCommandHistory.add(input)) {
      updateCommandHistory();
    }

  }

  protected void checkStamped(Turtle currentTurtle) {
    /**
     * checks that stamp was the command used. if so, adds a stamp to canvas
     */
    if (myModel.getStamped()) {
      Turtle newTurtle = new Turtle(currentTurtle.getCenterX(), currentTurtle.getCenterY(),
          START_SIZE);
      newTurtle.setFill(currentTurtle.getFill());
      myCanvas.getChildren().add(newTurtle);
      myModel.setStamped(false);
    }
  }

  protected void checkGoHome() {
    /**
     * checks if home command is used. if so, sets the turtle to go to the home coordinates
     */
    if (myModel.getGoHome()) {
      currLine = null;
      nextSpotX = myModel.getHomeX();
      nextSpotY = myModel.getHomeY();
    }
  }

  protected void checkVisibility(Turtle currentTurtle) {
    /**
     * checks if show/hide turtle is used. if so, removes/adds the turtle from canvas
     */
    if (!myModel.getTurtleVisible()) {
      myCanvas.getChildren().remove(currentTurtle);
    } else if (!myCanvas.getChildren().contains(currentTurtle)) {
      myCanvas.getChildren().add(currentTurtle);
    }
  }

  protected void checkIfRotate(Turtle currentTurtle) {
    /**
     * checks if the heading is changed. if so turtle is updated.
     */
    if (myModel.isRotateCommand()) {
      currentTurtle.setHeading(myModel.getHeading());
    }
  }

  protected void tryCommand(String[] inputParts) {
    /**
     * runs the command through the model, and handles errors
     */
    try {
      myModel.runCommand(inputParts);
    } catch (MissingResourceException | NoSuchMethodException e) {
      showError(myResources.getString("CommandError"), String.join(" ", inputParts));
      instructionQueue.clear();
    } catch (IllegalAccessException | InvocationTargetException e) {

    } catch (ModelException e) {

    }
  }

  protected void startAnimation(int frames, Turtle turtle) {
    /**
     * starts the animation that moves the turtle (and line if pen down)
     * lasts for pixels*FRAME_RATE seconds. upon completion, if there is another command
     * in instruction stack, it is executed.
     */
    if (myAnimation != null) {
      myAnimation.stop();
    }

    myAnimation = new Timeline();
    myAnimation.setCycleCount(frames);
    myAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(FRAME_RATE), e -> step(turtle)));
    myAnimation.setOnFinished(e -> {
      doAfterAnimation();
    });
    myAnimation.play();
  }

  protected void doAfterAnimation() {
    /**
     * actions to be done after animation for a command is complete.
     */
    currLine = null;
    readInstrStack();
  }

  public void drawLine(Turtle turtle) {
    /**
     * updates the line's endpoint to make it look like a line is being drawn
     */
    if (currLine != null) {
      currLine.setEndX(turtle.getCenterX());
      currLine.setEndY(turtle.getCenterY());
    }
  }

  public void makeLine(Turtle turtle) {
    /**
     * creates new line object and adds it to canvas
     */
    currLine = new Line(turtle.getCenterX(), turtle.getCenterY(), turtle.getCenterX(),
        turtle.getCenterY());
    currLine.setStrokeWidth(myModel.getThickness());
    myCanvas.getChildren().add(currLine);
  }

  public void showError(String message, String command) {
    /**
     * Alert for invalid commands.
     */
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(myResources.getString("CommandError"));
    alert.setContentText(String.format("%s: %s", message, command));
    alert.show();
  }
}
