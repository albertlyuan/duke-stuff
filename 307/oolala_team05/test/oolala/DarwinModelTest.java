package oolala;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import oolala.model.oolalaModel;
import oolala.model.oolalaModelDarwin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;


/**
 * Feel free to completely change this code or delete it entirely.
 */
class DarwinModelTest extends DukeApplicationTest {

  private oolalaModelDarwin model;
  private static final String DEFAULT_RESOURCE_PACKAGE = "oolala.resources.";
  private String language = "English";
  private ResourceBundle myResources = ResourceBundle.getBundle(
      DEFAULT_RESOURCE_PACKAGE + language);

  int XBounds = 900;
  int YBounds = 600;

  String[] input;

  @BeforeEach
  void setup() {
    model = new oolalaModelDarwin(myResources, XBounds, YBounds);
  }

  @Test
  void TestMove(){
    /**
     * moves 50
     */
    double start, end, delta;

    model.importTurtle(400, 300, 0, "A");
    input = "move 50".split("\\s+");

    start = model.getX();
    try {
      model.runCommand(input);
    } catch (Exception e) {
      e.printStackTrace();
    }
    end = model.getX();
    delta = end - start;
    assertEquals(50, delta, "moving +50 X, got: " + delta);
    assertEquals(true, model.didAction(), "action should be true");

    /**
     * testing running into the boundary
     */
    model.importTurtle(XBounds-10, 300, 0, "A");
    input = "move 50".split("\\s+");

    start = model.getX();
    try {
      model.runCommand(input);
    } catch (Exception e) {
      e.printStackTrace();
    }
    end = model.getX();
    delta = end - start;
    assertEquals(0, delta, "shouldn't move because at boarder, got: " + delta);
    assertEquals(true, model.didAction(), "action should be true");

  }

  @Test
  void testIfempty(){
    model.importTurtle(400, 300, 0, "A");
    input = "ifempty 4".split("\\s+");
    try {
      model.runCommand(input);
    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
      e.printStackTrace();
    }
    assertEquals(true, model.getConditionalState(), "should be empty space");
  }

  @Test
  void testIfwall(){
    model.importTurtle(899, 300, 0, "A");
    input = "ifwall 4".split("\\s+");
    try {
      model.runCommand(input);
    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
      e.printStackTrace();
    }
    assertEquals(true, model.getConditionalState(), "should be detecting wall");
  }
  @Test
  void testIfsame(){
    model.importTurtle(401, 300, 0, "A");
    model.importTurtle(400, 300, 0, "A");

    input = "ifsame 4".split("\\s+");
    try {
      model.runCommand(input);
    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
      e.printStackTrace();
    }
    assertEquals(true, model.getConditionalState(), "should be detecting ally");
  }

  @Test
  void testIfenemy(){
    model.importTurtle(401, 300, 0, "B");
    model.importTurtle(400, 300, 0, "A");

    input = "ifenemy 4".split("\\s+");
    try {
      model.runCommand(input);
    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
      e.printStackTrace();
    }
    assertEquals(true, model.getConditionalState(), "should be detecting enemy");

    model.importTurtle(403, 300, 0, "B");
    model.importTurtle(402, 300, 0, "B");
    input = "ifenemy 4".split("\\s+");
    try {
      model.runCommand(input);
    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
      e.printStackTrace();
    }
    assertEquals(false, model.getConditionalState(), "should NOT be detecting enemy");

  }

  @Test
  void testInfect(){
    model.importTurtle(401, 300, 0, "A");
    model.importTurtle(400, 300, 0, "B");

    input = "infect".split("\\s+");
    try {
      model.runCommand(input);
    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
      e.printStackTrace();
    }
    model.importTurtle(401, 300, 0, "A");
    assertEquals("B", model.getSpecies(), "should've changed from A to B species");
  }
}
