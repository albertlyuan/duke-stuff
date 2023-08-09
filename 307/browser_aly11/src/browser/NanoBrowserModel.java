package browser;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class NanoBrowserModel {
    // data
    private URL myCurrentURL;
    private int myCurrentIndex;
    private List<URL> myHistory;
    private URL myHome;
    private HashMap<String,Integer> topVisited;
    private HashMap<String,URL> myFavorites;

    public NanoBrowserModel(){
        myCurrentURL = null;
        myCurrentIndex = -1;
        myHistory = new ArrayList<>();
        myHome = null;
        topVisited = new HashMap<>();
        myFavorites = new HashMap<>();
    }
    public URL loadURL (String url) {
        url = aliasToURL(url);
        try {
            URL tmp = completeURL(url);
            if (tmp != null) {
                // unfortunately, completeURL may not have returned a valid URL, so test it
                tmp.openStream();
                // if successful, remember this URL
                myCurrentURL = tmp;
                if (hasNext()) {
                    myHistory = myHistory.subList(0, myCurrentIndex + 1);
                }
                updateHistory();
                myCurrentIndex += 1;
                updateVisitCount();
                return myCurrentURL;
            }
        }
        catch (Exception e) {
            showError(String.format("Could not load %s", url));
        }
        return null;
    }

    private void updateHistory() {
        if(myHistory.size() == 0){
            myHistory.add(myCurrentURL);
        }else if (!myHistory.get(myHistory.size()-1).equals(myCurrentURL)) {
            myHistory.add(myCurrentURL);
        }
    }

    private String aliasToURL(String url) {
        if (myFavorites.keySet().contains(url)){
            return myFavorites.get(url).toString();
        }
        return url;
    }

    private void updateVisitCount() {
        if(!topVisited.containsKey(myCurrentURL.toString())){
            topVisited.put(myCurrentURL.toString(),0);
        }
        topVisited.put(myCurrentURL.toString(), topVisited.get(myCurrentURL.toString())+1);
    }

    public URL getHistoryURL(int change){
        myCurrentIndex += change;
        if (myCurrentIndex < 0){
            myCurrentIndex = 0;
        }
        URL ret = myHistory.get(myCurrentIndex);
        topVisited.put(ret.toString(), topVisited.get(ret.toString())+1);
        return ret;
    }
    // Returns true if there is a next URL available
    boolean hasNext () {
        return myCurrentIndex < (myHistory.size() - 1);
    }

    // Deal with a potentially incomplete URL
    private URL completeURL (String possible) {
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
                    // FIXME: need to generalize this :(
                    return new URL(myCurrentURL.toString() + "/" + possible);
                }
                catch (MalformedURLException eee) {
                    // FIXME: not a good way to handle an error!
                    e.printStackTrace();
                    return null;
                }
            }
        }
    }
    // Display given message as an error in the GUI
    public void showError (String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Browser Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setHome() {
        myHome = myCurrentURL;
    }

    public void setFav(String alias) {
        myFavorites.put(alias,myCurrentURL);
    }

    public ObservableList<String> getTopSites() {
        /**
         * calculates the most viewed sites and returns a list in descending order
         */
        ObservableList<String> topTen = FXCollections.observableArrayList();
        for (String url : topVisited.keySet()){
            topTen.add(url);
        }
        topTen.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if(topVisited.get(o1) < topVisited.get(o2)){
                    return 1;
                }else if(topVisited.get(o1) > topVisited.get(o2)){
                    return -1;
                }
                return 0;
            }
        });

        return topTen;
    }

    public String getHome() {
        if (myHome == null){
            return "";
        }
        return myHome.toString();
    }
}
