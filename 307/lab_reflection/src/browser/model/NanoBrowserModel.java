package browser.model;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;


/**
 * This represents the heart of the browser: the collections
 * that organize all the URLs into useful structures.
 * 
 * @author Robert C. Duvall
 */
public class NanoBrowserModel {
    // constants
    // location AND prefix for property files
    private static final String DEFAULT_RESOURCE_PACKAGE = NanoBrowserModel.class.getPackageName() + ".resources.Errors";
    // state
    private URL myHome;
    private URL myCurrentURL;
    private int myCurrentIndex;
    private List<URL> myHistory;
    private Map<String, URL> myFavorites;
    // get strings from resource file
    private ResourceBundle myResources;


    /**
     * Creates an empty model.
     */
    public NanoBrowserModel (String language) {
        myHome = null;
        myCurrentURL = null;
        myCurrentIndex = -1;
        myHistory = new ArrayList<>();
        myFavorites = new HashMap<>();
        // use resources for errors
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
    }

    /**
     * Changes current page to given URL, removing next history.
     */
    public URL go (String url) throws BrowserException {
        try {
            URL tmp = completeURL(url);
            // unfortunately, completeURL may not have returned a valid URL, so test it
            tmp.openStream();
            // if successful, remember this URL and replace old history
            myCurrentURL = tmp;
            if (hasNext()) {
                myHistory = myHistory.subList(0, myCurrentIndex + 1);
            }
            myHistory.add(myCurrentURL);
            myCurrentIndex += 1;
            return myCurrentURL;
        }
        catch (Exception e) {
            throw new BrowserException(e, myResources.getString("NoLoad"), url);
        }
    }

    /**
     * Returns the first page in next history, null if next history is empty.
     */
    public URL next () throws BrowserException {
        if (hasNext()) {
            myCurrentIndex += 1;
            return myHistory.get(myCurrentIndex);
        }
        else {
            throw new BrowserException(myResources.getString("NoNext"));
        }
    }

    /**
     * Returns the first page in back history, null if back history is empty.
     */
    public URL back () throws BrowserException {
        if (hasPrevious()) {
            myCurrentIndex -= 1;
            return myHistory.get(myCurrentIndex);
        }
        else {
            throw new BrowserException(myResources.getString("NoPrevious"));
        }
    }

    /**
     * Returns true if there is a next URL available.
     */
    public boolean hasNext () {
        return myCurrentIndex < (myHistory.size() - 1);
    }

    /**
     * Returns true if there is a previous URL available.
     */
    public boolean hasPrevious () {
        return myCurrentIndex > 0;
    }

    /**
     * Returns URL of the current home page or null if none is set.
     */
    public URL getHome () {
        return myHome;
    }

    /**
     * Sets current home page to the current URL being viewed.
     */
    public void setHome () {
        // just in case, might be called before a page is visited
        if (myCurrentURL != null) {
            myHome = myCurrentURL;
        }
    }

    /**
     * Returns true if the home URL has been set.
     */
    public boolean hasHome () {
        return myHome != null;
    }

    /**
     * Adds current URL being viewed to favorites collection with given name.
     */
    public void addFavorite (String name) throws BrowserException {
        // just in case, might be called before a page is visited
        if (myCurrentURL != null && name != null && !name.equals("")) {
            myFavorites.put(name, myCurrentURL);
        }
        else {
            throw new BrowserException(myResources.getString("BadFavorite"), name);
        }
    }

    /**
     * Returns URL from favorites associated with given name, null if none set.
     */
    public URL getFavorite (String name) throws BrowserException {
        if (name != null && !name.equals("") && myFavorites.containsKey(name)) {
            return myFavorites.get(name);
        }
        else {
            throw new BrowserException(myResources.getString("BadFavorite"), name);
        }
    }

    // deal with a potentially incomplete URL
    private URL completeURL (String possible) throws MalformedURLException {
        final String PROTOCOL_PREFIX = "http://";
        try {
            // try it as is
            return new URL(possible);
        }
        catch (MalformedURLException e) {
            try {
                // e.g., let user leave off initial protocol
                return new URL(PROTOCOL_PREFIX + possible);
            }
            catch (MalformedURLException ee) {
                try {
                    // try it as a relative link
                    // FIXME: need to make this more robust :(
                    return new URL(myCurrentURL.toString() + "/" + possible);
                }
                catch (MalformedURLException eee) {
                    // not typical, but make explicit that letting the exception escape is intended
                    throw eee;
                }
            }
        }
    }
}
