package oolala.model;

import static java.lang.Integer.parseInt;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Random;
import java.util.ResourceBundle;
import oolala.view.oolalaView;

public class oolalaModelDarwin extends oolalaModel {

  //boarder of canvas
  private int Xmax, Ymax;

  //stores locations that are occupied as Y*Xmax + X
  private HashMap<Double, String> TurtleLocations;
  private boolean conditionalState;
  private String currSpecies;
  private Random random;
  private int jumpTo;
  private boolean action;
  private List<Double> infected;
  private double currPosition;

  public oolalaModelDarwin(ResourceBundle resources, int xRange, int yRange) {
    super(resources);
    Xmax = xRange;
    Ymax = yRange;
    TurtleLocations = new HashMap<>();
    random = new Random();
    infected = new ArrayList<>();
  }

  @Override
  public void importTurtle(double x, double y, double heading, String species) {
    /**
     * copies down the turtle's current state
     * and resets all flags used to detect action,conditionals,infected
     */
    super.importTurtle(x, y, heading, species);
    currSpecies = species;
    currPosition = Xmax * TurtleY + TurtleX;
    TurtleLocations.putIfAbsent(currPosition, currSpecies);
    if (!TurtleLocations.get(currPosition).equals(currPosition)) {
      currSpecies = TurtleLocations.get(currPosition);
    }
    conditionalState = false;
    action = false;
    infected.clear();
  }

  @Override
  public void runCommand(String[] input)
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, MissingResourceException {
    /**
     * runs command using reflection of string input
     */
    conditionalState = false;
    String translatedCommand = myResources.getString(input[0].toLowerCase(Locale.ROOT).strip());
    Method m = oolalaModelDarwin.class.getDeclaredMethod(translatedCommand, String[].class);
    m.invoke(this, new Object[]{input});
  }

  @Override
  public void reset() {
    /**
     * removes locations of all turtles
     */
    TurtleLocations = new HashMap<>();
  }

  private void move(String[] input) throws ModelException {
    /**
     * command that moves turtle x pixels
     * if turtle tries to move outside the boundary, the turtle remains at its original spot
     * updates turtleLocations
     * is an action
     */
    double radius = oolalaView.START_SIZE;
    double oldX = TurtleX;
    double oldY = TurtleY;
    super.fd(input);
    double oldLocation = Xmax * oldY + oldX;
    double newLocation = Xmax * TurtleY + TurtleX;
    if (TurtleX > (Xmax - radius) || TurtleX < (0 + radius) || TurtleY > (Ymax - radius)
        || TurtleY < (0
        + radius) || TurtleLocations.containsKey(newLocation)) {
      TurtleX = oldX;
      TurtleY = oldY;
    } else {
      TurtleLocations.remove(oldLocation);
      //need to import species before command is inputted
      TurtleLocations.put(newLocation, currSpecies);
    }
    action = true;
  }

  private void left(String[] input) {
    /**
     * same as base lt
     * is an action
     */
    super.lt(input);
    action = true;
  }

  private void right(String[] input) {
    /**
     * same as base rt
     * is an action
     */
    super.rt(input);
    action = true;
  }

  private void infect(String[] input) {
    /**
     * finds nearby turtles and infects them if the species is different.
     * infected turtles are added to infected
     * is an action
     */
    for (double position : TurtleLocations.keySet()) {
      if (position == currPosition){
        continue;
      }
      if (checkProximity(position)) {
        if (!TurtleLocations.get(position).equals(currSpecies)) {
          TurtleLocations.put(position, currSpecies);
          infected.add(position);
        }
      }
    }
    action = true;
  }

  private void ifempty(String[] input) {
    /**
     * raises the conditionalstate flag if none are near
     * sets jumpto
     */

    for (double position : TurtleLocations.keySet()) {
      if (position == currPosition){
        continue;
      }
      if (checkProximity(position)) {
        conditionalState = false;
        return;
      }
    }
    conditionalState = true;
    jumpTo = parseInt(input[1]);
  }

  private void ifwall(String[] input) {
    /**
     * tests if the turtle is heading into a wall. if so, conditionalstate is raised
     * sets jumpto
     */
    double radius = oolalaView.START_SIZE;
    double northWallDist = TurtleY - radius;
    double southWallDist = Ymax - (TurtleY + radius);
    double westWallDist = TurtleX - radius;
    double eastWallDist = Xmax - (TurtleX + radius);
    double[] distances = {northWallDist, eastWallDist, southWallDist, westWallDist};

    int leftIdx = (int) Math.ceil((TurtleHeading - 90) / 90) % 4;
    if (leftIdx < 0) {
      leftIdx += 4;
    }
    int rightIdx = (int) Math.floor((TurtleHeading + 90) / 90) % 4;
    if (distances[leftIdx] <= 0 || distances[rightIdx] <= 0) {
      conditionalState = true;
      jumpTo = parseInt(input[1]);
      return;
    }
    if (TurtleHeading % 90 == 0) {
      if (distances[leftIdx + 1] <= 0) {
        conditionalState = true;
        jumpTo = parseInt(input[1]);
        return;
      }
    }
    conditionalState = false;
  }

  private void ifsame(String[] input) {
    /**
     * finds nearby turtles and raises conditionalstate flag if the turtle is the same species
     * sets jumpto
     */
    for (double position : TurtleLocations.keySet()) {
      if (position == currPosition){
        continue;
      }
      if (checkProximity(position)) {
        if (TurtleLocations.get(position).equals(currSpecies)) {
          conditionalState = true;
          jumpTo = parseInt(input[1]);
          return;
        }
      }
    }
    conditionalState = false;
  }

  private void ifenemy(String[] input) {
    /**
     * finds nearby turtles and raises conditionalstate flag if the turtle is a different species
     * sets jumpto
     */
    for (double position : TurtleLocations.keySet()) {
      if (position == currPosition){
        continue;
      }
      if (checkProximity(position)) {
        if (!TurtleLocations.get(position).equals(currSpecies)) {
          conditionalState = true;
          jumpTo = parseInt(input[1]);
          return;
        }
      }
    }
    conditionalState = false;
  }

  private void ifrandom(String[] input) {
    /**
     * randomly sets conditionalstate flag to true/false
     * sets jumpto
     */
    conditionalState = random.nextBoolean();
    if (conditionalState) {
      jumpTo = parseInt(input[1]);
    }
  }

  private void go(String[] input) {
    /**
     * sets conditionalstate to true
     * sets jumpto
     */
    conditionalState = true;
    jumpTo = parseInt(input[1]);
  }

  private boolean checkProximity(double position) {
    /**
     * given a turtle's position, finds distance from current turtle.
     * Also checks if turtle in front of current turtle. if so, returns true.
     */
    double x, y, dist;
    x = position % Xmax;
    y = Math.floor(position / Xmax);
    dist = Math.sqrt((x - TurtleX) * (x - TurtleX) + (y - TurtleY) * (y - TurtleY));

    return dist <= 20 && checkAngle(x, y);
  }

  private boolean checkAngle(double x, double y) {
    /**
     * checks that the given turtle is infront of the current turtle using dot products
     */
    //make the currTurtle (0,0)
    double aX = x - TurtleX;
    double aY = y - TurtleY;
    double aMag = Math.sqrt(aX * aX + aY * aY);

    double modifiedHeading = 360 - TurtleHeading;
    double bX = Math.cos((modifiedHeading * Math.PI) / 180) * 10;
    double bY = Math.sin((modifiedHeading * Math.PI) / 180) * 10;
    double bMag = Math.sqrt(bX * bX + bY * bY);

    double cosineRule = Math.acos((aX * bX + aY * bY) / (aMag * bMag));
    double angle = (cosineRule * 180) / Math.PI;

    return angle <= 90;
  }

  @Override
  public boolean getConditionalState() {
    return conditionalState;
  }

  @Override
  public String getSpecies() {
    return currSpecies;
  }

  @Override
  public int getJumpTo() {
    return jumpTo - 1;
  }

  @Override
  public boolean didAction() {
    return action;
  }

  @Override
  public List<Double> getInfectedCreatures() {
    return infected;
  }
}
