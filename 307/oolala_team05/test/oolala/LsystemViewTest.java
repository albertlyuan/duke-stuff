package oolala;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import oolala.view.Turtle;
import oolala.view.oolalaView;
import oolala.model.oolalaModelLsystem;
import oolala.view.oolalaViewDarwin;
import oolala.view.oolalaViewLsystem;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

/**
 * Show alpha version of TestFX driving GUI for testing.
 *
 * @author Robert C. Duvall
 */
public class LsystemViewTest extends DukeApplicationTest {

  public static final String DEFAULT_RESOURCE_PACKAGE = "oolala.resources.";
  String language = "English";
  private ResourceBundle myResources = ResourceBundle.getBundle(
          DEFAULT_RESOURCE_PACKAGE + language);
  // keep only if needed to call application methods in tests
  private oolalaView turtleGraphic;
  // keep GUI components used in multiple tests
  private Circle turtle;
  private TextField enterIterations;
  private TextField enterLength;
  private TextField enterAngle;
  private Group canvas;
  private BorderPane screen;

  // this method is run BEFORE EACH test to set up application in a fresh state
  @Override
  public void start(Stage stage) {
    // create application and add scene for testing to given stage
    turtleGraphic = new oolalaViewLsystem(Main.DEFAULT_LANGUAGE);

    stage.setTitle(Main.MENU_TITLE);
    stage.setScene(turtleGraphic.makeScene(Main.DEFAULT_SIZE.width, Main.DEFAULT_SIZE.height));
    stage.show();

    // components that will be reused in different tests
    enterIterations = lookup("#controlPanel #enterIterations").query();
    enterLength = lookup("#controlPanel #userLength").query();
    enterAngle = lookup("#controlPanel #userAngle").query();
    canvas = lookup("#canvas").query();
    screen = lookup("#BorderPane").query();

  }

  @Test
  void enterNumIterations() throws NoSuchFieldException, IllegalAccessException {
    enterCommand("3", enterIterations);
    assertEquals("3", enterIterations.getText());

    Field number = turtleGraphic.getClass().getDeclaredField("numIterations");
    number.setAccessible(true);
    int iterations = (int) number.get(turtleGraphic);
    assertEquals(0, iterations);
  }

  @Test
  void enterLength() throws NoSuchFieldException, IllegalAccessException {
    enterCommand("10", enterLength);
    assertEquals("10", enterLength.getText());

    Field number = turtleGraphic.getClass().getSuperclass().getDeclaredField("myModel");
    number.setAccessible(true);
    oolalaModelLsystem system = (oolalaModelLsystem) number.get(turtleGraphic);
    assertEquals(10, system.getBaseLength());
  }

  @Test
  void enterAngle() throws NoSuchFieldException, IllegalAccessException {
    enterCommand("30", enterLength);
    assertEquals("30", enterLength.getText());

    Field number = turtleGraphic.getClass().getSuperclass().getDeclaredField("myModel");
    number.setAccessible(true);
    oolalaModelLsystem system = (oolalaModelLsystem) number.get(turtleGraphic);
    assertEquals(30, system.getBaseAngle());
  }

  @Test
  void containsAllButtons() {
    HBox controls = lookup("#controlPanel").query();
    Button inputFile = lookup("Upload File").query();
    Button resetButton = lookup("Reset").query();

    assertTrue(controls.getChildren().contains(inputFile));
    assertTrue(controls.getChildren().contains(resetButton));
  }

  private void enterCommand(String input, TextField commandLine) {
    commandLine.setText(input);
    press(KeyCode.ENTER);
    release(KeyCode.ENTER);
  }
}

