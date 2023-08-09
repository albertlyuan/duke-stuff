package debug;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class FixedSizeContainerTest {
    private FixedSizeContainer myContainer = null;

    @BeforeEach
    public void setUp () {
        myContainer = new FixedSizeContainer();
    }

    // do not change this test
    @Test
    public void testSizeChangeWithAdd () {
        int expected = 3;
        myContainer.add("Alligator");
        myContainer.add("Bear");
        myContainer.add("Camel");
        assertEquals(expected, myContainer.getNumElements(), "Add size");
    }

    // do not change this test
    @Test
    public void testObjectIsStored () {
        String expected = "Alligator";
        myContainer.add("Alligator");
        assertEquals(expected, myContainer.get(0), "Add should be same reference");
    }

    // do not change this test
    @Test
    public void testSizeChangeWithRemove () {
        int expected = 1;
        myContainer.add("Alligator");
        myContainer.add("Bear");
        myContainer.remove("Alligator");
        assertEquals(expected, myContainer.getNumElements(), "Remove size");
    }

    // do not change this test
    @Test
    public void testObjectIsRemoved () {
        String expected = "Alligator";
        myContainer.add("Alligator");
        myContainer.add("Bear");
        myContainer.remove("Bear");

        assertEquals(expected, myContainer.get(0), "Remove should be same reference");
    }
    @Test
    public void testNewConstructor() {
        String expected = "Bear";
        myContainer.add("Alligator");
        myContainer.add("Bear");
        myContainer.remove("Alligator");
        assertEquals(expected, myContainer.get(0), "Remove should be same reference");
    }
    // TODO: add new tests here
    @Test
    public void testObjectCorrectRemoved () {
        String expected = "Bear";
        myContainer.add("Alligator");
        myContainer.add("Bear");
        myContainer.remove("Alligator");
        assertEquals(expected, myContainer.get(0), "Remove should be same reference");
    }
    @Test
    public void testObjectAddedOverflow () {
        myContainer.add("A");
        myContainer.add("B");
        myContainer.add("c");
        myContainer.add("d");
        myContainer.add("e");
        myContainer.add("f");
        myContainer.add("g");
        myContainer.add("h");
        myContainer.add("i");
        myContainer.add("j");
        myContainer.add("k");
        myContainer.add("l");
    }
    @Test
    public void testObjectRemoveNonexistant () {
        myContainer.add("a");
        myContainer.remove("a");
        myContainer.remove("a");
        assertEquals(0,myContainer.getNumElements(), "should have 0 elements" );
    }
    @Test
    public void testObjectActuallyRemoves () {
        myContainer.add("a");
        myContainer.add("b");
        myContainer.remove("b");
        assertEquals(null,myContainer.get(1), "should have nothing at index 1" );
    }
}
