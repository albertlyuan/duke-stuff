import hangman.HangmanGame;
import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import hangman.HangmanAutoGame;
import hangman.HangmanInteractiveGame;
import util.HangmanDictionary;


/**
 * This class launches the Hangman game and plays once.
 *
 * @author Robert C. Duvall
 */
public class Main extends Application {
    // constants - JFX
    public static final String TITLE = "HangPerson";
    public static final int SIZE = 600;
    public static final Paint BACKGROUND = Color.THISTLE;
    public static final double SECOND_DELAY = 1;

    // constants - Game
    public static final String DICTIONARY = "lowerwords.txt";
    public static final int NUM_LETTERS = 6;
    public static final int NUM_MISSES = 8;

    //possible guesser/secretkeeper options
    public static final String CLEVER = "Clever";
    public static final String INTERACTIVE = "Interactive";
    public static final String SIMPLE = "Simple";

    /**
     * Organize display of game in a scene and start the game.
     */
    @Override
    public void start (Stage stage) {
        String GuesserType = SIMPLE;
        String SecretKeeperType = CLEVER;
        HangmanGame game = new HangmanGame(
                new HangmanDictionary(DICTIONARY), NUM_LETTERS, NUM_MISSES, GuesserType,SecretKeeperType);
        stage.setScene(game.setupDisplay(SIZE, SIZE, BACKGROUND));
        stage.setTitle(TITLE);
        stage.show();

        game.start(SECOND_DELAY);
    }
}
