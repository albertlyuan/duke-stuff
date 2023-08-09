package breakout;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

public class Main extends Application {
    /**
     * This is the main class that is run.
     * It creates the scene, moves the ball and player,
     * and keeps track of the squares being hit.
     * Running this will create a window with the Breakout game
     * */
    //general variables
    public static final String TITLE = "Breakout by Albert";
    public static final int APP_SIZE = 400;

    //square variables
    public static final int SQUARE_SIZE = 70;
    public static final Paint[] colors = {Color.AQUAMARINE, Color.CORAL,Color.DODGERBLUE,Color.GOLD,Color.LAVENDER};
    public static final int rows = 2;
    public static final int cols = 5;

    //player variables
    public static final int PLAYER_SPEED = 10;
    public static final int PLAYER_WIDTH = 80;
    public static final int PLAYER_THICKNESS = 20;
    public static int PLAYER_POSX = 200;
    public static int PLAYER_POSY = 370;

    //ball variables
    public static final int BALL_SIZE = 10;
    public static final int BALL_START_Y = 250;
    public static final int BALL_START_X = 200;
    public static int BALL_VELX = -25;
    public static int BALL_VELY = -10;

    // gameplay variables
    private Scene myScene;
    private Player player;
    private Ball ball;
    private ArrayList<Block> squares = new ArrayList();
    private int lives = 3;
    private Text livesLeft = new Text(300, 30, String.format("Lives Left: %d", lives));
    private Timeline animation;
    private Stage myStage;


    public void start (Stage stage) {
        /**
         * creates the stage and displays it.
         * Also calls move_ball
         * PARAMS: stage which will be used to display the scene
         * RETURN: void
         * */
        // attach scene to the stage and display it
        myStage = stage;
        myScene = setup(APP_SIZE, APP_SIZE);
        myStage.setScene(myScene);
        myStage.setTitle(TITLE);
        myStage.show();

        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(new KeyFrame(Duration.seconds(0.1), e -> moveBall()));
        animation.play();
    }
    private Group generateSquares(Group root){
        /** generates squares based on the set size, and amount.
         * each column of squares will have a different color
         * PARAMS: root that the squares will be added to
         * Returns: root with squares
         * */
        int yoffset = 10;
        for (int i=0; i<rows; i++){
            int xoffset = 10;
            for (int j=0; j<cols; j++){
                int x = SQUARE_SIZE * j + xoffset;
                int y = 50 + SQUARE_SIZE * i + yoffset;
                Block b = new Block(x,y, SQUARE_SIZE,colors[j]);
                root.getChildren().add(b);
                squares.add(b);
                xoffset += 10;
            }
            yoffset += 10;
        }
        return root;
    }
    private Scene setup (int width, int height) {
        /**
         * creates the scene that will be used in start()
         * generates squares, creates a player and ball, and
         * handles keypresses
         *
         * PARAMS: width and height of the scene
         * Returns: Scene
         * */
        Group root = new Group();
        StackPane header = new StackPane();

        root = generateSquares(root);
        player = new Player(PLAYER_POSX,PLAYER_POSY, PLAYER_WIDTH, PLAYER_THICKNESS);
        ball = new Ball(BALL_START_X,BALL_START_Y,BALL_SIZE);

        root.getChildren().add(player);
        root.getChildren().add(ball);

        Rectangle headerRectangle = new Rectangle(0,0,APP_SIZE,60);
        headerRectangle.setFill(Color.LAVENDER);
        header.getChildren().add(headerRectangle);
        header.getChildren().add(livesLeft);
        root.getChildren().add(header);

        Scene scene = new Scene(root, width, height);

        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));

        return scene;
    }

    private void moveBall () {
        /**
         * moves the ball based on its x and y velocity
         * also checks for collisions with the wall,player, or squares in order to
         * 'bounce' the ball
         * also checks for the game to end
         *
         * PARAMS: none
         * RETURN: void
         * */
        if (squares.size() == 0 || lives == 0){
            animation.stop();
            if (squares.size() == 0){
                winScreen();
            }else{
                loseScreen();
            }

        }

        if (floorHit()){
            lives -= 1;
            livesLeft.setText(String.format("Lives Left: %d", lives));
            ball.setCenterX(BALL_START_X);
            ball.setCenterY(BALL_START_Y);
            BALL_VELX = 25;
            BALL_VELY = 10;
        }else {
            System.out.println(ball.getCenterX());
            double wallhit = wallHit(ball.getCenterX()+BALL_VELX,ball.getRadius());
            double xgap=0;
            double ygap=0;
            if (wallhit != 0) {
                xgap = BALL_VELX + wallhit;
                BALL_VELX = bounce(BALL_VELX);
            }
            double ceilinghit = ceilingHit(ball.getCenterY() + BALL_VELY + ball.getRadius());
            if (ceilinghit != 0) {
                System.out.print("old" + ball.getCenterX() + ball.getCenterY());
                ygap = BALL_VELY+ceilinghit;
                System.out.println("new" + ball.getCenterX() + ball.getCenterY());
                BALL_VELY = bounce(BALL_VELY);
            }
            if (playerHit()) {
                BALL_VELY = bounce(BALL_VELY);
            }
            String squareFace = squareHit();
            if (squareFace != "") {
                if (squareFace == "X") {
                    BALL_VELY = bounce(BALL_VELY);
                } else {
                    BALL_VELX = bounce(BALL_VELX);

                }
            }
            if (xgap != 0 || ygap != 0) {
                ball.move(ball.getCenterX() + xgap, ball.getCenterY() + ygap);
            }else{
                ball.move(ball.getCenterX() + BALL_VELX, ball.getCenterY() + BALL_VELY);
            }
        }
    }

    private void winScreen() {
        StackPane root = new StackPane();
        Text youWin = new Text("You Win");
        youWin.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        youWin.setTextAlignment(TextAlignment.CENTER);
        root.getChildren().add(youWin);
        Scene win = new Scene(root, APP_SIZE, APP_SIZE,Color.GREEN);
        myStage.setScene(win);
        myStage.setTitle("YOU WIN");
        myStage.show();

    }
    private void loseScreen(){
        StackPane root = new StackPane();
        Text youLose = new Text("You Lose");
        youLose.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        youLose.setTextAlignment(TextAlignment.CENTER);
        root.getChildren().add(youLose);
        Scene lose = new Scene(root, APP_SIZE, APP_SIZE,Color.RED);
        myStage.setScene(lose);
        myStage.setTitle("YOU LOSE");
        myStage.show();
    }

    public int bounce(int vel){
        /**
         * calculates the resulting velocity after bouncing
         * PARAMS: velocity of the ball
         * RETURNS: velocity of the ball after bouncing off an object
         * */
        return -vel;
    }
    public boolean floorHit(){
        /**
         * detects if the ball touches the floor
         * PARAMS: none
         * RETURNS: true if the floor was touched
         * */
        if (ball.getCenterY()+ball.getRadius() >= APP_SIZE){
            return true;
        }
        return false;
    }
    public double wallHit(double x, double radius){
        /**
         * detects if the ball touches the wall
         * PARAMS: position of the ball
         * RETURNS: true if the wall was touched
         * */
        if (x - radius < 0) {
            return -(x - radius);
        }
        if (x + radius > 400) {
            return -((x + radius) - 400);
        }
        return 0;
    }
    public double ceilingHit(double y){
        /**
         * detects if the ball touches the wall
         * PARAMS: position of the ball
         * RETURNS: true if the wall was touched
         * */
        if (y < 60) {
            return 60 - y;
        }
        return 0;
    }
    public boolean playerHit(){
        /**
         * detects if the ball touches the player
         * PARAMS: none
         * RETURNS: true if the player was touched
         * */
        double playerMinX = player.getX();
        double playerMaxX = player.getX() + player.getWidth();
        double playerY = player.getY();
        double ballY = ball.getCenterY() + ball.getRadius();
        double ballX = ball.getCenterX();
        if (ballX >= playerMinX && ballX <= playerMaxX && playerY - ballY < 5){
            return true;
        }
        return false;
    }
    public String squareHit(){
        /**
         * detects if the ball touches any square
         * PARAMS: none
         * RETURNS: the side of the square that was touched. if a top/bottom was touched,
         * Y is returned because Y velocity will remain unchanged
         * */
        return "";
        /*
        for (Block square : squares){
            double ballX = ball.getCenterX()+ball.getRadius();
            double ballY = ball.getCenterY()+ball.getRadius();

            if (ballX >= square.leftBound && ballX <= square.rightBound && ballY >= square.upBound && ballY <= square.downBound){
                square.setFill(Color.TRANSPARENT);
                square.setStroke(Color.TRANSPARENT);
                squares.remove(square);
                if (ballX == square.leftBound || ballX == square.rightBound){
                    return "X";
                }
                else{
                    return "Y";
                }
            }
        }
        return "";

         */
    }

    private void handleKeyInput (KeyCode code) {
        /**
         * moves the player based on the key pressed
         * PARAMS: keyboard key that was inputted
         * RETURNS: none
         * */
        switch (code) {
            case RIGHT:
                if (APP_SIZE - (player.getX() + player.getWidth() ) >= PLAYER_SPEED) {
                    player.setX(player.getX() + PLAYER_SPEED);
                }
                break;
            case LEFT:
                if (player.getX() >= PLAYER_SPEED){
                    player.setX(player.getX() - PLAYER_SPEED);
                }
                break;
        }

    }

}



