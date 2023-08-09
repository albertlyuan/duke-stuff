package oolala;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Feel free to completely change this code or delete it entirely.
 */
class MainTest extends DukeApplicationTest {

  // how close do real valued numbers need to be to count as the same
  static final double TOLERANCE = 0.0005;

  /**
   * Test a method from Main.
   */
  @Test
  void testVersionIsReady() {
    Main m = new Main();
    // different ways to test double results
    assertEquals(1, Math.round(m.getVersion() * 1000));
    assertTrue(m.getVersion() < 1);
    assertEquals(0.001, m.getVersion(), TOLERANCE);
  }

  @Test
  void testAppIsChosenCorrectly() {
    Main m = new Main();

  }
}
