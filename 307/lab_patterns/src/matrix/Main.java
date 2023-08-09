package matrix;

import java.util.Iterator;


/**
 * Basic driver for iterating over a 2D grid structure without worrying about its implementation.
 *
 * @author Robert C. Duvall
 */
public class Main {
    // simple example of iterating over all elements in the matrix without regard to its structure
    private int getTotal (Matrix mat) {
        int total = 0;
        for (int val : mat) {
            //System.out.println(val);
            total += val;
        }
        return total;
    }

    // OLD style way of using iterator that has been replaced by the simpler syntax of the foreach loop
    private int getTotalUsingIteratorDirectly (Matrix mat) {
        int total = 0;
        Iterator<Integer> iter = mat.iterator();
        while (iter.hasNext()) {
            int val = iter.next();
            //System.out.println(val);
            total += val;
        }
        return total;
    }

    // some simple "tests" for the code (all the answers should be 45 :)
    public static void main (String[] args) {
        Main m = new Main();

        // Note, new Java syntax so must set Project Language Level to "13 Preview" under File -> Project Structure
        System.out.println("total = " + m.getTotal(new Matrix("""
                45
                """)));
        System.out.println("total = " + m.getTotal(new Matrix("""
                1 2 3
                4 5 6
                7 8 9
                """)));
        System.out.println("total = " + m.getTotalUsingIteratorDirectly(new Matrix("""
                0 1 2 3 4
                5 6 7 8 9
                """)));
        // this test may not work based on your assumptions
        System.out.println("total = " + m.getTotal(new Matrix("""
                0
                1 2
                3 4 5
                6 7 8 9
                """)));
    }
}
