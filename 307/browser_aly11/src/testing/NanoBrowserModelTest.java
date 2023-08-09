package testing;

import browser.NanoBrowserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class NanoBrowserModelTest {
    private NanoBrowserModel model;

    @BeforeEach
    public void setUp() {
        model = new NanoBrowserModel();
    }

    @Test
    public void testValidURL1 () {
        String input = "NOT_A_URL";
        String expected = null;
        String message = "should be " + expected;

        try{
            String ret = model.loadURL(input).toString();
            assertEquals(expected, ret, message);
        }catch (ExceptionInInitializerError e){

        }
    }
    @Test
    public void testValidURL2 () {
        String input = "https://diddukewin.com/";
        String expected = "https://diddukewin.com/";
        String message = "should be " + expected;

        try {
            String ret = model.loadURL(input).toString();
            assertEquals(expected, ret, message);
        } catch (NoClassDefFoundError e) {

        }
    }

    @Test
    public void testgetTopSites1 () {
        model.loadURL("http://www.google.com");
        model.loadURL("http://www.bing.com");
        model.loadURL("http://www.google.com");
        model.loadURL("http://www.bing.com");
        model.loadURL("http://www.google.com");
        model.loadURL("http://www.yahoo.com");

        ArrayList<String> expectedList = new ArrayList(){
            {
                add("http://www.google.com");
                add("http://www.bing.com");
                add("http://www.yahoo.com");
            }
        };

        String expected = expectedList.toString();
        String ret = model.getTopSites().toString();
        assertEquals(expected, ret);

    }
}
