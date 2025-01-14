package game.roulette;


/**
 * Represents a Gambler simply as an amount to money to be spent.
 * 
 * @author Robert C. Duvall
 */
public class Gambler {
    private String myName;
    private int myMoney;

    /**
     * Constructs a gambler with the given name and an initial bankroll.
     * 
     * @param name used to refer to the gambler
     * @param money initial amount of the money the gambler has to spend
     */
    public Gambler (String name, int money) {
        myName = name;
        myMoney = money;
    }

    /**
     * @return amount of money the gambler has left
     */
    public int getBankroll () {
        return myMoney;
    }

    /**
     * @return true if the gambler has money left to spend, false otherwise
     */
    public boolean isSolvent () {
        return (myMoney > 0);
    }

    /**
     * Changes the gambler's bankroll to reflect the given amount won or lost.
     * 
     * @param amount money won (positive) or lost (negative) by the gambler
     */
    public void updateBankroll (int amount) {
        myMoney += amount;
    }

    /**
     * @return name of the gambler
     */
    @Override
    public String toString () {
        return myName;
    }
}
