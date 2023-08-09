package bowling;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


/**
 * Tests for Frame class.
 *
 * @author Robert C Duvall
 */
class FrameTest {
    @Test
    void testGetNumRolls () {
        Frame spare = new Frame("0 10");
        Frame spareTest = new Frame("10");
        Frame strike = new Frame("10 0");
        Frame spare2 = new Frame("5 5");
        Frame spare2Duplicate = new Frame("5 5");
        Frame morethanTen = new Frame("11 5");
        Frame morethanTen2 = new Frame("5 6");
        Frame not10 = new Frame("3 5");

        assertEquals(2, spare.getNumRolls());
        assertEquals(2, morethanTen.getNumRolls());
        assertEquals(true,strike.isStrike ());
        assertEquals(false,spare.isStrike ());
        assertEquals(false,morethanTen.isStrike ());
        assertEquals(true,spare.isSpare ());
        assertEquals(false,strike.isSpare());  //thinks that a strike is also a spare
        assertEquals(true,spare2.isSpare ());
        assertEquals(false,morethanTen2.isSpare ());
        //assertEquals(false,morethanTen2.getRollScore (2));
        assertEquals(5,spare2.getRollScore (0));
        assertEquals(10,spare.getRollScore (1));
        assertEquals(8,not10.getTotalScore());
        assertEquals(true,spare2Duplicate.equals(spare2));
        assertEquals(false,spare.equals(strike));
        assertEquals(false,spareTest.isSpare());
    }
}
