package browser;

import java.awt.Dimension;
import javafx.application.Application;
import javafx.stage.Stage;
import browser.model.NanoBrowserModel;
import browser.view.NanoBrowserView;


/**
 * A simple HTML browser.
 * 
 * @author Robert C. Duvall
 */
public class Main extends Application {
    // convenience constants
    public static final String TITLE = "NanoBrowser";
    public static final String DEFAULT_START_PAGE = "https://users.cs.duke.edu/rcd";
    public static final Dimension DEFAULT_SIZE = new Dimension(800, 600);
    public static final String LANGUAGE = "Image";

    /**
     * @see Application#start(Stage)
     */
    @Override
    public void start (Stage stage) {
        // create program specific components
        NanoBrowserModel model = new NanoBrowserModel(LANGUAGE);
        NanoBrowserView display = new NanoBrowserView(model, LANGUAGE);
        // give the window a title
        stage.setTitle(TITLE);
        // add our user interface components to Frame and show it
        stage.setScene(display.makeScene(DEFAULT_SIZE.width, DEFAULT_SIZE.height));
        stage.show();
        // start somewhere, less typing for debugging
        display.showPage(DEFAULT_START_PAGE);
    }

    /**
     * Start of the program -- give up control to JavaFX.
     */
    public static void main (String[] args) {
        launch(args);
    }
}
