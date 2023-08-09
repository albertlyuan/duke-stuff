package browser.view;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javax.imageio.ImageIO;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;
import browser.model.BrowserException;
import browser.model.NanoBrowserModel;


/**
 * A class used to display the viewer for a simple HTML browser.
 * 
 * See this tutorial for help on how to use the variety of components:
 *   http://download.oracle.com/otndocs/products/javafx/2/samples/Ensemble/
 * 
 * @author Owen Astrachan
 * @author Marcin Dobosz
 * @author Yuzhang Han
 * @author Edwin Ward
 * @author Robert C. Duvall
 */
public class NanoBrowserView {
    // constants
    private static final String RESOURCES = "browser/view/resources/";
    // use Java's dot notation, like with import, for properties files
    public static final String DEFAULT_RESOURCE_PACKAGE = NanoBrowserView.class.getPackageName() + ".resources.";
    // use file system notation, standard Unix slashes, for other kinds of files
    public static final String DEFAULT_RESOURCE_FOLDER = "/" + DEFAULT_RESOURCE_PACKAGE.replace(".", "/");
    public static final String STYLESHEET = "default.css";
    public static final String BLANK = " ";

    // web page
    private WebView myPage;
    // information area
    private Label myStatus;
    // navigation
    private TextField myURLDisplay;
    private Button myBackButton;
    private Button myNextButton;
    private Button myHomeButton;
    private ComboBox<String> myFavoritesBox;
    // get strings from resource file
    private ResourceBundle myResources;
    // data
    private NanoBrowserModel myModel;


    /**
     * Create a view of the given model of a web browser with prompts in the given language.
     */
    public NanoBrowserView (NanoBrowserModel model, String language) {
        myModel = model;
        // use resources for labels
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
    }

    /**
     * Returns scene for this view so it can be added to stage.
     */
    public Scene makeScene (int width, int height) {
        BorderPane root = new BorderPane();
        // must be first since other panels may refer to page
        root.setCenter(makePageDisplay());
        root.setTop(makeInputPanel());
        root.setBottom(makeInformationPanel());
        // control the navigation
        enableButtons();
        // create scene to hold UI
        Scene scene = new Scene(root, width, height);
        // activate CSS styling
        scene.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET).toExternalForm());
        return scene;
    }

    /**
     * Display given URL.
     */
    public void showPage (String url) {
        try {
            update(myModel.go(url));
        }
        catch (BrowserException e) {
            showError(e.getMessage());
        }
    }

    // Display given message as information in the GUI
    private void showStatus (String message) {
        myStatus.setText(message);
    }

    // Display given message as an error in the GUI
    private void showError (String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(myResources.getString("ErrorTitle"));
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Move to the next URL in the history
    private void next () {
        update(myModel.next());
    }

    // Move to the previous URL in the history
    private void back () {
        update(myModel.back());
    }

    // Change current URL to the home page, if set
    private void home () {
        showPage(myModel.getHome().toString());
    }

    // Change home URL to be current URL
    private void setHome () {
        myModel.setHome();
        enableButtons();
    }

    // NOTE, no parameter version of method is matched to other methods to be used by reflection
    private void showPage () {
        showPage(myURLDisplay.getText());
    }

    // Change page to favorite choice
    private void showFavorite (String favorite) {
        showPage(myModel.getFavorite(favorite).toString());
    }

    // Prompt user for name of favorite to add to collection
    private void addFavorite () {
        TextInputDialog input = new TextInputDialog("");
        input.setTitle(myResources.getString("FavoritePromptTitle"));
        input.setContentText(myResources.getString("FavoritePrompt"));
        Optional<String> response = input.showAndWait();
        // did user make a choice?
        if (response.isPresent() && !response.get().equals("")) {
            myModel.addFavorite(response.get());
            myFavoritesBox.getItems().add(response.get());
        }
    }

    // Update just the view to display given URL
    private void update (URL url) {
        String urlText = url.toString();
        myPage.getEngine().load(urlText);
        myURLDisplay.setText(urlText);
        enableButtons();
    }

    // Only enable buttons when useful to user
    private void enableButtons () {
        myBackButton.setDisable(! myModel.hasPrevious());
        myNextButton.setDisable(! myModel.hasNext());
        myHomeButton.setDisable(! myModel.hasHome());
    }

    // Convenience method to create HTML page display
    private Node makePageDisplay () {
        myPage = new WebView();
        // catch "browsing" events within web page
        myPage.getEngine().getLoadWorker().stateProperty().addListener(new LinkListener());
        return myPage;
    }

    // Organize user's options for controlling/giving input to model
    private Node makeInputPanel () {
        VBox result = new VBox();
        result.getChildren().addAll(makeNavigationPanel(), makePreferencesPanel());
        return result;
    }

    // Make user-entered URL/text field and back/next buttons
    private Node makeNavigationPanel () {
        HBox result = new HBox();
        // create buttons, with their associated actions
        // TODO: replace these lambdas with strings representing a method name (i.e., "back", "next")
        myBackButton = makeButton("BackCommand", event -> back());
        result.getChildren().add(myBackButton);
        myNextButton = makeButton("NextCommand", event -> next());
        result.getChildren().add(myNextButton);
        // if user presses button or enter in text field, load/show the URL
        EventHandler<ActionEvent> showHandler = event -> showPage();
        result.getChildren().add(makeButton("GoCommand", showHandler));
        myURLDisplay = makeInputField(40, showHandler);
        result.getChildren().add(myURLDisplay);
        return result;
    }

    // Make buttons for setting favorites/home URLs
    private Node makePreferencesPanel () {
        HBox result = new HBox();
        myHomeButton = makeButton("HomeCommand", event -> home());
        result.getChildren().add(myHomeButton);
        result.getChildren().add(makeButton("SetHomeCommand", event -> setHome()));
        myFavoritesBox = new ComboBox<>();
        myFavoritesBox.setPromptText(myResources.getString("FavoriteFirstItem"));
        myFavoritesBox.valueProperty().addListener((o, old, neww) -> showFavorite(neww));
        result.getChildren().add(makeButton("AddFavoriteCommand", event -> addFavorite()));
        result.getChildren().add(myFavoritesBox);
        return result;
    }

    // Make panel where "would-be" clicked URL is displayed
    private Node makeInformationPanel () {
        // BLANK must be non-empty or status label will not be displayed in GUI
        myStatus = new Label(BLANK);
        return myStatus;
    }

    // Make button using either an image or a label
    // TODO: change this method to take a String representing a method name (like "next") instead of an EventHandler
    private Button makeButton (String property, EventHandler<ActionEvent> handler) {
        // represent all supported image suffixes
        final String IMAGE_FILE_SUFFIXES = String.format(".*\\.(%s)", String.join("|", ImageIO.getReaderFileSuffixes()));

        Button result = new Button();
        String label = myResources.getString(property);
        if (label.matches(IMAGE_FILE_SUFFIXES)) {
            result.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(DEFAULT_RESOURCE_FOLDER + label))));
        }
        else {
            result.setText(label);
        }
        // TODO: make your own handler that creates a Method object from the given string and calls its invoke() method
        result.setOnAction(handler);
        return result;
    }

    // Make text field for input
    private TextField makeInputField (int width, EventHandler<ActionEvent> handler) {
        TextField result = new TextField();
        result.setPrefColumnCount(width);
        result.setOnAction(handler);
        return result;
    }


    // Inner class to deal with link-clicks and mouse-overs Mostly taken from
    //   http://blogs.kiyut.com/tonny/2013/07/30/javafx-webview-addhyperlinklistener/
    private class LinkListener implements ChangeListener<State> {
        public static final String HTML_LINK = "href";
        public static final String EVENT_CLICK = "click";
        public static final String EVENT_MOUSEOVER = "mouseover";
        public static final String EVENT_MOUSEOUT = "mouseout";

        @Override
        public void changed (ObservableValue<? extends State> ov, State oldState, State newState) {
            if (newState == Worker.State.SUCCEEDED) {
                EventListener listener = event -> {
                    final String href = ((Element) event.getTarget()).getAttribute(HTML_LINK);
                    if (href != null) {
                        switch (event.getType()) {
                            case EVENT_CLICK -> showPage(href);
                            case EVENT_MOUSEOVER -> showStatus(href);
                            case EVENT_MOUSEOUT -> showStatus(BLANK);
                            default -> { /* do nothing */ }
                        }
                    }
                };
                Document doc = myPage.getEngine().getDocument();
                NodeList nodes = doc.getElementsByTagName("a");
                for (int k = 0; k < nodes.getLength(); k += 1) {
                    EventTarget node = (EventTarget)nodes.item(k);
                    node.addEventListener(EVENT_CLICK, listener, false);
                    node.addEventListener(EVENT_MOUSEOVER, listener, false);
                    node.addEventListener(EVENT_MOUSEOUT, listener, false);
                }
            }
        }
    }
}
