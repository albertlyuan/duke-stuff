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

public class SecretKeeper {
    private String mySecretWord;

    public SecretKeeper(String word){
        mySecretWord = word;
        System.out.println("*** " + mySecretWord);
    }
    public SecretKeeper(){
        this(null);
    }
    public boolean CheckHit(String guess) {
        if (! mySecretWord.contains(guess)) {
            return false;
        }
        return true;
    }
    public String getSecret(){
        return mySecretWord;
    }
    public boolean checkDisplay(DisplayWord myDisplayWord){
        if (myDisplayWord.equals(mySecretWord)){
            return true;
        }
        return false;
    }
    public DisplayWord createDisplay(){
        return new DisplayWord(mySecretWord);
    }
}
