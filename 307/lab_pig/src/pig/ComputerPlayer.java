package pig;


public class ComputerPlayer extends Player {
    // constant
    private final int MIN_POINTS = 15;


    public ComputerPlayer() {
        super("R2D2");
    }

    /**
     * Computer will stop rolling if:
     * - It doesn't have 15 points yet (or MIN_POINTS)
     * - Stopping will win the game.
     */
    @Override
    public boolean rollAgain(int totalSoFar) {
        return (getScore() + totalSoFar) < Player.WIN_SCORE && totalSoFar < MIN_POINTS;
    }
}
