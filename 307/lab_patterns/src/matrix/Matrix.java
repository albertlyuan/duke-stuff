package matrix;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;


/**
 * Simple class that reads in String data to construct a 2D data structure and allows other classes to loop over all of its elements.
 *
 * @author Albert Yuan Luis Pereda Amaya
 */
public class Matrix implements Iterable<Integer> {
    private List<List<Integer>> myData;

    /**
     * Construct class from formatted data (space separated values on separate lines)
     */
    public Matrix (String values) {
        myData = readData(values);
    }

    /**
     * @see Iterable#iterator()
     */
    @Override
    public Iterator<Integer> iterator () {
        return new MatrixIterator();
    }


    // Read formatted data of numbers into data structure
    private List<List<Integer>> readData (String values) {
        List<List<Integer>> results = new ArrayList<>();
        for (String line : values.split("\n")) {
            List<Integer> row = new ArrayList<>();
            for (String val : line.split(" ")) {
                row.add(Integer.valueOf(val));
            }
            results.add(row);
        }
        return results;
    }


    // TODO: Implement this class
    private class MatrixIterator implements Iterator<Integer> {
        private int currRow = 0;
        private int currCol = 0;

        @Override
        public boolean hasNext () {
            if (currRow < myData.size() && currCol < myData.get(currRow).size()){
                return true;
            }
            return false;
        }

        @Override
        public Integer next () {
            // assumes there is at least one more element
            if (! hasNext()) {
                throw new NoSuchElementException();
            }
            int ret = myData.get(currRow).get(currCol);
            if (currCol + 1 >= myData.get(currRow).size()){
                currCol = 0;
                currRow += 1;
            }else{
                currCol += 1;
            }
            return ret;
        }
    }
}
