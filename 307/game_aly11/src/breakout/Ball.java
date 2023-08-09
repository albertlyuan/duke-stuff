package breakout;


import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

class Ball extends Circle {
    /**
     * Ball class that represents the ball that the player plays with.
     * Controlled by move_ball() in the main class
     * */
    public Ball(int startX, int startY, int size){
        setCenterX(startX);
        setCenterY(startY);
        setRadius(size);
        setFill(Color.BLACK);
        setStroke(Color.BLACK);

    }
    public void move(double newX, double newY){
        /**
         * moves the ball to a new location
         * PARAMS: the new center for the ball
         * RETURNS: none
         * */
        setCenterX(newX);
        setCenterY(newY);
    }

}
