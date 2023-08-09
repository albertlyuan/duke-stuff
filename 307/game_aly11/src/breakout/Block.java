package breakout;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

class Block extends Rectangle {
    /**
     * Block class that represents the squares that the player is trying to get rid of.
     * Controlled squareHit() in main which makes the square invisible and removes it from the squares arraylist
     * */
    public int leftBound;
    public int rightBound;
    public int upBound;
    public int downBound;
    public Block(int x, int y, int size, Paint color){
        setX(x);
        setY(y);
        setWidth(size);
        setHeight(size);
        setFill(color);
        setStroke(Color.BLACK);
        leftBound = x;
        rightBound = x + size;
        upBound = y;
        downBound = y + size;
    }
}
