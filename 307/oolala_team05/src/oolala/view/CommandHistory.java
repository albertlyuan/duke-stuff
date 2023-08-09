package oolala.view;

import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.Queue;
import java.util.ResourceBundle;

public class CommandHistory extends VBox {

  private Queue<String> myHistory;
  private static final int MAX_STORED_COMMANDS = 22;
  private ResourceBundle myResources;

  public CommandHistory(ResourceBundle resources) {
    myHistory = new LinkedList<>();
    myResources = resources;
    setId("commandPanel");

    Border border = new Border(
        new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
            BorderStroke.MEDIUM));
    setBorder(border);

    Label header = new Label(myResources.getString("CommandHistory"));
    header.setId("commandHistory");

    getChildren().addAll(header);
  }

  public boolean add(String command) {
    /**
     * tries to add a command to history. if command already exists, returns false.
     */
    if (!myHistory.contains(command)) {
      if (myHistory.size() == MAX_STORED_COMMANDS) {
        myHistory.remove();
      }
      myHistory.add(command);
      return true;
    }
    return false;
  }

  public Queue<String> getCommandHistory() {
    return myHistory;
  }

}
