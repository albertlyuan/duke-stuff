package oolala;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import oolala.view.oolalaView;

import java.awt.Dimension;
import oolala.view.oolalaViewDarwin;
import oolala.view.oolalaViewLogo;
import oolala.view.oolalaViewLsystem;


/**
 * Feel free to completely change this code or delete it entirely.
 */
public class Main extends Application {

  // convenience constants
  public static final String MENU_TITLE = "OOLALA";
  public static final Dimension DEFAULT_SIZE = new Dimension(900, 600);
  public static final String DEFAULT_LANGUAGE = "English";
  private static final double VERSION = 0.001;

  private oolalaView turtleGraphic;

  /**
   * Start of the program.
   */
  public double getVersion() {
    return VERSION;
  }

  @Override
  public void start(Stage stage) {
    ChoiceBox<String> cb = new ChoiceBox<>();
    TilePane pane = new TilePane();
    Label label = new Label("Choose an Application");

    cb.getItems().add("Logo Programming");
    cb.getItems().add("L-System Visualizer");
    cb.getItems().add("Darwin Simulator");

    cb.setOnAction(event -> {
      String selected = cb.getValue();
      if (selected.equals("Logo Programming")) {
        turtleGraphic = new oolalaViewLogo(DEFAULT_LANGUAGE);
        makeApplication(stage, selected);
      }
      else if (selected.equals("L-System Visualizer")) {
        turtleGraphic = new oolalaViewLsystem(DEFAULT_LANGUAGE);
        makeApplication(stage, selected);
      }
      else if (selected.equals("Darwin Simulator")) {
        turtleGraphic = new oolalaViewDarwin(DEFAULT_LANGUAGE);
        makeApplication(stage, selected);
      }
    });
    pane.getChildren().addAll(label, cb);

    stage.setTitle(MENU_TITLE);
    Scene scene = new Scene(pane, 160, 50);
    stage.setScene(scene);
    stage.show();
  }

  private void makeApplication(Stage stage, String title) {
    stage.hide();
    stage.setTitle(title);
    stage.setScene(turtleGraphic.makeScene(DEFAULT_SIZE.width, DEFAULT_SIZE.height));
    stage.show();
  }

}
