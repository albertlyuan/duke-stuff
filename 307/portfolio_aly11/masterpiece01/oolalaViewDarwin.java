package oolala.view;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javafx.animation.Animation.Status;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import oolala.Main;
import oolala.model.oolalaModelDarwin;


public class oolalaViewDarwin extends oolalaView{
  private final String DARWIN_FILES = "data/examples/darwin/";
  private Random random = new Random();
  private final List<String> species = new ArrayList(Arrays.asList("a", "b", "c"));
  private final List<Color> colors = new ArrayList(Arrays.asList(Color.RED, Color.GREEN,Color.BLUE));
  private final List<String> files = new ArrayList(Arrays.asList(DARWIN_FILES+"rover.txt", DARWIN_FILES+"rover.txt",DARWIN_FILES+"rover.txt"));
  private final int totalCreatures = 10;

  private ArrayList<Turtle> unUsedTurtles = new ArrayList<>();
  private ArrayList<Turtle> usedTurtles = new ArrayList<>();
  private Turtle currTurtle;

  //runtime variables
  private int maxFrames;
  private String command;
  private Turtle displayTurtle;

  public oolalaViewDarwin(String language){
    super(language);
    myModel = new oolalaModelDarwin(myResources, Main.DEFAULT_SIZE.width, Main.DEFAULT_SIZE.height);

  }

  @Override
  protected HBox makeControlPanel() {
    /**
     * Overrides base controlpanel by adding play/pause button
     */
    HBox controlPanel = super.makeControlPanel();
    Button pauseButton = new Button("Play");
    pauseButton.setId("play");
    pauseButton.setOnAction(event -> {
      if (myAnimation != null) {
        if (myAnimation.getStatus().equals(Status.RUNNING)){
          myAnimation.pause();
          pauseButton.setText("Play");
        }else{
          myAnimation.play();
          pauseButton.setText("Pause");
        }
      }else{
        selectTurtle();
        pauseButton.setText("Pause");
      }
    });
    controlPanel.getChildren().add(pauseButton);
    return controlPanel;
  }

  private void selectTurtle() {
    /**
     * randomly selects a turtle that hasn't been used in the current round
     * The turtle's state is sent to model, and the turtle's next instruction is executed
     */
    if (unUsedTurtles.isEmpty()){
      for (Turtle turtle : usedTurtles){
        turtle.setInstrIdx(0);
        unUsedTurtles.add(turtle);
      }
      usedTurtles.clear();
    }

    currTurtle = unUsedTurtles.get(random.nextInt(unUsedTurtles.size()));
    unUsedTurtles.remove(currTurtle);
    usedTurtles.add(currTurtle);


    myModel.importTurtle(currTurtle.getCenterX(), currTurtle.getCenterY(), currTurtle.getHeading(),currTurtle.getSpecies());

    readInstrStack();
  }

  @Override
  protected void readInstrStack() {
    /**
     * Overrides base readinstruction stack
     * instead of reading instructionQueue, we just read the turtle's next command
     */
    try{
      command = currTurtle.getNextCommand();
      tryCommand(command.split("\\s+"));
      updateStates();
      maxFrames = (int) Math.max(Math.abs(nextSpotX - currTurtle.getCenterX()),
          Math.abs(nextSpotY - currTurtle.getCenterY()));
      startAnimation(maxFrames, currTurtle);
    } catch (IndexOutOfBoundsException e){
    }
  }
  @Override
  protected void resetCanvas() {
    /**
     * Overrides the base resetcanvas
     * clears all turtle data in model and view
     * creates new turtle data in model and view
     */
    myCanvas.getChildren().clear();
    myAnimation = null;
    unUsedTurtles.clear();
    usedTurtles.clear();
    myModel.reset();
    populateCanvas();
  }

  @Override
  protected void doAfterAnimation(){
    /**
     * overrides base doAfterAnimation
     * instead of calling get readInstrQueue,
     * we check condition for actions and all turtles used to iterate through all turtles
     */
    if (unUsedTurtles.isEmpty()){
      selectTurtle();
    } else if (myModel.didAction()){
      selectTurtle();
    }else{
      readInstrStack();
    }
  }

  private void updateStates() {
    /**
     * updates the turtle's states with info from model
     */
    nextSpotX = myModel.getX();
    nextSpotY = myModel.getY();

    if (myModel.getHeading() != currTurtle.getHeading()){
      currTurtle.setHeading(myModel.getHeading());
    }
    if (myModel.getConditionalState()){
      currTurtle.setInstrIdx(myModel.getJumpTo());
    }
    if (myModel.getInfectedCreatures().size() > 0){
      findInfected(myModel.getInfectedCreatures());
    }
  }

  private void findInfected(List<Double> infectedCreatures) {
    /**
     * if a creature has been infected, finds the infected creature
     * and updates its states to match those of its new species
     */
    String newSpecies = currTurtle.getSpecies();
    double turtleposition;
    for (ArrayList<Turtle> turtleList : Arrays.asList(unUsedTurtles,usedTurtles)){
      for (Turtle turtle : turtleList){
        turtleposition = turtle.getCenterY()*Main.DEFAULT_SIZE.width + turtle.getCenterX();
        if (infectedCreatures.contains(turtleposition)){
          updateInfected(turtle, newSpecies);
        }
      }
    }

  }

  private void updateInfected(Turtle turtle, String newSpecies) {
    /**
     * updates an infected creature to its new species and behavior.
     */
    int speciesIdx = 0;
    while (speciesIdx < species.size()){
      if (species.get(speciesIdx).equals(newSpecies)){
        break;
      }
      speciesIdx++;
    }
    turtle.setSpecies(newSpecies);
    turtle.setInstrIdx(0);
    turtle.setFill(colors.get(speciesIdx));
    turtle.setBaseCode(fileReader.readFile(new File(files.get(speciesIdx))));

  }

  @Override
  protected void makeCanvas(){
    /**
     * overrides base makeCanvas by also populating the canvas with totalCreatures creatures
     */
    super.makeCanvas();
    populateCanvas();
  }

  private void populateCanvas(){
    /**
     * Randomly generates locations, species, and headings to make totalCreature turtles
     */
    for (int i = 0; i < totalCreatures; i++){
      int speciesIdx = random.nextInt(species.size());
      int randomX = random.nextInt(Main.DEFAULT_SIZE.width);
      int randomY = random.nextInt(Main.DEFAULT_SIZE.height);
      int randomHeading = random.nextInt(360);
      addNewTurtle(speciesIdx, randomX, randomY,randomHeading);
      myModel.importTurtle(randomX,randomY,randomHeading, species.get(speciesIdx));
    }
  }

  private void addNewTurtle(int speciesIdx, int randomX, int randomY, int randomHeading){
    /**
     * creates new turtle with random heading, location, and species and logs their existance
     * in model and in view
     */
    String type = species.get(speciesIdx);
    Color speciesColor = colors.get(speciesIdx);
    List<String> behavior = fileReader.readFile(new File(files.get(speciesIdx)));
    Turtle newTurtle = new DarwinTurtle(randomX, randomY, oolalaView.START_SIZE, type, behavior);
    newTurtle.setHeading(randomHeading);
    newTurtle.setFill(speciesColor);
    handleStateDisplay(newTurtle);
    unUsedTurtles.add(newTurtle);
    displayTurtle = newTurtle;
    myCanvas.getChildren().add(newTurtle);
  }

  private void handleStateDisplay(Turtle newTurtle) {
    /**
     * adds event for each turtle so that when clicked, the myTurtleStateDisplay will display its info
     */
    newTurtle.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
      clickedTurtle = true;
      displayTurtle = newTurtle;
      if (myTurtleStateDisplay == null){
        myTurtleStateDisplay = new TurtleStateDisplay(myResources, newTurtle.getCenterX(), newTurtle.getCenterY(),
            newTurtle.getHeading());
        controlPanel.getChildren().add(myTurtleStateDisplay);
      }
      myTurtleStateDisplay.updateDisplay(newTurtle.getCenterX(), newTurtle.getCenterY(),
          newTurtle.getHeading());
    });
  }

  @Override
  protected void step(Turtle turtle) {
    /**
     * overriden step function.
     * Handles turtlestatedisplay when it's undefined until a turtle is clicked
     */
    super.step(turtle);
    if (myTurtleStateDisplay != null) {
      myTurtleStateDisplay.updateDisplay(displayTurtle.getCenterX(), displayTurtle.getCenterY(),
          displayTurtle.getHeading());
    }
  }
}
