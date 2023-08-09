package game.roulette.bets;

import game.roulette.Bet;
import game.util.ConsoleReader;


public class ThreeConsecutive extends Bet {
    private int myStart;


    public ThreeConsecutive () {
        this("Three in a Row", 11);
    }

    public ThreeConsecutive (String description, int odds) {
        super(description, odds);
        // set to invalid value in case isMade() is called before place()
        myStart = -1;
    }

    /**
     * @see Bet#place()
     */
    @Override
    public void place () {
        myStart = ConsoleReader.promptRange("Enter first of three consecutive numbers", 1, 34);
    }

    /**
     * @see Bet#isMade(int, String)
     */
    @Override
    public boolean isMade (int number,  String color) {
        return myStart >= 0 && myStart <= number && number < myStart + 3;
    }
}
