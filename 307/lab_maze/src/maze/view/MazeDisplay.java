package maze.view;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import maze.model.Maze;
import maze.solvers.BFS;
import maze.solvers.DFS;
import maze.solvers.Greedy;
import maze.solvers.Magic;
import maze.solvers.RandomWalk;
import maze.solvers.SearchAlgorithm;


/**
 * This class displays a maze that can be solved using multiple algorithms.
 *
 * @author Robert C. Duvall
 * @author Shannon Pollard
 */
public class MazeDisplay {
	// Display constants
	public final static String NO_SOLVER_TITLE = "No Solver Selected";
	public final int TITLE_OFFSET = 40;
	public final int BORDER_OFFSET = 20;
	// speed of animation
	public final double SECONDS_DELAY = 0.01;
	// size of each maze space in pixels
	public final int BLOCK_SIZE = 12;
	// color of each of the states
	public final List<Color> STATE_COLORS = List.of(
			Color.DARKRED,		    // wall color
			Color.BLUE,	            // path color
			Color.WHITE,			// empty cell color
			Color.LIGHTBLUE	        // visited cell color
	);

	// Display settings
	private Text myTitle;
	// rectangles that represent the maze's state graphically in colors
	private List<List<Rectangle>> myDisplayMaze;
	// animate searching for maze path
	private Timeline myAnimation;
	private boolean isPaused;
	// Maze settings
	private final Maze myMaze;
	private SearchAlgorithm mySolver;


	/**
	 * Create display based on given maze.
	 */
	public MazeDisplay (Maze maze) {
		myMaze = maze;
	}

	/**
	 * Create simulation's "scene": what shapes will be in the display the maze and their starting properties
	 */
	public Scene setupDisplay (Paint background) {
		Group root = new Group();
		myDisplayMaze = setupMaze(root);
		myTitle = setTitle(root, NO_SOLVER_TITLE);

		Scene scene = new Scene(root,
				          myMaze.getNumColumns()*BLOCK_SIZE+2*BORDER_OFFSET,
				         myMaze.getNumRows()*BLOCK_SIZE+BORDER_OFFSET+TITLE_OFFSET,
				                background);
		scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
		return scene;
	}


	// Set up maze display, one rectangle for each of the maze's space that is colored based on its state
	private List<List<Rectangle>> setupMaze (Group parent) {
		List<List<Rectangle>> displayMaze = new ArrayList<>();
		for (int r = 0; r < myMaze.getNumRows(); r+=1) {
			displayMaze.add(new ArrayList<>());
			for (int c = 0; c < myMaze.getNumColumns(); c+=1) {
				Rectangle rect = new Rectangle(c*BLOCK_SIZE+BORDER_OFFSET, r*BLOCK_SIZE+TITLE_OFFSET, BLOCK_SIZE, BLOCK_SIZE);
				rect.setFill(STATE_COLORS.get(myMaze.getSpot(r, c).getState()));
				parent.getChildren().add(rect);
				displayMaze.get(r).add(rect);
			}
		}
		return displayMaze;
	}

	// Set up title for the display
	private Text setTitle (Group root, String text) {
		Text title = new Text(160, 25, text);
		title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		root.getChildren().add(title);
		return title;
	}

	// Take action based on user's input
	private void handleKeyInput (KeyCode code) {
		// NEW Java 14 syntax that some prefer (but watch out for the many special cases!)
		//   https://blog.jetbrains.com/idea/2019/02/java-12-and-intellij-idea/
		switch (code) {
			case N -> newMaze();
			case S -> step();
			case SPACE -> togglePause();
			case D -> setSearch(new DFS(myMaze));
			case B -> setSearch(new BFS(myMaze));
			case G -> setSearch(new Greedy(myMaze));
			case R -> setSearch(new RandomWalk(myMaze));
			case M -> setSearch(new Magic(myMaze));
		}
	}

	// Take one step with current search algorithm
	private void step () {
		if (mySolver != null) {
			if (mySolver.step()) {
				myAnimation.stop();
			}
			redraw();
		}
	}

	// Start or stop searching animation as appropriate
	private void togglePause() {
		if (isPaused) {
			myAnimation.play();
		}
		else {
			myAnimation.pause();
		}
		isPaused = ! isPaused;
	}

	// Make new random maze to solve
	private void newMaze() {
		myMaze.resetMaze();
		myMaze.createMaze();
		myTitle.setText(NO_SOLVER_TITLE);
		mySolver = null;
		redraw();
	}

	// Start given search algorithm for this maze
	private void setSearch (SearchAlgorithm solver) {
		mySolver = solver;
		myTitle.setText(mySolver.toString());
		myMaze.resetMaze();
		redraw();
		startAnimation();
	}

	// Start new animation to show search algorithm's steps
	private void startAnimation () {
		if (myAnimation != null) {
			myAnimation.stop();
		}
		myAnimation = new Timeline();
		myAnimation.setCycleCount(Timeline.INDEFINITE);
		myAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(SECONDS_DELAY), e -> step()));
		myAnimation.play();
		isPaused = false;
	}

	// Resets rectangle colors according to their current state in the maze, making it match the model maze
	private void redraw () {
		for (int r = 0; r < myMaze.getNumRows(); r+=1) {
			for (int c = 0; c < myMaze.getNumColumns(); c+=1) {
				myDisplayMaze.get(r).get(c).setFill(STATE_COLORS.get(myMaze.getSpot(r, c).getState()));
			}
		}
	}
}
