package game.roulette.bets;

import game.roulette.Bet;
import game.util.ConsoleReader;


public class OddEven extends Bet {
    private String myChoice;


    public OddEven () {
        this("Odd or Even", 1);
    }

    public OddEven (String description, int odds) {
        super(description, odds);
        // set to non-null value in case isMade() is called before place()
        myChoice = "";
    }

    /**
     * @see Bet#place()
     */
    @Override
    public void place () {
        myChoice = ConsoleReader.promptOneOf("Please bet", "even", "odd");
    }

    /**
     * @see Bet#isMade(int, String)
     */
    @Override
    public boolean isMade (int number,  String color) {
        return (number % 2 == 0 && myChoice.equals("even")) ||
               (number % 2 == 1 && myChoice.equals("odd"));
    }
}
