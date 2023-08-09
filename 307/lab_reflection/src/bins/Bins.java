package bins;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.function.Consumer;

/**
 * Runs a number of algorithms that try to fit files onto disks.
 */
public class Bins {
    public static final String DATA_FILE = "example.txt";
    public static final String WORST_FIT = "worst-fit";
    public static final String WORST_FIT_DECREASING = "worst-fit decreasing";
    public static final String WORST_FIT_RANDOM = "worst-fit random";

    /**
     * Reads list of integer data from the given input.
     *
     * @param input tied to an input source that contains space separated numbers
     * @return list of the numbers in the order they were read
     */
    public List<Integer> readData (Scanner input) {
        List<Integer> results = new ArrayList<>();
        while (input.hasNext()) {
            results.add(input.nextInt());
        }
        return results;
    }

    /**
     * Returns sum of all values in given list.
     */
    public int getTotalSize (List<Integer> data) {
        int total = 0;
        for (int size : data) {
            total += size;
        }
        return total;
    }

    // add files to collection of Disks
    private Collection<Disk> calculateDisksNeeded (List<Integer> data) {
        PriorityQueue<Disk> pq = new PriorityQueue<>();
        int diskId = 1;
        pq.add(new Disk(diskId));
        for (int size : data) {
            Disk d = pq.peek();
            if (d.freeSpace() >= size) {
                pq.poll();
            } else {
                diskId += 1;
                d = new Disk(diskId);
            }
            d.add(size);
            pq.add(d);
        }
        return pq;
    }

    // print contents of given pq and a header, description
    private void printDisksUsed (Collection<Disk> disks, String description) {
        System.out.println();
        System.out.println(description);
        System.out.println("number of disks used: " + disks.size());
        for (Disk d : disks) {
            System.out.println(d);
        }
        System.out.println();
    }

    // add contents of given data to pq and print results
    private void fitDisksAndPrint (List<Integer> data, String description, Consumer<List<Integer>> con) {
        List<Integer> copy = new ArrayList<>(data);
        // TODO: replace these conditional by calling the method of new parameter
        con.accept(copy);
        Collection<Disk> pq = calculateDisksNeeded(copy);
        printDisksUsed(pq, description);
    }

    /**
     * The main program.
     */
    public static void main (String args[]) {
        Bins b = new Bins();
        Scanner input = new Scanner(Bins.class.getClassLoader().getResourceAsStream(DATA_FILE));
        List<Integer> data = b.readData(input);
        int total = b.getTotalSize(data);

        System.out.println("total size = " + (double) total / Disk.GIGABYTE + "GB");
        // TODO: call these methods using lambdas to implement the change to the collection
        Map<String, Consumer<List<Integer>>> map = Map.of(WORST_FIT, list -> {},
            WORST_FIT_DECREASING, list -> Collections.sort(list, Collections.reverseOrder()),
            WORST_FIT_RANDOM, list -> Collections.shuffle(list));
        for(String description : map.keySet()){
            b.fitDisksAndPrint(data,description,map.get(description));
        }
    }
}
