package oolala.model;

import static java.lang.Integer.parseInt;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class oolalaModelLogo extends oolalaModel{

  public oolalaModelLogo(ResourceBundle resources) {
    super(resources);
  }
  @Override
  public void runCommand(String[] inputParts)
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, MissingResourceException {
    /**
     * runs command using reflection of string input
     */
    String translatedCommand = myResources.getString(inputParts[0].toLowerCase(Locale.ROOT).strip());
    Method m = oolalaModelLogo.class.getDeclaredMethod(translatedCommand, String[].class);
    m.invoke(this, new Object[]{inputParts});
  }

  protected void fd(String[] input) throws ModelException{
    super.fd(input);
  }
  protected void bk(String[] input )throws ModelException{
    super.bk(input);
  }
  protected void lt(String[] input){
    super.lt(input);
  }
  protected void rt(String[] input){
    super.rt(input);
  }

  private void pd(String[] input) {
    TurtlePen = true;
  }

  private void pu(String[] input) {
    TurtlePen = false;
  }

  private void st(String[] input) {
    TurtleVisible = true;
  }

  private void ht(String[] input) {
    TurtleVisible = false;
  }

  private void home(String[] input) {
    goHome = true;
  }

  private void stamp(String[] input) {
    stamped = true;
  }

  private void tell(String[] input) {
    told = true;
    tellCommand = true;
  }

  private void pt(String[] input) throws ModelException {
    /**
     * changes pen thickness
     */
    try {
      int amount = parseInt(input[1].strip());
      TurtleThickness = amount;
    } catch (Exception e) {
      LOGGER.info(errorStatement);
      throw new ModelException(errorStatement);
    }
  }

  private void sh(String[] input) throws ModelException {
    /**
     * sets home coordinates to the given coordinates
     */
    try {
      homeX = parseInt(input[1].strip());
      homeY = parseInt(input[2].strip());
    } catch (Exception e) {
      LOGGER.info(errorStatement);
      throw new ModelException(errorStatement);
    }
  }

}
