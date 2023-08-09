package oolala;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.*;

import oolala.model.oolalaModelLogo;
import oolala.model.oolalaModelLsystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;


/**
 * Feel free to completely change this code or delete it entirely.
 */

class LsystemModelTest extends DukeApplicationTest {

  // how close do real valued numbers need to be to count as the same
  static final double TOLERANCE = 0.0005;
  oolalaModelLsystem model;
  public static final String DEFAULT_RESOURCE_PACKAGE = "oolala.resources.";
  public static final String DEFAULT_RESOURCE_FOLDER =
      "/" + DEFAULT_RESOURCE_PACKAGE.replace(".", "/");
  String language = "English";
  private ResourceBundle myResources = ResourceBundle.getBundle(
      DEFAULT_RESOURCE_PACKAGE + language);

  @BeforeEach
  void setup() {
    model = new oolalaModelLsystem(myResources);
  }

  @Test
  void testpd() {
    String input;
    boolean shown;
    /**
     * testing pd
     */
    input = "pd";

    try {
      model.runCommand(input.split("\\s+"));
    } catch (Exception e) {
      e.printStackTrace();
    }
    shown = model.getPenStatus();

    assertTrue(shown, "pen down got: " + shown);
  }

  @Test
  void testStopCommand() {
    String input;
    boolean shown;
    /**
     * testing stop - this is a command that I added to differentiate between each iteration
     */
    input = "stop";

    try {
      model.runCommand(input.split("\\s+"));
    } catch (Exception e) {
      e.printStackTrace();
    }
    shown = model.getChangeColor();

    assertTrue(shown, "stop got: " + shown);
  }

  @Test
  void testStart() {
    String input;
    String symbol;
    /**
     * testing start
     */
    input = "start D";

    try {
      model.runCommand(input.split("\\s+"));
    } catch (Exception e) {
      e.printStackTrace();
    }
    symbol = model.getStartSymbol();

    assertEquals("D", symbol);
  }

  @Test
  void testRule() {
    String input;
    Set<String> symbolProp;
    String symbol;
    String value;
    /**
     * testing rule
     */
    input = "rule F \t F-G+F+G-F";

    try {
      model.runCommand(input.split("\\s+"));
    } catch (Exception e) {
      e.printStackTrace();
    }
    symbolProp = model.getSymbolProps().keySet();
    symbol = symbolProp.stream().findFirst().get();
    value = model.getSymbolProps().get(symbol).toString();

    assertEquals("F", symbol);
    assertEquals("[F-G+F+G-F]", value);
  }

  @Test
  void testSet() {
    String input;
    Set<String> symbolProp;
    String symbol;
    String value;
    /**
     * testing set
     */
    input = "set G pd fd LENGTH";

    try {
      model.runCommand(input.split("\\s+"));
    } catch (Exception e) {
      e.printStackTrace();
    }
    symbolProp = model.getSymbolProps().keySet();
    symbol = symbolProp.stream().findFirst().get();
    value = model.getSymbolProps().get(symbol).toString();

    assertEquals("G", symbol);
    assertEquals("[pd fd 10 ]", value);
  }

  @Test
  void testAddDefaultCommands() {
    String instr1 = "start F";
    String instr2 = "rule F F-F++F-F";

    try {
      model.runCommand(instr1.split("\\s+"));
      model.runCommand(instr2.split("\\s+"));
    } catch (Exception e) {
      e.printStackTrace();
    }
    model.addDefaultCommands();

    assertEquals("pd fd 10", model.getSymbolProps().get("F").get(1));
    assertEquals("pu fd 10", model.getSymbolProps().get("G").get(0));
    assertEquals("pu bk 10", model.getSymbolProps().get("A").get(0));
    assertEquals("pd bk 10", model.getSymbolProps().get("B").get(0));
    assertEquals("rt 30", model.getSymbolProps().get("+").get(0));
    assertEquals("lt 30", model.getSymbolProps().get("-").get(0));
    assertEquals("stamp", model.getSymbolProps().get("X").get(0));
  }

  @Test
  void testSetCommandForLetter() {
    String instr1 = "start    T";
    String instr2 = "rule T \t F-G-G";
    String instr3 = "rule F \t F-G+F+G-F";
    String instr4 = "rule G \t GG";
    String instr5 = "set G pd fd LENGTH";

    try {
      model.runCommand(instr1.split("\\s+"));
      model.runCommand(instr2.split("\\s+"));
      model.runCommand(instr3.split("\\s+"));
      model.runCommand(instr4.split("\\s+"));
      model.runCommand(instr5.split("\\s+"));
    } catch (Exception e) {
      e.printStackTrace();
    }

    model.addDefaultCommands();
    model.setCommandForLetter();
    String tCommands = model.getSymbolProps().get("T").get(1);
    assertEquals("pd fd 10 lt 30 pd fd 10  lt 30 pd fd 10  ", tCommands);

  }

  @Test
  void testGetArrayOfCommands() {
    String instr1 = "start    T";
    String instr2 = "rule T \t F-G-G";
    String instr3 = "rule F \t F-G+F+G-F";
    String instr4 = "rule G \t GG";
    String instr5 = "set G pd fd LENGTH";

    try {
      model.runCommand(instr1.split("\\s+"));
      model.runCommand(instr2.split("\\s+"));
      model.runCommand(instr3.split("\\s+"));
      model.runCommand(instr4.split("\\s+"));
      model.runCommand(instr5.split("\\s+"));
    } catch (Exception e) {
      e.printStackTrace();
    }

    model.addDefaultCommands();
    model.setCommandForLetter();

    String[] fCommands = model.getArrayOfCommands("F");
    assertEquals("[pd, fd 10]", Arrays.toString(fCommands));

    String[] gCommands = model.getArrayOfCommands("G");
    assertEquals("[pd, fd 10 ]", Arrays.toString(gCommands));

    String[] tCommands = model.getArrayOfCommands("T");
    assertEquals("[pd, fd 10, lt 30, pd, fd 10, lt 30, pd, fd 10  ]", Arrays.toString(tCommands));
  }

  @Test
  void testGetNextRule() {
    String instr1 = "start F";
    String instr2 = "rule F F-F++F-F";

    try {
      model.runCommand(instr1.split("\\s+"));
      model.runCommand(instr2.split("\\s+"));
    } catch (Exception e) {
      e.printStackTrace();
    }
    model.addDefaultCommands();

    String secondIteration = model.getNextRule("F");
    assertEquals("F-F++F-F", secondIteration);

    String thirdIteration = model.getNextRule(secondIteration);
    assertEquals("F-F++F-F-F-F++F-F++F-F++F-F-F-F++F-F", thirdIteration);
  }
}
