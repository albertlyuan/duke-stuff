package oolala;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.animation.Animation.Status;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import oolala.model.oolalaModelDarwin;
import oolala.model.oolalaModelLogo;
import oolala.utils.FileReader;
import oolala.view.CommandHistory;
import oolala.view.DarwinTurtle;
import oolala.view.Turtle;
import oolala.view.oolalaView;
import oolala.view.oolalaViewDarwin;
import oolala.view.oolalaViewLogo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

/**
 * Show alpha version of TestFX driving GUI for testing.
 *
 * @author Robert C. Duvall
 */
public class DarwinViewTest extends DukeApplicationTest {

  public static final String DEFAULT_RESOURCE_PACKAGE = "oolala.resources.";
  String language = "English";
  private ResourceBundle myResources = ResourceBundle.getBundle(
      DEFAULT_RESOURCE_PACKAGE + language);
  // keep only if needed to call application methods in tests
  private oolalaViewDarwin turtleGraphic;
  // keep GUI components used in multiple tests
  private Button playButton;
  private Group canvas;
  private BorderPane screen;

  // this method is run BEFORE EACH test to set up application in a fresh state
  @Override
  public void start(Stage stage) {
    // create application and add scene for testing to given stage
    turtleGraphic = new oolalaViewDarwin(Main.DEFAULT_LANGUAGE);

    stage.setTitle(Main.MENU_TITLE);
    stage.setScene(turtleGraphic.makeScene(Main.DEFAULT_SIZE.width, Main.DEFAULT_SIZE.height));
    stage.show();

    // components that will be reused in different tests
    playButton = lookup("#controlPanel #play").query();
    canvas = lookup("#canvas").query();
    screen = lookup("#BorderPane").query();

  }

  @Test
  void testPlayButton() throws NoSuchFieldException, IllegalAccessException {
    clickOn(playButton);
    Field field = turtleGraphic.getClass().getSuperclass().getDeclaredField("myAnimation");
    field.setAccessible(true);
    Timeline myAnimation = (Timeline) field.get(turtleGraphic);

    assertEquals(Status.RUNNING, myAnimation.getStatus(), "animation should be running: " + myAnimation.getStatus());
    sleep(1000);
    clickOn(playButton);
    assertEquals(Status.STOPPED, myAnimation.getStatus(), "animation shouldn't be running: " + myAnimation.getStatus());
  }

  @Test
  void checkPopulated () throws NoSuchFieldException, IllegalAccessException {
    Field field = turtleGraphic.getClass().getDeclaredField("totalCreatures");
    field.setAccessible(true);
    int expectedTotalCreatures = (int) field.get(turtleGraphic);
    int actualTotalCreatures = 0;
    for (Node n : canvas.getChildren()){
      if (n instanceof DarwinTurtle){
        actualTotalCreatures += 1;
      }
    }
    assertEquals(expectedTotalCreatures,actualTotalCreatures,"expected " + expectedTotalCreatures + "turtles. got: " + actualTotalCreatures);
  }

}

