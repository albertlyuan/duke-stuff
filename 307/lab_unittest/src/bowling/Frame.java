package bowling;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * Represents a frame in a game of bowling that can either be a strike, a spare, or open.
 *
 * By overriding standard Object methods this object can be stored properly in collections and,
 * by implementing Comparable, this object can be sorted alphabetically by default.
 *
 * @author Robert C. Duvall
 */
public class Frame {
    public static final int MAX_FRAME_SCORE = 10;
    public static final String VALUE_SEPARATOR = " ";

    private List<Integer> myRolls;


    /**
     * Create frame from data about its rolls.
     *
     * Data is in format: "roll1 roll2 ... rollN"
     */
    public Frame (String rolls) {
        myRolls = new ArrayList<>();
        for (String v : rolls.split(VALUE_SEPARATOR)) {
            myRolls.add(Integer.valueOf(v));
        }
    }

    /**
     * Returns number of rolls in this frame:
     *  - 1 indicates a strike
     *  - 2 is a typical frame
     *  - 3 should only be the last frame
     */
    public int getNumRolls () {
        return myRolls.size();
    }

    /**
     * Returns true if this frame represents a strike, otherwise false.
     */
    public boolean isStrike () {
        return myRolls.get(0) == MAX_FRAME_SCORE;
    }

    /**
     * Returns true if this frame represents a spare, otherwise false.
     */
    public boolean isSpare () {
        return getTotalScore() == MAX_FRAME_SCORE && getNumRolls() == 2 && getRollScore(0) != 10;
    }

    /**
     * not in use but could filter out bad input
     * @return - true if rolls could be real rolls (not negative, 2 rolls, score less than 10)
     */
    public boolean isValid() {
        if (getNumRolls() <= 2 && getTotalScore() <= 10) {
            for (Integer roll : myRolls) {
                if (roll > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns number of pins knocked down on the given roll in this frame.
     */
    public int getRollScore (int roll) {
        return myRolls.get(roll);
    }

    /**
     * Returns total number of pins knocked down in this frame.
     */
    public int getTotalScore () {
        int total = 0;
        for (int r : myRolls) {
            total += r;
        }
        return total;
    }

    /**
     * @see Object#toString()
     *
     * Note, this method was generated using IntelliJ
     */
    @Override
    public String toString () {
        return myRolls.toString();
    }

    /**
     * @see Object#equals(Object)
     *
     * Note, this method was generated using IntelliJ
     */
    @Override
    public boolean equals (Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Frame frame = (Frame)other;
        return myRolls.equals(frame.myRolls);
    }

    /**
     * @see Object#hashCode()
     *
     * Note, this method was generated using IntelliJ
     */
    @Override
    public int hashCode () {
        return Objects.hash(myRolls);
    }
}
