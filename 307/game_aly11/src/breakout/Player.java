package breakout;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class Player extends Rectangle {
    /**
     * player class that represents the bar that the player controls.
     * Controlled by left right arrow keys
     * */
    public Player(int xpos, int ypos, int width, int thickness){
        setX(xpos);
        setY(ypos);
        setWidth(width);
        setHeight(thickness);
        setFill(Color.WHITE);
        setStroke(Color.BLACK);
    }

}
