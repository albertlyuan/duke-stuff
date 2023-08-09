package debug;


/**
 * A very buggy example in a few lines of code!
 *
 * How you fix the bugs is entirely up to you, but fix each ONE AT A TIME
 * to emphasize thinking about different strategies for what to test.
 *
 * @author Anonymous :)
 */
public class FixedSizeContainer {
    private int myInitialCapacity = 10;
    private int myCurrentSize = 0;
    private String[] myInternalArray;


    /**
     * Create Container with default capacity.
     */
    public FixedSizeContainer () {
        this(10);
    }

    /**
     * Create Container with given capacity.
     */
    public FixedSizeContainer (int initialCapacity) {
        myInternalArray = new String[initialCapacity];
    }

    /**
     * Returns number of non-null elements added to container.
     */
    public int getNumElements () {
        return myCurrentSize;
    }

    /**
     * Returns container's total capacity.
     */
    public int getCapacity () {
        return myInitialCapacity;
    }

    /**
     * Add given element to end of container.
     * Bug 1 - if you try to add an 11th item then it throws an error (basically adding 1 more than size array throws error)
     */
    public boolean add (String element) {
        myInternalArray[myCurrentSize] = element;
        myCurrentSize += 1;
        return true;
    }

    /**
     * Bug 2 - doesn't remove
     * Bug 3 - removes correct item
     * Bug 4 - if you remove when there are no items it will think currentSize is negative
     * Removes first instance of given element from container.
     */
    public void remove (String element) {
        myCurrentSize -= 1;
    }

    /**
     * Returns element in container at given index, possibly null.
     */
    public String get (int index) {
        return myInternalArray[index];
    }
}
