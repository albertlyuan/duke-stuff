package pig;

import java.util.Random;


public class Game {
    // constants
    private static final String LOSER_SPIN = "grunt";
    private static final int LOSER_ROLL = 1;
    // variables
    private Player myPlayer1;
    private Player myPlayer2;
    private Random myDie;
    private Spinner mySpinner;


    public Game() {
        myPlayer1 = new InteractivePlayer();
        myPlayer2 = new ComputerPlayer();
        myDie = new Random();
        mySpinner = new Spinner();
    }

    /**
     * Method will play one game between the players.
     */
    public void playGame() {
        printStartGameMessage();
        Player whoseTurn = myPlayer1;
        while (! isWinner()) {
            int roundScore = takeATurn(whoseTurn);
            whoseTurn.addToScore(roundScore);
            // switch whose turn it is
            whoseTurn = (whoseTurn == myPlayer1) ? myPlayer2 : myPlayer1;

        }
        printEndGameMessage();
    }

    /**
     * One player's turn.  Ends because
     * - roll a 1
     * - spin a "GRUNT"
     * - or Player decides to stop
     * <p>
     * Return the number of points to add to the player
     */
    private int takeATurn(Player whoseTurn) {
        printStartRoundMessage(whoseTurn);
        int roundScore = 0;
        boolean keepGoing = true;
        while (keepGoing) {
            int roll = myDie.nextInt(5) + 1;
            String spin = mySpinner.spin();
            System.out.println(roll + " " + spin);

            if (spin.equals(LOSER_SPIN.toUpperCase())) {
                System.out.println("Too bad!  Lose all your points.");
                whoseTurn.resetScore();
                return 0;
            }
            else if (roll == LOSER_ROLL) {
                System.out.println("Lose a turn.");
                return 0;
            }
            else {
                roundScore = roundScore + roll;
                System.out.println("Round total is currently: " + roundScore);
                if (whoseTurn.getScore() + roundScore >= whoseTurn.WIN_SCORE) break;
                keepGoing = whoseTurn.rollAgain(roundScore);
            }
        }
        return roundScore;
    }

    // True if one of the players has won the game.
    private boolean isWinner() {
        return myPlayer1.hasWon() || myPlayer2.hasWon();
    }

    // These methods are for printing messages to the console to follow the game.
    private void printStartRoundMessage(Player whoseTurn) {
        System.out.println(String.format("New Round!  %s's turn.", whoseTurn.getName()));
        System.out.println(myPlayer1);
        System.out.println(myPlayer2);
    }

    private void printEndGameMessage() {
        if (myPlayer1.hasWon()) {
            System.out.println("Winner is " + myPlayer1.getName());
        } else {
            System.out.println("Winner is " + myPlayer2.getName());
        }
    }

    private void printStartGameMessage() {
        // this is a Java 15 feature!
        System.out.println("""
                Let's Play Pig!
                """);
    }
}
