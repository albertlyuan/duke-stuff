package hangman;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
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
public class HangmanGame {
    // constant
    public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    public static final String CLEVER = "Clever";
    public static final String INTERACTIVE = "Interactive";
    public static final String SIMPLE = "Simple";

    // 2 Players
    private String secretkeeperType;
    private SecretKeeper SecretKeeper;

    private String guesserType;
    private Guesser Guesser;

    // how many guesses are remaining
    private int myNumGuessesLeft;
    private static int startingLives;

    // what is shown to the user
    private DisplayWord myDisplayWord;
    // tracks letters guessed
    private StringBuilder myLettersLeftToGuess;
    // current player's guess
    private String myGuess;
    // JFX variables
    private Scene myScene;
    private Timeline myAnimation;
    private Text myNumGuessesLeftDisplay;
    private List<Text> mySecretWordDisplay;
    private List<Text> myLettersLeftToGuessDisplay;
    private ArrayList<Shape> hangmanComponents;

    /**
     * Create Hangman game with the given dictionary of words to play a game with words
     * of the given length and giving the user the given number of chances.
     */
    public HangmanGame (HangmanDictionary dictionary, int wordLength, int numGuesses, String guessertype, String secretkeepertype) {
        startingLives = numGuesses;
        myNumGuessesLeft = numGuesses;
        myLettersLeftToGuess = new StringBuilder(ALPHABET);
        guesserType = guessertype;
        secretkeeperType = secretkeepertype;
        SecretKeeper = new SecretKeeper(dictionary, wordLength,secretkeeperType);
        Guesser = new Guesser(dictionary,wordLength,guesserType);
        myDisplayWord = SecretKeeper.createDisplay();

        // SHOULD NOT PUBLIC, but makes it easier to test
    }

    /**
     * Start the game by animating the display of changes in the GUI every speed seconds.
     */
    public void start (double speed) {
        myAnimation = new Timeline();
        myAnimation.setCycleCount(Timeline.INDEFINITE);
        myAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(speed), e -> {
            if (guesserType != "Interactive"){
                myScene.setOnKeyPressed(null);
                myGuess = Guesser.getGuess();
            }
            playGuess();
        }));
        myAnimation.play();
    }

    /**
     * Create the game's "scene": what shapes will be in the game and their starting properties.
     */
    public Scene setupDisplay (int width, int height, Paint background) {
        Group root = new Group();
        root = displayHangman(width,height,root);

        // show letters available for guessing
        root = displayAvailableLetters(root);
        // show "hanged man" simply as a number that counts down
        root = displayNumGuesses(width, height, root);
        // show word being guessed, with letters hidden until they are guessed
        root = displaySecretWord(root);


        // create place to see and interact with the shapes
        myScene = new Scene(root, width, height, background);
        //if interactive guesser
        if (guesserType == INTERACTIVE){
            myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        }
        return myScene;
    }

    private Group displayHangman(int width, int height, Group root) {


        Line base1 = new Line(width/2 + 50, height/2 + 100, width/2 ,height/2 + 100);
        Line base2 = new Line(width/2, height/2 + 100, width/2 - 50,height/2 + 100);
        Line base3 = new Line(width/2 - 50, height/2 + 100, width/2 - 100,height/2 + 100);
        Line base4 = new Line(width/2 - 75, height/2 + 90, width/2 - 25,height/2 + 90);
        Line pole1 = new Line(width/2 - 50, height/2 + 80, width/2 - 50,height/2 + 30);
        Line pole2 = new Line(width/2 - 50, height/2 + 20 , width/2 - 50,height/2 -30);
        Line pole3 = new Line(width/2 - 50, height/2 - 40, width/2 - 50,height/2 - 90);
        Line pole4 = new Line(width/2 - 50, height/2 - 100, width/2 - 50,height/2 - 150);
        Line pole5 = new Line(width/2 - 50, height/2 - 160, width/2 - 50,height/2 - 210);
        Line top = new Line(width/2 - 50, height/2 - 220, width/2 + 50 ,height/2 - 220);
        Line rope = new Line(width/2 + 50, height/2 - 210, width/2 + 50, height/2 - 180);

        Line neck = new Line(width/2 + 50, height/2 - 50, width/2 + 50, height/2 - 30);
        Line torso = new Line(width/2 + 50, height/2 - 30, width/2 + 50, height/2 + 20);
        Line arm1 = new Line(width/2 + 50, height/2 - 30, width/2 , height/2 - 10);
        Line arm2 = new Line(width/2 + 50, height/2 - 30, width/2 + 100, height/2 - 10);
        Line leg1 = new Line(width/2 + 50, height/2 + 20, width/2 , height/2 + 40);
        Line leg2 = new Line(width/2 + 50, height/2 + 20, width/2 + 100, height/2 + 40);

        Line brow1 = new Line(width /2 + 15,height /2 - 130,width /2 + 35,height /2 - 130);
        Line brow2 = new Line(width /2 + 65,height /2 - 130,width /2 + 85,height /2 - 130);
        Circle eye1 = new Circle(width/2 + 25, height/2 - 110, 5,Color.TRANSPARENT);
        eye1.setStroke(Color.BLACK);
        Circle eye2 = new Circle(width/2 + 75, height/2 - 110, 5,Color.TRANSPARENT);
        eye2.setStroke(Color.BLACK);
        Circle nose = new Circle(width/2 + 50, height/2 - 90, 5,Color.TRANSPARENT);
        nose.setStroke(Color.BLACK);
        Line mouth = new Line(width/2 + 40,height/2 - 70,width/2 + 60,height/2 - 70);

        Circle head = new Circle(width /2 + 50,height /2 - 100,50, Color.THISTLE);
        head.setStroke(Color.BLACK);
        Circle ear1 = new Circle(width/2 + 90, height/2 - 100, 20,Color.THISTLE);
        ear1.setStroke(Color.BLACK);
        Circle ear2 = new Circle(width/2 + 10, height/2 - 100, 20,Color.THISTLE);
        ear2.setStroke(Color.BLACK);

        hangmanComponents = new ArrayList<Shape>(Arrays.asList(
                base1,base2,base3,base4,
                pole1,pole2,pole3,pole4,pole5,
                top,rope,
                ear1, ear2, head,
                neck,torso,arm1,arm2,leg1,leg2,
                brow1,brow2,eye1,eye2,
                nose,mouth));
        for (Shape s : hangmanComponents){
            s.setStroke(Color.TRANSPARENT);
        }

        root.getChildren().addAll(hangmanComponents);

        return root;
    }

    private Group displaySecretWord(Group root) {
        /**
         * initiates the secretword display from the current mydisplayword
         */
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
        /**
         * initiates the numGuesses display from the current value of mynumguessesleft
         */
        myNumGuessesLeftDisplay = new Text(width /2 - 100, height /2 + 100, ""+myNumGuessesLeft);
        myNumGuessesLeftDisplay.setFont(Font.font("Verdana", FontWeight.BOLD, 300));
        myNumGuessesLeftDisplay.setFill(Color.TRANSPARENT);
        root.getChildren().add(myNumGuessesLeftDisplay);
        return root;
    }

    private Group displayAvailableLetters(Group root) {
        /**
         * initiates the lettersleft display from ALPHABET string
         */
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
        else if (SecretKeeper.checkDisplay()) {
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
        /**
         * checks that the guess is valid
         * updates myletterslefttoguess
         * checks if letter is a correct guess
         * updates lives and letters displays
         */
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
                myDisplayWord = SecretKeeper.updateDisplay(myGuess.charAt(0));
                if (guesserType == CLEVER){
                    Guesser.updateLetterDict(myLettersLeftToGuess, myDisplayWord);
                }
            }else{
                myNumGuessesLeft--;
                if (guesserType == CLEVER){
                    Guesser.updateLetterDict(myLettersLeftToGuess, myDisplayWord);
                }
            }
            // update letters displayed to the user
            UpdateLivesAndLetters();

        }
        else {
            System.out.println("Please enter a single alphabetic letter ...");
        }
    }


    private void UpdateLivesAndLetters() {
        /**
         * updates lettersleft, numguessesleft, and secretWord displays
         */
        for (int k = 0; k < myLettersLeftToGuess.length(); k += 1) {
            myLettersLeftToGuessDisplay.get(k).setText(myLettersLeftToGuess.substring(k, k+1));
        }
        myNumGuessesLeftDisplay.setText(""+myNumGuessesLeft);
        float fracComponents = ((startingLives-myNumGuessesLeft)*26)/startingLives;
        int numComponentsToDisplay = (int)fracComponents;

        for (int i=0;i<numComponentsToDisplay;i++){
            hangmanComponents.get(i).setStroke(Color.BLACK);
        }
        for (int k = 0; k < myDisplayWord.toString().length(); k += 1) {
            mySecretWordDisplay.get(k).setText(myDisplayWord.toString().substring(k, k+1));
        }
    }

    // Record user's input to be used in the game loop
    private void handleKeyInput (KeyCode code) {
        myGuess = code.getChar().toLowerCase();
    }
}
