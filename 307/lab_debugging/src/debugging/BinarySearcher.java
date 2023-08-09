package debugging;

import java.util.List;


/**
 * Class that implements standard binary search algorithm:
 *   https://en.wikipedia.org/wiki/Binary_search_algorithm
 */
public class BinarySearcher {
    public static final int DATA_NOT_FOUND = -1;

    // TODO: find and fix the bug in this method
    public int findIndex (List<Integer> data, int key) {
        int low = 0;
        int high = data.size() - 1;

        while (low <= high) {
            int midIdx = (low + high) / 2;
            int midVal = data.get(midIdx);

            if (midVal < key) {
                low = midIdx + 1;
            }
            else if (midVal > key) {
                high = midIdx - 1;
            }
            else {
                return midIdx;
            }
        }
        return DATA_NOT_FOUND;
    }

    public static void main (String[] args) {
        BinarySearcher bs = new BinarySearcher();

        System.out.println("Expected 0, got: " + bs.findIndex(List.of(4, 5), 4));
        System.out.println("Expected 1, got: " + bs.findIndex(List.of(4, 5), 5));
        System.out.println("Expected 5, got: " + bs.findIndex(List.of(0, 1, 2, 3, 4, 5, 6, 7), 5));
        System.out.println("Expected 5, got: " + bs.findIndex(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8), 5));
    }
}
