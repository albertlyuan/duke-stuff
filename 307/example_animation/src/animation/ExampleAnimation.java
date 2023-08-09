package animation;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * A basic example JavaFX program for the first lab.
 * 
 * @author Robert C. Duvall
 */
public class ExampleAnimation extends Application {
    public static final String TITLE = "Example JavaFX";
    public static final int SIZE = 400;
    public static final int FRAMES_PER_SECOND = 60;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final Paint BACKGROUND = Color.AZURE;
    public static final Paint HIGHLIGHT = Color.OLIVEDRAB;
    public static final String BOUNCER_IMAGE = "duke-seal-logo.png";
    public static final int OFFSET_AMOUNT = 80;
    public static final int MOVER_SIZE = 100;
    public static final int MOVER_SPEED = 5;
    public static final int GROWER_SIZE = 50;
    public static final Paint GROWER_COLOR = Color.BISQUE;
    public static final double GROWER_RATE = 1.1;
    public static final int ROTATION_RATE = 90;

    // some things needed to remember during game
    private Scene myScene;
    private ImageView myMover;
    private Rectangle myGrower;


    /**
     * Initialize what will be displayed and how it will be updated.
     */
    @Override
    public void start (Stage stage) {
        // attach scene to the stage and display it
        myScene = setupGame(SIZE, SIZE, BACKGROUND);
        stage.setScene(myScene);
        stage.setTitle(TITLE);
        stage.show();
        // attach "game loop" to timeline to play it (basically just calling step() method repeatedly forever)
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step(SECOND_DELAY)));
        animation.play();
    }

    // Create the game's "scene": what shapes will be in the game and their starting properties
    private Scene setupGame (int width, int height, Paint background) {
        // make some shapes and set their properties
        myMover = new ImageView(new Image(BOUNCER_IMAGE));
        myMover.setFitWidth(MOVER_SIZE);
        myMover.setFitHeight(MOVER_SIZE);
        // x and y represent the top left corner, so center it in window
        myMover.setX(width / 2 - myMover.getFitWidth() / 2);
        myMover.setY(height / 2 - OFFSET_AMOUNT);
        myGrower = new Rectangle(width / 2 - GROWER_SIZE / 2, height / 2 + OFFSET_AMOUNT, GROWER_SIZE, GROWER_SIZE);
        myGrower.setFill(GROWER_COLOR);
        // create one top level collection to organize the things in the scene
        // order added to the group is the order in which they are drawn
        Group root = new Group(myMover, myGrower);
        // could also add them dynamically later
        //root.getChildren().add(myMover);
        //root.getChildren().add(myGrower);
        // create a place to see the shapes
        Scene scene = new Scene(root, width, height, background);
        // respond to input
        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        scene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
        return scene;
    }

    // Change properties of shapes in small ways to animate them over time
    // Note, there are more sophisticated ways to animate shapes, but these simple ways work fine to start
    private void step (double elapsedTime) {
        // update "actors" attributes
        myMover.setRotate(myMover.getRotate() - ROTATION_RATE * elapsedTime);
        myGrower.setRotate(myGrower.getRotate() + ROTATION_RATE / 2 * elapsedTime);

        // check for collisions and choose response
        if (isIntersecting(myMover, myGrower)) {
            myGrower.setFill(HIGHLIGHT);
        }
        else {
            myGrower.setFill(GROWER_COLOR);
        }
    }

    // What to do each time a key is pressed
    private void handleKeyInput (KeyCode code) {
        // NOTE Java 14 syntax that some prefer (but watch out for the many special cases!)
        //   https://blog.jetbrains.com/idea/2019/02/java-12-and-intellij-idea/
        switch (code) {
            case RIGHT -> myMover.setX(myMover.getX() + MOVER_SPEED);
            case LEFT -> myMover.setX(myMover.getX() - MOVER_SPEED);
            case UP -> myMover.setY(myMover.getY() - MOVER_SPEED);
            case DOWN -> myMover.setY(myMover.getY() + MOVER_SPEED);
        }
        // TYPICAL way to do it, definitely more readable for longer actions
//        if (code == KeyCode.RIGHT) {
//            myMover.setX(myMover.getX() + MOVER_SPEED);
//        }
//        else if (code == KeyCode.LEFT) {
//            myMover.setX(myMover.getX() - MOVER_SPEED);
//        }
//        else if (code == KeyCode.UP) {
//            myMover.setY(myMover.getY() - MOVER_SPEED);
//        }
//        else if (code == KeyCode.DOWN) {
//            myMover.setY(myMover.getY() + MOVER_SPEED);
//        }
    }

    // What to do each time a key is pressed
    private void handleMouseInput (double x, double y) {
        if (myGrower.contains(x, y)) {
            myGrower.setScaleX(myGrower.getScaleX() * GROWER_RATE);
            myGrower.setScaleY(myGrower.getScaleY() * GROWER_RATE);
        }
    }

    private boolean isIntersecting (ImageView a, Rectangle b) {
        // with images can only check bounding box (as it is calculated in container with other objects)
        return b.getBoundsInParent().intersects(a.getBoundsInParent());
        // with shapes, can check precisely (in this case, it is easy because the image is circular)
//        Shape moverBounds = new Circle(a.getX() + a.getFitWidth() / 2,
//                                       a.getY() + a.getFitHeight() / 2,
//                                       a.getFitWidth() / 2 - MOVER_SIZE / 20);
//        return ! Shape.intersect(moverBounds, b).getBoundsInLocal().isEmpty();
    }
}
