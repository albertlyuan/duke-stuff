package bowling;

import java.util.ArrayList;
import java.util.List;


/**
 * Represents a complete game of bowling using standard scoring rules:
 *   https://www.bowl.com/Welcome/Welcome_Home/Keeping_Score/
 *
 * @author Robert C. Duvall
 */
public class Game {
    public static final int MAX_NUM_FRAMES = 10;
    public static final String VALUE_SEPARATOR = ",";

    private List<Frame> myFrames;


    /**
     * Create game from data about its rolls.
     */
    public Game (String rolls) {
        myFrames = new ArrayList<>();
        for (String v : rolls.split(VALUE_SEPARATOR)) {
            myFrames.add(new Frame(v));
        }
    }

    /**
     * Returns score for entire game
     */
    public int getScore () {
        return getScoreUpTo(MAX_NUM_FRAMES);
    }

    /**
     * Returns score for partial game, up to the given frame
     */
    public int getScoreUpTo (int frame) {
        int score = 0;
        for (int k = 0; k < frame; k+=1) {
            Frame f = myFrames.get(k);
            score += f.getTotalScore();
            if (k+1 < MAX_NUM_FRAMES) {
                if (f.isSpare()) {
                    score += getSpareBonus(k);
                }
                else if (f.isStrike()) {
                    score += getStrikeBonus(k);
                }
            }
            /**
             * needs to handle the 3 rolls in the tenth frame here
             */
        }
        return score;
    }

    // returns bonus for a spare: score of next roll
    private int getSpareBonus (int currentFrame) {
        return myFrames.get(currentFrame + 1).getRollScore(0);
    }

    // returns bonus for a strike: score of next two rolls
    private int getStrikeBonus (int currentFrame) {
        Frame next = myFrames.get(currentFrame + 1);
        int bonus = next.getTotalScore();
        if (next.getNumRolls() < 2) {
            bonus += myFrames.get(currentFrame + 2).getRollScore(0);
        }
        return bonus;
    }
}
