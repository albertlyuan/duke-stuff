package oolala;

import java.util.MissingResourceException;
import oolala.model.ModelException;
import oolala.model.oolalaModel;
import oolala.model.oolalaModelLogo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ResourceBundle;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;
import util.DukeApplicationTest;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Feel free to completely change this code or delete it entirely.
 */
class LogoModelTest extends DukeApplicationTest {

  // how close do real valued numbers need to be to count as the same
  static final double TOLERANCE = 0.0005;
  oolalaModelLogo model;
  public static final String DEFAULT_RESOURCE_PACKAGE = "oolala.resources.";
  public static final String DEFAULT_RESOURCE_FOLDER =
      "/" + DEFAULT_RESOURCE_PACKAGE.replace(".", "/");
  String language = "English";
  private ResourceBundle myResources = ResourceBundle.getBundle(
      DEFAULT_RESOURCE_PACKAGE + language);

  @BeforeEach
  void setup() {
    model = new oolalaModelLogo(myResources);
  }

  @Test
  void testTranslatePuPd() {
    String input;
    boolean newStatus;
    /**
     * testing pu
     */
    input = "pu";
    try {
      model.runCommand(input.split("\\s+"));
    } catch (Exception e) {
      e.printStackTrace();
    }
    newStatus = model.getPenStatus();

    assertFalse(newStatus, "pen up got: " + newStatus);
    /**
     * testing pd
     */
    input = "pd";

    try {
      model.runCommand(input.split("\\s+"));
    } catch (Exception e) {
      e.printStackTrace();
    }
    newStatus = model.getPenStatus();
    assertTrue(newStatus, "pen down got: " + newStatus);
  }

  @Test
  void testResetHome() {
    /**
     * testing sh
     */
    String input = "sh 300 750";

    try {
      model.runCommand(input.split("\\s+"));
    } catch (Exception e) {
      e.printStackTrace();
    }

    assertEquals(300, model.getHomeX());
    assertEquals(750, model.getHomeY());
    assertEquals(0, model.getHeading());
    assertEquals(1, model.getThickness());
  }

  @Test
  void testTurtleVisibility() {
    String input;
    boolean shown;
    /**
     * testing st
     */
    input = "st";

    try {
      model.runCommand(input.split("\\s+"));
    } catch (Exception e) {
      e.printStackTrace();
    }
    shown = model.getTurtleVisible();

    assertTrue(shown, "show turtle got: " + shown);

    /**
     * testing ht
     */
    input = "ht";

    try {
      model.runCommand(input.split("\\s+"));
    } catch (Exception e) {
      e.printStackTrace();
    }
    shown = model.getTurtleVisible();

    assertFalse(shown, "show turtle got: " + shown);
  }

  /**
   * Testing the fd() method from oolalaModel.
   */
  @Test
  void testTranslateMovement() {
    String input;
    double start, end, delta;
    /**
     * testing fd
     */
    input = "fd 50";

    start = model.getX();
    try {
      model.runCommand(input.split("\\s+"));
    } catch (Exception e) {
      e.printStackTrace();
    }
    end = model.getX();
    delta = end - start;

    assertEquals(50, delta, "moving +50 X, got: " + delta);
    /**
     * testing bk
     */
    input = "bk 50";

    start = model.getX();
    try {
      model.runCommand(input.split("\\s+"));
    } catch (Exception e) {
      e.printStackTrace();
    }
    end = model.getX();
    delta = end - start;

    assertEquals(-50, delta, "moving -50 X, got: " + delta);

  }

  @Test
  void testTranslateTurns() {
    String input;
    double start, end, delta;
    /**
     * testing rt
     */
    input = "rt 50";

    start = model.getHeading();
    try {
      model.runCommand(input.split("\\s+"));
    } catch (Exception e) {
      e.printStackTrace();
    }
    end = model.getHeading();
    delta = end - start;

    assertEquals(50, delta, "moving +50 degrees, got: " + delta);
    /**
     * testing lt
     */
    input = "lt 460";

    start = model.getHeading();
    try {
      model.runCommand(input.split("\\s+"));
    } catch (Exception e) {
      e.printStackTrace();
    }
    end = model.getHeading();
    delta = end - start;

    assertEquals(-100, delta, "moving -100 degrees, got: " + delta);
  }

  @Test
  void testHome() {
    String input;
    boolean homeStatus;
    /**
     * testing home
     */
    input = "home";

    try {
      model.runCommand(input.split("\\s+"));
    } catch (Exception e) {
      e.printStackTrace();
    }
    homeStatus = model.getGoHome();

    assertTrue(homeStatus, "show turtle got: " + homeStatus);
  }

  @Test
  void testInvalidCommand() {
    String input;
    /**
     * testing "50 fd" - invalid command
     */
    input = "50 fd";

    assertThrows(MissingResourceException.class, () -> {
      model.runCommand(input.split("\\s+"));
    });
  }

  @Test
  void testStamp() {
    String input;
    boolean stampStatus;
    /**
     * testing "50 fd" - invalid command
     */
    input = "stamp";

    try {
      model.runCommand(input.split("\\s+"));
    } catch (Exception e) {
      e.printStackTrace();
    }
    stampStatus = model.getStamped();

    assertTrue(stampStatus, "show turtle got: " + stampStatus);
  }

}
