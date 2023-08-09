package bowling;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


/**
 * Tests for Game class.
 *
 * @author Robert C Duvall
 */
class GameTest {
    @Test
    void testGetScore() {
        Game g = new Game("1 9,9 0,5 5,10 0,10 0,3 4,10 0,5 5,0 10,1 9 10");
        assertEquals(156,g.getScore());
    }
    @Test
    void testGetScoreUpToForOpens () {
        //Game g = new Game("1 9,9 0,5 5,10 0,10 0,3 4,10 0,5 5,0 10,1 9,10");
        Game g = new Game("1 9,9 0,5 5,10,10,3 4,10 0,5 5,0 10,1 9 10");
        assertEquals(19, g.getScoreUpTo(1));
        assertEquals(28, g.getScoreUpTo(2));
        assertEquals(48, g.getScoreUpTo(3));
        assertEquals(71, g.getScoreUpTo(4));
        assertEquals(88, g.getScoreUpTo(5));
        assertEquals(95, g.getScoreUpTo(6));
        assertEquals(115, g.getScoreUpTo(7));
        assertEquals(125, g.getScoreUpTo(8));
        assertEquals(136, g.getScoreUpTo(9));
        assertEquals(156, g.getScoreUpTo(10));

        Game g2 = new Game("9 1,2 2,2 2,2 2,2 2,2 2,2 2,2 2,2 2,0 8 0");
        assertEquals(52, g2.getScoreUpTo(10));

    }


}
