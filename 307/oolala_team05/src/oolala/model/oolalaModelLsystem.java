package oolala.model;

import static java.lang.Integer.parseInt;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import jdk.jfr.Name;

public class oolalaModelLsystem extends oolalaModel{

  private String startSymbol;
  private String rule;
  private StringBuilder command;
  private Map<String, List<String>> symbolProps;
  private double baseLength = 10;
  private double baseAngle = 30;
  private boolean changeColor;

  private final Map<String, String> immutableAlphabet = Map.of(
  "F", String.format("%s %s %s", myResources.getString("pd"), myResources.getString("fd"), myResources.getString("Length")),
  "G", String.format("%s %s %s", myResources.getString("pu"), myResources.getString("fd"), myResources.getString("Length")),
  "A", String.format("%s %s %s", myResources.getString("pu"), myResources.getString("bk"), myResources.getString("Length")),
  "B", String.format("%s %s %s", myResources.getString("pd"), myResources.getString("bk"), myResources.getString("Length")),
  "-", String.format("%s %s", myResources.getString("lt"), myResources.getString("Angle")),
  "+", String.format("%s %s", myResources.getString("rt"), myResources.getString("Angle")),
  "X", String.format("%s", myResources.getString("stamp"))
  );
  private Map<String, String> builtInAlphabet = new HashMap<>(immutableAlphabet);

  private Map<String, Boolean> ifLetterHasRule;

  public oolalaModelLsystem(ResourceBundle resources) {
    super(resources);
    symbolProps = new HashMap<>();
    command = new StringBuilder();
    ifLetterHasRule = new HashMap<>();
  }

  /**
   * runs the command using reflection where all the methods are in this subclass and share the same name as inputParts[0]
   * @param inputParts command in array form, which is string split at spaces
   * @throws NoSuchMethodException
   * @throws InvocationTargetException
   * @throws IllegalAccessException
   * @throws MissingResourceException
   */
  @Override
  public void runCommand(String[] inputParts)
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, MissingResourceException {
    String translatedCommand = myResources.getString(inputParts[0].toLowerCase(Locale.ROOT).strip());
    Method m = oolalaModelLsystem.class.getDeclaredMethod(translatedCommand, String[].class);
    m.invoke(this, new Object[]{inputParts});
  }
  private void pd(String[] input) {
    TurtlePen = true;
  }

  private void pu(String[] input) {TurtlePen = false;}

  private void stamp(String[] input) {stamped = true;}

  private void stop(String[] input) {
    changeColor = true;
  }

  protected void lt(String[] input) throws ModelException {super.lt(input);}

  protected void rt(String[] input) throws ModelException {super.rt(input);}

  protected void fd(String[] input) throws ModelException {super.fd(input);}

  private void start(String input[]){
    try {
      startSymbol = input[1].strip();
    } catch (Exception e) {
      LOGGER.info(errorStatement);
      throw new ModelException(errorStatement);
    }
  }

  private void rule(String input[]){
    try {
      String key = input[1].strip();
      rule = input[2].strip();
      if (!symbolProps.containsKey(key)) {
        symbolProps.put(key, new ArrayList<>());
      }
      symbolProps.get(key).add(rule);
      ifLetterHasRule.put(key, false);
    } catch (Exception e) {
      LOGGER.info(errorStatement);
      throw new ModelException(errorStatement);
    }
  }

  private void set(String input[]) {
    try {
      String key = input[1].strip();
      for (int i = 2; i < input.length; i++) {
        if (input[i].equals(myResources.getString("Length"))) {
          input[i] = String.valueOf((int) baseLength);
        } else if (input[i].equals(myResources.getString("Angle"))) {
          input[i] = String.valueOf((int) baseAngle);
        }
        command.append(input[i]).append(" ");
      }
      if (!symbolProps.containsKey(key)) {
        symbolProps.put(key, new ArrayList<>());
      }
      symbolProps.get(key).add(command.toString());
      ifLetterHasRule.put(key, true);
      command.setLength(0);
    } catch (Exception e) {
      LOGGER.info(errorStatement);
      throw new ModelException(errorStatement);
    }
  }

  /**
   * gets the index that the commands are in, in the symbolProps map
   * @param letter like "F", or "G"
   * @return returns the index the commands are in
   */
  private int getCmdIndexInMap(String letter) {
    int commandIndex = 0;
    if (symbolProps.get(letter).size() == 2) {
      commandIndex = 1;
    }
    return commandIndex;
  }

  /**
   * gets the commands in array form
   * @param letter like "F", or "G"
   * @return returns the commands in array form
   */
  @Override
  public String[] getArrayOfCommands(String letter) {
    int cmdIndex = getCmdIndexInMap(letter);
    return symbolProps.get(letter).get(cmdIndex).split("\\s+(?=[a-zA-Z])");
  }

  /**
   * the next rule for the recursive implementation
   * @param rules the current rule that has already been executed
   * @return the next rule, based on the current rule
   */
  @Override
  public String getNextRule(String rules) {
    StringBuilder nextRule = new StringBuilder();
    String[] eachRule = rules.split("");
    for (int i=0; i<eachRule.length; i++) {
      boolean hasARule = symbolProps.get(eachRule[i]).size() > 1;
      if (symbolProps.containsKey(eachRule[i]) && hasARule) {
        String rule = symbolProps.get(eachRule[i]).get(0);
        eachRule[i] = rule;
      }
      nextRule.append(eachRule[i]);
    }
    return nextRule.toString();
  }

  /**
   * adds the commands associated with the built-in alphabet to symbolProps
   */
  public void addDefaultCommands() {
    boolean noSetYet;
    setNumsForCommands();
    for (String letter : builtInAlphabet.keySet()) {
      if (symbolProps.containsKey(letter)) {
        noSetYet = symbolProps.get(letter).size() < 2;
        if (noSetYet) {
          symbolProps.get(letter).add(builtInAlphabet.get(letter));
        }
      }
      else {
        symbolProps.put(letter, new ArrayList<>());
        symbolProps.get(letter).add(builtInAlphabet.get(letter));
      }
      ifLetterHasRule.put(letter, true);
    }
  }

  /**
   * sets numbers for the commands with "LENGTH" and "ANGLE" in them
   */
  private void setNumsForCommands() {
    for (String key : builtInAlphabet.keySet()) {
      String command = builtInAlphabet.get(key);
      if (command.contains(myResources.getString("Length"))) {
        String newCmd = command.replace(myResources.getString("Length"), String.valueOf((int) getBaseLength()));
        builtInAlphabet.put(key, newCmd);
      }
      else if (command.contains(myResources.getString("Angle"))) {
        String newCmd = command.replace(myResources.getString("Angle"), String.valueOf((int) getBaseAngle()));
        builtInAlphabet.put(key, newCmd);
      }
    }
  }

  /**
   * if a letter like "T" does not have a built-in command or set is never called for this letter, it will create a command
   * using its rule and the commands associated with the letters in the rule
   */
  @Override
  public void setCommandForLetter() {
    StringBuilder listOfCommands = new StringBuilder();
    for (String key : ifLetterHasRule.keySet()) {
      if (!ifLetterHasRule.get(key)) {
        String letterSequence = symbolProps.get(key).get(0);
        String[] eachLetter = letterSequence.split("(?!^)");
        for (int i=0; i<eachLetter.length; i++) {
          int cmdIndex = getCmdIndexInMap(eachLetter[i]);
          listOfCommands.append(symbolProps.get(eachLetter[i]).get(cmdIndex)).append(" ");
        }
        symbolProps.get(key).add(listOfCommands.toString());
      }
    }
  }
  @Override
  public void resetAlphabet() {
    builtInAlphabet = new HashMap<>(immutableAlphabet);
  }
  @Override
  public void setBaseLength(double baseLength) {
    this.baseLength = baseLength;
  }
  @Override
  public void setBaseAngle(double baseAngle) {
    this.baseAngle = baseAngle;
  }
  @Override
  public void setChangeColor(boolean changed) {
    changeColor = changed;
  }

  @Override
  public double getBaseLength() {
    return baseLength;
  }
  @Override
  public double getBaseAngle() {
    return baseAngle;
  }
  @Override
  public Map<String, List<String>> getSymbolProps() {
    return symbolProps;
  }
  @Override
  public String getStartSymbol() {
    return startSymbol;
  }
  @Override
  public boolean getChangeColor() {
    return changeColor;
  }
}
