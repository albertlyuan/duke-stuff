package browser;

import java.net.URL;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;


/**
 * A class used to display the viewer for a simple HTML browser.
 *
 * See this tutorial for help on how to use all variety of components:
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
    public static final String BLANK = " ";

    // web page
    private WebView myPage;
    // navigation
    private TextField myURLDisplay;
    private Button HomeButton;
    private ComboBox topVisited;
    // information area
    private Label myStatus;

    //model object
    private NanoBrowserModel myModel;


    /**
     * Create a web browser with prompts in the given language with initially empty state.
     */
    public NanoBrowserView () {
        myModel = new NanoBrowserModel();
    }

    /**
     * Returns scene for the browser, so it can be added to stage.
     */
    public Scene makeScene (int width, int height) {
        BorderPane root = new BorderPane();
        // must be first since other panels may refer to page
        root.setCenter(makePageDisplay());
        root.setTop(makeInputPanel());
        root.setBottom(makeInformationPanel());
        // create scene to hold UI
        return new Scene(root, width, height);
    }

    /**
     * Display given URL.
     */
    public void showPage (String url) {
        URL loadedURL = myModel.loadURL(url);
        topVisited.getItems().clear();
        topVisited.getItems().addAll(myModel.getTopSites());
        if (loadedURL != null){
            update(loadedURL);
            if(!loadedURL.toString().equals(myModel.getHome())){
                HomeButton.setStyle("-fx-text-fill: black");
            }
        }
    }

    // Move to next URL in the history
    public void next () {
        update(myModel.getHistoryURL(1));
    }

    // Move to previous URL in the history
    public void back () {
        update(myModel.getHistoryURL(-1));
    }
    public void goHome() {
        /**
         * when clicked goes to set home site. if no home, alert is shown.
         */
        try {
            showPage(myModel.getHome());
            HomeButton.setStyle("-fx-text-fill: blue");

        } catch (NullPointerException e) {
            myModel.showError("No Home Set");
        }
    }
    // Update just the view to display given URL
    private void update (URL url) {
        String urlText = url.toString();
        myPage.getEngine().load(urlText);
        myURLDisplay.setText(urlText);
    }

    // Display given message as information in the GUI
    private void showStatus (String message) {
        myStatus.setText(message);
    }

    // Make user-entered URL/text field and back/next buttons
    private Node makeInputPanel () {
        HBox result = new HBox();
        // create buttons, with their associated actions
        Button backButton = makeButton("Back", event -> back());
        Button nextButton = makeButton("Next", event -> next());
        Button goButton = makeButton("Go", event -> showPage(myURLDisplay.getText()));
        // if user presses button or enter in text field, load/show the URL
        myURLDisplay = makeInputField(40, event -> showPage(myURLDisplay.getText()));
        HomeButton = makeButton("Go Home", event -> goHome());
        Button setHome = makeSetHome();
        Button setFavorite = makeFavoriteButton();
        makeTopVisited();

        result.getChildren().addAll(backButton,nextButton,goButton, myURLDisplay, setHome, HomeButton,setFavorite,topVisited);
        return result;
    }

    private Button makeSetHome() {
        Button setHome = makeButton("Set Home", event -> {
            myModel.setHome();
            HomeButton.setStyle("-fx-text-fill: blue");
        });
        return setHome;
    }

    private Button makeFavoriteButton() {
        Button setFavorite = makeButton("Set Favorite", event -> {
            TextInputDialog td = new TextInputDialog("Name Your Site");
            td.showAndWait();
            myModel.setFav(td.getEditor().getText());
        });
        return setFavorite;
    }

    private void makeTopVisited() {
        topVisited = new ComboBox(myModel.getTopSites());
        topVisited.setPromptText("Top visited sites");
        topVisited.setEditable(true);
        topVisited.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                showPage(t1);
            }
        });
    }

    // Make panel where "would-be" clicked URL is displayed
    private Node makeInformationPanel () {
        // BLANK must be non-empty or status label will not be displayed in GUI
        myStatus = new Label(BLANK);
        return myStatus;
    }

    // Typical code to create HTML page display
    private Node makePageDisplay () {
        myPage = new WebView();
        // catch "browsing" events within web page
        myPage.getEngine().getLoadWorker().stateProperty().addListener(new LinkListener());
        return myPage;
    }

    // Typical code to create button
    private Button makeButton (String label, EventHandler<ActionEvent> handler) {
        Button result = new Button();
        result.setText(label);
        result.setOnAction(handler);
        return result;
    }

    // Typical code to create text field for input
    private TextField makeInputField (int width, EventHandler<ActionEvent> handler) {
        TextField result = new TextField();
        result.setPrefColumnCount(width);
        result.setOnAction(handler);
        return result;
    }

    // Inner class to deal with link-clicks and mouse-overs Mostly taken from
    //   http://blogs.kiyut.com/tonny/2013/07/30/javafx-webview-addhyperlinklistener/
    private class LinkListener implements ChangeListener<State> {
        public static final String ANCHOR = "a";
        public static final String HTML_LINK = "href";
        public static final String EVENT_CLICK = "click";
        public static final String EVENT_MOUSEOVER = "mouseover";
        public static final String EVENT_MOUSEOUT = "mouseout";

        @Override
        public void changed (ObservableValue<? extends State> ov, State oldState, State newState) {
            if (newState == Worker.State.SUCCEEDED) {
                EventListener listener = event -> handleMouse(event);
                Document doc = myPage.getEngine().getDocument();
                NodeList nodes = doc.getElementsByTagName(ANCHOR);
                for (int k = 0; k < nodes.getLength(); k+=1) {
                    EventTarget node = (EventTarget)nodes.item(k);
                    node.addEventListener(EVENT_CLICK, listener, false);
                    node.addEventListener(EVENT_MOUSEOVER, listener, false);
                    node.addEventListener(EVENT_MOUSEOUT, listener, false);
                }
            }
        }

        // Give user feedback as expected by modern browsers
        private void handleMouse (Event event) {
            final String href = ((Element)event.getTarget()).getAttribute(HTML_LINK);
            if (href != null) {
                switch (event.getType()) {
                    case EVENT_CLICK -> showPage(href);
                    case EVENT_MOUSEOVER -> showStatus(href);
                    case EVENT_MOUSEOUT -> showStatus(BLANK);
                }
            }
        }
    }
}
