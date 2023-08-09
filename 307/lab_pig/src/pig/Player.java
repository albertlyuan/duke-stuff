package pig;


public class Player {
    // constants
    public static final int WIN_SCORE = 100;
    // variables
    private String myName;
    private int myScore;


    public Player(String name) {
        myName = name;
        myScore = 0;
    }

    // each player must provide logic for deciding to roll again
    public boolean rollAgain(int totalSoFar) {
        return false;
    }

    public boolean hasWon() {
        return myScore >= WIN_SCORE;
    }

    public void resetScore() {
        myScore = 0;
    }

    public void addToScore(int thisRound) {
        myScore += thisRound;
    }

    public String getName() {
        return myName;
    }

    public int getScore() {
        return myScore;
    }

    protected void setName(String name) {
        myName = name;
    }

    @Override
    public String toString() {
        return myName + ": " + myScore;
    }
}
