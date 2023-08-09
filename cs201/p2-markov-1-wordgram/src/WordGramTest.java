import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * These tests are meant to test a student's WordGram class.
 * Last modified February 7th, 2021
 * @author Daniel Hwang, Charles Lyu, et al.
 */
class WordGramTest {
    private WordGram[] myGrams;

    /**
     * This method initializes a series of order-3 WordGrams that we use for testing.
     */
    @BeforeEach
    void setUp() throws Exception {
        String str = "aa bb cc aa bb cc aa bb cc aa bb dd ee ff gg hh ii jj";
        String[] array = str.split("\\s+");
        myGrams= new WordGram[array.length-2];
        for(int k=0; k < array.length-2; k++){
            myGrams[k] = new WordGram(array,k,3);
        }
    }

    /**
     * This test checks if a WordGram returns the correct length.
     * Added Spring 2021
     */
    @Test
    public void testLength() {
        Assertions.assertAll("length equals 3",
                () -> assertTrue(myGrams[0].length() == 3, "length of wordGram 0"),
                () -> assertTrue(myGrams[1].length() == 3, "length of wordGram 1"),
                () -> assertTrue(myGrams[2].length() == 3, "length of wordGram 2")
        );
    }

    /**
     * This test checks if a WordGram was constructed correctly.
     * Added Spring 2021
     */
    @Test
    public void testConstructor() {
        Assertions.assertAll("test constructor, wordGram 4",
                () -> assertTrue(myGrams[4].wordAt(0).equals("bb"), "word at index 0 is bb"),
                () -> assertTrue(myGrams[4].wordAt(1).equals("cc"), "word at index 1 is cc"),
                () -> assertTrue(myGrams[4].wordAt(2).equals("aa"), "word at index 2 is aa")
        );
    }

    /**
     * This test checks if two WordGrams are the same.
     */
    @Test
    public void testEqualsTrue() {
        Assertions.assertAll("equals true",
                () -> assertTrue(myGrams[0].equals(myGrams[3]), "0 3"),
                () -> assertTrue(myGrams[0].equals(myGrams[6]), "0 6"),
                () -> assertTrue(myGrams[1].equals(myGrams[4]), "1 4"),
                () -> assertTrue(myGrams[2].equals(myGrams[5]), "2 5"),
                () -> assertTrue(myGrams[2].equals(myGrams[8]), "2 8")
        );
    }

    /**
     * This test checks if two WordGrams are different.
     */
    @Test
    public void testEqualsFalse() {
        Assertions.assertAll("equals false",
                () -> assertFalse(myGrams[0].equals(myGrams[2]), "0 2"),
                () -> assertFalse(myGrams[0].equals(myGrams[4]), "0 4"),
                () -> assertFalse(myGrams[2].equals(myGrams[3]), "2 3"),
                () -> assertFalse(myGrams[2].equals(myGrams[6]), "2 6"),
                () -> assertFalse(myGrams[7].equals(myGrams[8]), "7 8"),
                () -> assertFalse(myGrams[7].equals(myGrams[11]), "7 11")
        );
    }

    /**
     * This test checks if two supposedly equal WordGrams have the same hashCode.
     * In addition to checking the hashCode method, you should check the equals method if you fail this test.
     */
    @Test
    public void testHashEquals() {
        Assertions.assertAll("hashing equals",
                () -> assertEquals(myGrams[0].hashCode(),myGrams[3].hashCode(),"0 3"),
                () -> assertEquals(myGrams[0].hashCode(),myGrams[6].hashCode(),"0 6"),
                () -> assertEquals(myGrams[1].hashCode(),myGrams[4].hashCode(),"1 4"),
                () -> assertEquals(myGrams[2].hashCode(),myGrams[5].hashCode(),"2 5"),
                () -> assertEquals(myGrams[2].hashCode(),myGrams[8].hashCode(),"2 8")
        );
    }

    /**
     * This test checks if in the initial WordGram set we make contains enough unique WordGrams.
     */
    @Test
    public void testHashDensity() {
        Set<Integer> set = new HashSet<Integer>();
        for(WordGram w : myGrams) {
            set.add(w.hashCode());
        }
        assertTrue(set.size() > 9,"set size <= 9, got: "+set.size());
    }

    /**
     * This test checks if the shiftAdd method creates a new WordGram according
     * to the project specification.
     */
    @Test
    public void testShift() {
        String[] words = {"apple", "zebra", "mongoose", "hat","cat"};
        WordGram a = new WordGram(words,0,4);
        WordGram b = new WordGram(words,1,4);
        String before = a.toString();
        WordGram as = a.shiftAdd("cat");
        String after = a.toString();
        assertTrue(as.equals(b),"shift add wordgrams");
        assertTrue(as.length() == a.length(),"shift add lengths");
        assertTrue(before.equals(after),"shift add immutable");
        assertFalse(a == as, "shift add immutable, shiftAdd wordgram should not be equal to original");
    }
}