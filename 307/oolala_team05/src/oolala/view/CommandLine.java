package oolala.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;

public class CommandLine extends TextField {

  public CommandLine(String x, String y, String width, EventHandler<ActionEvent> handler, String message) {
    setId("commandLine");
    setPromptText(message);
    setFocusTraversable(false);
    setPrefColumnCount(Integer.parseInt(width));
    setOnAction(handler);
    relocate(Double.parseDouble(x), Double.parseDouble(y));
  }
}
