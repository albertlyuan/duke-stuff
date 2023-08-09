package game.roulette.bets;

import game.roulette.Bet;
import game.util.ConsoleReader;
import java.util.List;


/**
 * Class that encapsulates possible Bet options and how they are created/chosen so other classes,
 * especially the Game logic, does not need to be aware of the implementation details.
 *
 * @author Robert C. Duvall
 */
public class BetFactory {
    // bets player can make
    // TODO: replace this with reflection and then remove completely by reading from property file
    private static final List<Bet> POSSIBLE_BETS = List.of(
        new RedBlack(),
        new OddEven(),
        new ThreeConsecutive()
    );


    /**
     * Prompt the user to make a bet from a menu of choices.
     */
    public Bet promptForBet () {
        System.out.println("You can make one of the following types of bets:");
        for (int k = 0; k < POSSIBLE_BETS.size(); k += 1) {
            System.out.println(String.format("%d) %s", (k + 1), POSSIBLE_BETS.get(k)));
        }
        int whichBet = ConsoleReader.promptRange("Please make a choice", 1, POSSIBLE_BETS.size()) - 1;
        return POSSIBLE_BETS.get(whichBet);
    }
}
