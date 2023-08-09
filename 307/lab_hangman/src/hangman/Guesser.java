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

public class Guesser {
    private String myGuess;
    public Guesser(){

    }
    // Record user's input to be used in the game loop
    public void handleKeyInput (KeyCode code) {
        myGuess = code.getChar().toLowerCase();
    }
}
