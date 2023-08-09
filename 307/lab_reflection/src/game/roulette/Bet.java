package game.roulette;


/**
 * Represents player's attempt to bet on outcome of the roulette wheel's spin.
 * 
 * @author Robert C. Duvall
 */
public abstract class Bet {
    private String myDescription;
    private int myOdds;

    /**
     * Constructs a bet with the given name and odds.
     * 
     * @param description name of this kind of bet
     * @param odds odds given by the house for this kind of bet
     */
    public Bet (String description, int odds) {
        myDescription = description;
        myOdds = odds;
    }

    /**
     * @return amount to pay out for winning this bet
     */
    public int payout (int wager) {
        return myOdds * wager;
    }

    /**
     * @return string representation of this bet
     */
    public String toString () {
        return myDescription;
    }

    /**
     * Place the chosen bet by prompting user for specific information need to complete that bet.
     */
    public abstract void place ();

    /**
     * Checks if the chosen bet is won or lost given user's choice and result of spinning the wheel.
     */
    public abstract boolean isMade (int number,  String color);
}
