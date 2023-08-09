package oolala.model;

public class ModelException extends RuntimeException {

  /**
   * Create an exception based on an issue in our code.
   */
  public ModelException(String message, Object... values) {
    super(String.format(message, values));
  }

}

