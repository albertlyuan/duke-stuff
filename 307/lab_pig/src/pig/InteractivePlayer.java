package pig;

import java.util.Scanner;


public class InteractivePlayer extends Player {
    // variables
    private Scanner myScanner;


    public InteractivePlayer() {
        super("Human");
        System.out.println("Hello Player! Please enter your name.");
        myScanner = new Scanner(System.in);
        setName(myScanner.nextLine());
    }

    /**
     * Prompt the player to decide when to stop rolling.
     */
    @Override
    public boolean rollAgain(int totalSoFar) {
        System.out.println("Score is " + getScore() + " Round score is " + totalSoFar);
        System.out.println("Do you want to roll again? Y/N");
        String answer = myScanner.nextLine();
        return answer.toUpperCase().charAt(0) == 'Y';
    }
}
