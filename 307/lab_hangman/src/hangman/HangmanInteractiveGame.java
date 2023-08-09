package hangman;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import util.DisplayWord;
import util.HangmanDictionary;


/**
 * This class represents a game of Hangman between:
 *  - an interactive guesser
 *  - a secret word keeper that chooses a random secret word
 *
 * @author Robert C. Duvall
 */
public class HangmanInteractiveGame {
    // constant
    public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    // word that is being guessed
    private SecretKeeper SecretKeeper;
    private Guesser Guesser;
    // how many guesses are remaining
    private int myNumGuessesLeft;
    // what is shown to the user
    private DisplayWord myDisplayWord;
    // tracks letters guessed
    private StringBuilder myLettersLeftToGuess;
    // current player's guess
//    private String myGuess;
    // JFX variables
    private Scene myScene;
    private Timeline myAnimation;
    private Text myNumGuessesLeftDisplay;
    private List<Text> mySecretWordDisplay;
    private List<Text> myLettersLeftToGuessDisplay;


    /**
     * Create Hangman game with the given dictionary of words to play a game with words
     * of the given length and giving the user the given number of chances.
     */
    public HangmanInteractiveGame (HangmanDictionary dictionary, int wordLength, int numGuesses) {
        myNumGuessesLeft = numGuesses;

        myLettersLeftToGuess = new StringBuilder(ALPHABET);
        SecretKeeper = new SecretKeeper(dictionary.getRandomWord(wordLength).toLowerCase());
        Guesser = new Guesser();
        myDisplayWord = SecretKeeper.createDisplay();
        // SHOULD NOT PUBLIC, but makes it easier to test
    }

    /**
     * Start the game by animating the display of changes in the GUI every speed seconds.
     */
    public void start (double speed) {
        myAnimation = new Timeline();
        myAnimation.setCycleCount(Timeline.INDEFINITE);
        myAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(speed), e -> playGuess()));
        myAnimation.play();
    }

    /**
     * Create the game's "scene": what shapes will be in the game and their starting properties.
     */
    public Scene setupDisplay (int width, int height, Paint background) {
        Group root = new Group();
        // show letters available for guessing
        root = displayAvailableLetters(root);
        // show "hanged man" simply as a number that counts down
        root = displayNumGuesses(width, height, root);
        // show word being guessed, with letters hidden until they are guessed
        root = displaySecretWord(root);
        // create place to see and interact with the shapes
        myScene = new Scene(root, width, height, background);
        myScene.setOnKeyPressed(e -> Guesser.handleKeyInput(e.getCode()));
        return myScene;
    }

    private Group displaySecretWord(Group root) {
        mySecretWordDisplay = new ArrayList<>();
        for (int k = 0; k < myDisplayWord.toString().length(); k += 1) {
            Text displayLetter = new Text(200+20*k, 500, myDisplayWord.toString().substring(k, k+1));
            displayLetter.setFont(Font.font("Arial", FontWeight.BOLD, 40));
            mySecretWordDisplay.add(displayLetter);
            root.getChildren().add(displayLetter);
        }
        return root;
    }

    private Group displayNumGuesses(int width, int height, Group root) {
        myNumGuessesLeftDisplay = new Text(width /2 - 100, height /2 + 100, ""+myNumGuessesLeft);
        myNumGuessesLeftDisplay.setFont(Font.font("Verdana", FontWeight.BOLD, 300));
        root.getChildren().add(myNumGuessesLeftDisplay);
        return root;
    }

    private Group displayAvailableLetters(Group root) {
        myLettersLeftToGuessDisplay = new ArrayList<>();
        for (int k = 0; k < ALPHABET.length(); k += 1) {
            Text displayLetter = new Text(50+20*k, 50, ALPHABET.substring(k, k+1));
            displayLetter.setFont(Font.font("Arial", FontWeight.BOLD, 20));
            myLettersLeftToGuessDisplay.add(displayLetter);
            root.getChildren().add(displayLetter);
        }
        return root;
    }

    /**
     * Play one round of the game.
     */
    public void playGuess () {
        // has user guessed?
        if (myGuess == null) {
            return;
        }
        // handle only valid guesses
        CheckGuess();
        myGuess = null;

        // check for end of game
        if (myNumGuessesLeft == 0) {
            System.out.println("YOU ARE HUNG!!!");
        }
        else if (SecretKeeper.checkDisplay(myDisplayWord)) {
            System.out.println("YOU WIN!!!");
        }
        else {
            return;
        }
        // stop responding to key events when game is over
        myScene.setOnKeyPressed(null);
        // stop guessing when game is over
        myAnimation.stop();
    }
    private void CheckGuess(){
        if (myGuess.length() == 1 && ALPHABET.contains(myGuess)) {
            int index = myLettersLeftToGuess.indexOf(myGuess);
            // do not count repeated guess as a miss
            if (index < 0) {
                return;
            }
            // record guess
            myLettersLeftToGuess.setCharAt(index, ' ');
            // check for guess in secret word
            if (SecretKeeper.CheckHit(myGuess)) { //SECRET KEEPER
                myDisplayWord.update(myGuess.charAt(0), SecretKeeper.getSecret());
            }else{
                myNumGuessesLeft--;
            }
            // update letters displayed to the user
            UpdateLivesAndLetters();
        }
        else {
            System.out.println("Please enter a single alphabetic letter ...");
        }
    }


    private void UpdateLivesAndLetters() {
        for (int k = 0; k < myLettersLeftToGuess.length(); k += 1) {
            myLettersLeftToGuessDisplay.get(k).setText(myLettersLeftToGuess.substring(k, k+1));
        }
        myNumGuessesLeftDisplay.setText(""+myNumGuessesLeft);
        for (int k = 0; k < myDisplayWord.toString().length(); k += 1) {
            mySecretWordDisplay.get(k).setText(myDisplayWord.toString().substring(k, k+1));
        }
    }

//    // Record user's input to be used in the game loop
//    private void handleKeyInput (KeyCode code) {
//        myGuess = code.getChar().toLowerCase();
//    }
}
