package pig;

import java.util.List;
import java.util.Random;


public class Spinner {
    // constants
    public static final List<String> RESULTS = List.of("Oink", "Squeal", "Snort", "GRUNT");
    // This means that "oink" should happen 20% of the time, "Squeal" 50%, etc...
    public static final List<Double> PROBABILITIES = List.of(0.2, 0.5, 0.27, 0.03);
    // variables
    private Random mySpinRandom;


    public Spinner() {
        mySpinRandom = new Random();
    }

    /**
     * Returns random word based on spinner's probabilities
     */
    public String spin() {
        return numToWord(mySpinRandom.nextDouble());
    }

    // Turn random number into one of the spinner words based on the given probabilities.
    private String numToWord(double spinNumber) {
        int index = 0;
        double low = 0;
        boolean done = false;
        String result = "";
        while (!done) {
            double high = PROBABILITIES.get(index) + low;
            if (spinNumber >= low && spinNumber < high) {
                result = RESULTS.get(index);
                done = true;
            } else {
                low = high;
                index += 1;
            }
        }
        return result;
    }
}
