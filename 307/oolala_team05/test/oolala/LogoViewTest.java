package oolala;

import java.lang.reflect.Field;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javax.swing.text.html.parser.Entity;
import oolala.utils.FileReader;
import oolala.view.CommandHistory;
import oolala.view.Turtle;
import oolala.view.oolalaView;
import oolala.view.oolalaViewLogo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Show alpha version of TestFX driving GUI for testing.
 *
 * @author Robert C. Duvall
 */
public class LogoViewTest extends DukeApplicationTest {

  public static final String DEFAULT_RESOURCE_PACKAGE = "oolala.resources.";
  String language = "English";
  private ResourceBundle myResources = ResourceBundle.getBundle(
      DEFAULT_RESOURCE_PACKAGE + language);
  // keep only if needed to call application methods in tests
  private oolalaView turtleGraphic;
  // keep GUI components used in multiple tests
  private Circle turtle;
  private TextField commandLine;
  private Group canvas;
  private BorderPane screen;


  // this method is run BEFORE EACH test to set up application in a fresh state
  @Override
  public void start(Stage stage) {
    // create application and add scene for testing to given stage

    turtleGraphic = new oolalaViewLogo(Main.DEFAULT_LANGUAGE);

    stage.setTitle(Main.MENU_TITLE);
    stage.setScene(turtleGraphic.makeScene(Main.DEFAULT_SIZE.width, Main.DEFAULT_SIZE.height));
    stage.show();

    // components that will be reused in different tests
    commandLine = lookup("#controlPanel #commandLine").query();
    canvas = lookup("#canvas").query();
    screen = lookup("#BorderPane").query();

  }

  @BeforeEach
  private void makeTurtle(){
    enterCommand("tell 1");
    clickOn(commandLine);
    this.press(KeyCode.ENTER);
    this.release(KeyCode.ENTER);
    try {
      Field field = oolalaView.class.getDeclaredField("activeTurtles");
      field.setAccessible(true);
      ArrayList<Turtle> activeTurtles = (ArrayList<Turtle>) field.get(turtleGraphic);
      turtle = activeTurtles.get(0);
    } catch (NoSuchFieldException | IllegalAccessException e) {

    }
  }
  @Test
  public void containsAllButtons() {
    HBox controls = lookup("#controlPanel").query();
    Button inputFile = lookup("Upload File").query();
    Button imageButton = lookup("Set Image").query();
    Button resetButton = lookup("Reset").query();

    assertTrue(controls.getChildren().contains(inputFile));
    assertTrue(controls.getChildren().contains(imageButton));
    assertTrue(controls.getChildren().contains(resetButton));
  }

  @Test
  public void resetButtonDefaultTest() {

    Line line = new Line(300, 400, 500, 600);
    Turtle turtle = new Turtle(400, 300, 10);
    runAsJFXAction(() -> canvas.getChildren().addAll(line, turtle));

    Button inputFile = lookup("Reset").query();
    clickOn(inputFile);

    assertEquals(0, canvas.getChildren().size());
  }

  @Test
  public void setImageButtonTest() {

    Button setImg = lookup("Set Image").query();
    clickOn(setImg);
    /**
     * next 3 lines are code found in the method to pick an image from computer
     */
    File selectedFile = new File("./data/examples/images/IMG_6295.JPG");
    Image image = new Image(selectedFile.toURI().toString());
    ImagePattern fillImg = new ImagePattern(image);

    turtle.setFill(fillImg);
    String img = turtle.getFill().toString().substring(0,31);
    assertEquals("javafx.scene.paint.ImagePattern", img);
  }

  @Test
  public void setHomeTest() {
    Button inputFile = lookup("Reset").query();
    clickOn(screen, 200, 200);
    clickOn(inputFile);

    assertEquals(399, (int) turtle.getCenterX());
    assertEquals(299, (int) turtle.getCenterY());
  }

  @Test
  public void readFileTest() {
    FileReader fr = new FileReader();

    File testFile = new File("./data/examples/logo/grid.txt");

    String[] expectedCmds = {"fd 100", "rt 90", "fd 100", "rt 90", "fd 100", "rt 90", "fd 100",
        "fd 100", "rt 90", "fd 100", "rt 90", "fd 100", "rt 90", "fd 100",
        "fd 100", "rt 90", "fd 100", "rt 90", "fd 100", "rt 90", "fd 100",
        "fd 100", "rt 90", "fd 100", "rt 90", "fd 100", "rt 90", "fd 100", "rt 90"};

    List<String> list = new ArrayList<>(Arrays.asList(expectedCmds));

    List<String> realCmds = fr.readFile(testFile);
    assertEquals(list, realCmds);
  }

  @Test
  public void lookForCommandHistory() {
    VBox commandHistoryPanel = lookup("#commandPanel").query();
    Label commandHistoryHeader = lookup("#commandHistory").query();

    assertTrue(commandHistoryPanel.getChildren().contains(commandHistoryHeader));
    assertTrue(screen.getChildren().contains(commandHistoryPanel));
  }

  @Test
  public void commandLineMove() {
    double startx, starty, endx, endy;

    startx = turtle.getCenterX();
    starty = turtle.getCenterY();
    enterCommand("fd 10");
    endx = turtle.getCenterX();
    endy = turtle.getCenterY();

    assertEquals(starty, endy, "turtle shouldn't move in y direction");
    assertEquals(10, endx - startx, "turtle should move +10 x");

    /**
     * various white space
     */
    startx = turtle.getCenterX();
    starty = turtle.getCenterY();
    enterCommand(" fd 10 ");
    endx = turtle.getCenterX();
    endy = turtle.getCenterY();

    assertEquals(starty, endy, "turtle shouldn't move in y direction");
    assertEquals(10, endx - startx, "trailing and starting white space: turtle should move +10 x");

    enterCommand("fd asdf");
  }

  @Test
  public void commandLineSquare() {
    double startx, starty, endx, endy;
    turtle.setCenterX(400);
    turtle.setCenterY(300);
    startx = turtle.getCenterX();
    starty = turtle.getCenterY();

    for (int i = 0; i < 4; i++) {
      enterCommand("fd 50");
      sleep(1000);
      enterCommand("rt 90");
    }

    boolean line1, line2, line3, line4;
    line1 = line2 = line3 = line4 = false;
    for (Node node : canvas.getChildren()) {
      String nodeType = node.toString().split("\\[")[0];
      String nodeInfo = node.toString().split("\\[")[1];
      if (nodeType.equals("Line")) {
        if (nodeInfo.equals(
            "startX=400.0, startY=300.0, endX=450.0, endY=300.0, stroke=0x000000ff, strokeWidth=1.0]")) {
          line1 = true;
        } else if (nodeInfo.equals(
            "startX=450.0, startY=300.0, endX=450.0, endY=350.0, stroke=0x000000ff, strokeWidth=1.0]")) {
          line2 = true;
        } else if (nodeInfo.equals(
            "startX=450.0, startY=350.0, endX=400.0, endY=350.0, stroke=0x000000ff, strokeWidth=1.0]")) {
          line3 = true;
        } else if (nodeInfo.equals(
            "startX=400.0, startY=350.0, endX=400.0, endY=300.0, stroke=0x000000ff, strokeWidth=1.0]")) {
          line4 = true;
        }
      }
    }
    endx = turtle.getCenterX();
    endy = turtle.getCenterY();
    boolean lineResult = line1 && line2 && line3 && line4;

    assertEquals(startx, endx, "turtle should be at starting x");
    assertEquals(starty, endy, "turtle should be at starting y");
    System.out.println(canvas.getChildren().toString());
    assertTrue(lineResult, "Wrong lines drawn");
  }


  @Test
  void testCommandHistory() {

    CommandHistory myCommandHistory = new CommandHistory(myResources);
    /**
     * Testing that duplicate commands are not added to the command history Queue
     */
    int expected1 = 3;
    String[] input1 = {"fd 40", "bk 40", "rt 90", "fd 40", "bk 40"};
    for (String cmd : input1) {
      myCommandHistory.add(cmd);
    }
    assertEquals(expected1, myCommandHistory.getCommandHistory().size());

    /**
     * Testing that when the command history Queue reaches its max size,
     * adding more commands updates command history to hold the <max size>
     * most recent commands. --* Current max size is 22 *--
     */
    myCommandHistory.getCommandHistory().removeAll(myCommandHistory.getCommandHistory());
    String expected2 = "fd 2";
    String[] input2 = {"fd 1", "fd 2", "fd 3", "fd 4", "fd 5", "fd 6", "fd 7", "fd 8",
        "fd 9", "fd 10", "fd 11", "fd 12", "fd 13", "fd 14", "fd 15",
        "fd 16", "fd 17", "fd 18", "fd 19", "fd 20", "fd 21", "fd 22", "fd 23"};

    for (String cmd : input2) {
      myCommandHistory.add(cmd);
    }
    assertEquals(expected2, myCommandHistory.getCommandHistory().element());

  }

  private void enterCommand(String input) {
    commandLine.setText(input);
    press(KeyCode.ENTER);
    release(KeyCode.ENTER);
  }
}

