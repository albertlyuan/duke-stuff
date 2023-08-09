package oolala.view;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.shape.Circle;
import oolala.utils.ImageChooser;

public class DarwinTurtle extends Turtle {

  private String species;
  private List<String> baseCode;
  private int instrIdx=0;
  public DarwinTurtle(double initialX, double initialY, double initialSize, String species,
      List<String> program) {
    super(initialX, initialY, initialSize);
    this.species = species;
    baseCode = program;
  }
  @Override
  public String getNextCommand() throws IndexOutOfBoundsException {
    /**
     * returns the next command to be run from basecode
     */
    String next = baseCode.get(instrIdx);
    instrIdx++;
    return next;
  }
  @Override
  public void setBaseCode(List<String> baseCode) {
    this.baseCode = baseCode;
  }
  @Override
  public void setSpecies(String species) {
    this.species = species;
  }
  @Override
  public void setInstrIdx(int instrIdx) {
    this.instrIdx = instrIdx;
  }
  @Override
  public String getSpecies() {
    return species;
  }
}
