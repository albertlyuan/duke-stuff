package game.roulette.bets;

import game.roulette.Bet;
import game.roulette.Wheel;
import game.util.ConsoleReader;


public class RedBlack extends Bet {
    private String myChoice;


    public RedBlack () {
        this("Red or Black", 1);
    }

    public RedBlack (String description, int odds) {
        super(description, odds);
        // set to non-null value in case isMade() is called before place()
        myChoice = "";
    }

    /**
     * @see Bet#place()
     */
    @Override
    public void place () {
        myChoice = ConsoleReader.promptOneOf("Please bet", Wheel.BLACK, Wheel.RED);
    }

    /**
     * @see Bet#isMade(int, String)
     */
    @Override
    public boolean isMade (int number,  String color) {
        return color.equals(myChoice);
    }
}
