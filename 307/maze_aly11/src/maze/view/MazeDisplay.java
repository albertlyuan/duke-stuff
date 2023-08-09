package maze.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import maze.model.Maze;
import maze.solvers.BFS;
import maze.solvers.DFS;
import maze.solvers.Greedy;
import maze.solvers.Magic;
import maze.solvers.RandomWalk;
import maze.solvers.SearchAlgorithm;

import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.WHITE;


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
	// size of each maze space in pixels
	public final int BLOCK_SIZE = 12;
	// color of each of the states
	public final List<Color> STATE_COLORS = List.of(
			Color.DARKRED,		    // wall color
			Color.BLUE,	            // path color
			Color.WHITE,			// empty cell color
			Color.LIGHTBLUE	        // visited cell color
	);

	// Algorithm Panel Display
	private HBox AlgorithmPanel;
	private Label myTitle;
	private Label stepCounter;

	//Control Panel Display
	private Button PauseButton;
	private Slider SpeedSlider;
	private Label leastSteps;
	private Label smallestSize;
	private Label fewestBacktracks;

	//Display Variables
	private int stepCount =0;
	public double animationDelay = 0.01;
	private HashMap<SearchAlgorithm, ArrayList<Integer>> MapStats;
	private boolean first = true;
	private SearchAlgorithm minSteps;
	private SearchAlgorithm minSize;
	private SearchAlgorithm minBacktrack;

	// Maze settings
	private final Maze myMaze;
	private SearchAlgorithm mySolver;
	// rectangles that represent the maze's state graphically in colors
	private List<List<Rectangle>> myDisplayMaze;
	// animate searching for maze path
	private Timeline myAnimation;
	private boolean isPaused;



	/**
	 * Create display based on given maze.
	 */
	public MazeDisplay(Maze maze) {
		myMaze = maze;
	}

	/**
	 * Create simulation's "scene": what shapes will be in the display the maze and their starting properties
	 */
	public Scene setupDisplay (Paint background) {
		VBox root = new VBox();
		root.setSpacing(20);
		root.setPadding(new Insets(10, 10, 10, 10));

		AlgorithmPanel = makeAlgorithmPanel();
		root.getChildren().addAll(AlgorithmPanel, makeMazeDisplay(), makeControlPanel());

		Scene scene = new Scene(root, background);
		scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
		return scene;
	}

	// Organize maze squares with title
	private Node makeMazeDisplay () {
		/**
		 * creates the display for everything between and including
		 * the maze and the title label
		 */
		VBox panel = new VBox();
		panel.setAlignment(Pos.CENTER);
		panel.setSpacing(10);

		Group group = new Group();
		myDisplayMaze = setupMaze(group);
		myTitle = makeTitle(NO_SOLVER_TITLE);
		stepCounter = makeCounter(stepCount);
		panel.getChildren().addAll(myTitle, stepCounter, group);
		return panel;
	}

	// Organize buttons to choose which search algorithm to run
	private HBox makeAlgorithmPanel () {
		/**
		 * creates buttons that are used to select the
		 * different algorithms
		 */
		HBox panel = new HBox();
		panel.setSpacing(5);

		Button dfs = new Button("Depth-First Search (d)");
		dfs.setOnAction(value -> setSearch(new DFS(myMaze)));
		Button bfs = new Button("Breadth-First Search (b)");
		bfs.setOnAction(value -> setSearch(new BFS(myMaze)));
		Button greedy = new Button("Greedy Search (g)");
		greedy.setOnAction(value -> setSearch(new Greedy(myMaze)));
		Button random = new Button("Random Search (r)");
		random.setOnAction(value -> setSearch(new RandomWalk(myMaze)));
		Button magic = new Button("Magic Search (m)");
		magic.setOnAction(value -> setSearch(new Magic(myMaze)));
		panel.getChildren().addAll(dfs,bfs,greedy,magic,random);

		return panel;
	}

	// Organize UI elements to control how the maze and search animation perform
	private Node makeControlPanel () {
		/**
		 * creates all display items below the maze such as
		 * new maze button, pause button, animationSpeed slider, stat panel
		 */
		HBox panel = new HBox();
		panel.setSpacing(10);

		Button newMazeButton = createNewMazeButton();
		createPauseButton();
		createSpeedSlider();
		VBox StatPanel = createStatPanel();

		panel.getChildren().addAll(newMazeButton, PauseButton, SpeedSlider,StatPanel);
		return panel;
	}

	private void createPauseButton() {
		PauseButton = new Button("||");
		PauseButton.setOnAction(value -> togglePause());
	}

	private Button createNewMazeButton() {
		Button newMazeButton = new Button("New Maze (m)");
		newMazeButton.setOnAction(value ->  newMaze());
		return newMazeButton;
	}

	private VBox createStatPanel(){
		/**
		 * creates panel containing labels that track which algorithm uses the
		 * fewest steps, least memory, and fewest backtracks
		 */
		MapStats = new HashMap<>();
		VBox panel = new VBox();
		panel.setSpacing(1);

		leastSteps = new Label();
		smallestSize = new Label();
		fewestBacktracks = new Label();
		for (Label label : new Label[]{leastSteps, smallestSize, fewestBacktracks}){
			label.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		}

		resetStatLabels();
		panel.getChildren().addAll(leastSteps,smallestSize,fewestBacktracks);
		return panel;
	}

	private void resetStatLabels() {
		smallestSize.setText("Smallest Size: N/A");
		leastSteps.setText("Least Steps: N/A");
		fewestBacktracks.setText("Fewest Backtracks: N/A");
	}

	private void createSpeedSlider() {
		SpeedSlider = new Slider(0.01, 0.5, animationDelay);
		SpeedSlider.setMajorTickUnit(0.1);
		SpeedSlider.setMinorTickCount(5);
		SpeedSlider.setSnapToTicks(true);
		SpeedSlider.setShowTickMarks(true);
		SpeedSlider.setShowTickLabels(true);
	}

	// Set up title for the maze
	private Label makeTitle (String text) {
		Label label = new Label(text);
		label.setFont(Font.font("Arial", FontWeight.BOLD, 15));
		return label;
	}

	//set up counter for maze
	private Label makeCounter(int start){
		Label counter = new Label("Steps: "+start);
		counter.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		return counter;
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

	/**
	 * main simulation function. called during every animation tick.
	 * calls the specified search algorithm. if algorithm runs to completion,
	 * calls foundCorrectPath
	 */
	// Take one step with current search algorithm
	private void step () {
		if (animationDelay != SpeedSlider.getValue()){
			changeAnimationSpeed();
		}

		if (mySolver != null) {
			stepCount += 1;
			int[] currStats = {stepCount, mySolver.getMaxSize(),mySolver.getCountBacktrack()};
			stepCounter.setText("Steps: "+ stepCount);
			myTitle.setText(mySolver.toString() + " | Max Size: " + currStats[1] + " | Backtracks: " + currStats[2]);
			if (mySolver.step()) {
				foundCorrectPath(currStats);
			}
			redraw();
		}
	}

	private void foundCorrectPath(int[] currStats) {
		/**
		 * handles all end of maze functions.
		 * Sets the algorithm background to green, changes labels,
		 * and compares results of the algorithm
		 */
		AlgorithmPanel.setBackground(new Background(new BackgroundFill(GREEN, CornerRadii.EMPTY,
				Insets.EMPTY)));

		myTitle.setText(mySolver.toString() + ": Found Solution | Max Size: " + currStats[1] + " | Backtracks: " + currStats[2]);
		updateStats(currStats);

		for (SearchAlgorithm alg : MapStats.keySet()){
			if (first){
				initializeLeaderboard(alg);
			}
			updateLeaderboard(alg);
		}
		myAnimation.stop();
	}

	private void initializeLeaderboard(SearchAlgorithm alg) {
		minSteps = alg;
		minSize = alg;
		minBacktrack = alg;
		first = false;
	}

	private void updateLeaderboard(SearchAlgorithm alg) {
		/**
		 * compares the current algorithm's stats with the leaderboard.
		 * If the current algorithm is better, it is crowned the new winner
		 */
		if (MapStats.get(alg).get(0) <= MapStats.get(minSteps).get(0)){
			minSteps = alg;
			leastSteps.setText("Least Steps: " + minSteps.toString() + " (" + MapStats.get(minSteps).get(0) + " steps)");
		}
		if (MapStats.get(alg).get(1) <= MapStats.get(minSize).get(1)){
			minSize = alg;
			smallestSize.setText("Smallest Size: " + minSize.toString() + " (" + MapStats.get(minSize).get(1) + " items)");
		}
		if (MapStats.get(alg).get(2) <= MapStats.get(minBacktrack).get(2)){
			minBacktrack = alg;
			fewestBacktracks.setText("Fewest Backtracks: " + minBacktrack.toString() + " (" + MapStats.get(minBacktrack).get(2) + " backtracks)");
		}
	}

	private void updateStats(int[] currStats) {
		/**
		 * updates the statbook containing the stats for each algorithm run
		 * for the current maze
		 */
		if (!MapStats.keySet().contains(mySolver)){
			MapStats.put(mySolver, new ArrayList<>());
			for (int i=0; i<3; i++ ){
				MapStats.get(mySolver).add(currStats[i]);
			}
		}else{
			for (int i=0; i<3; i++ ){
				if (currStats[i] < MapStats.get(mySolver).get(i)){
					MapStats.get(mySolver).set(i, currStats[i]);
				}
			}
		}
	}

	private void changeAnimationSpeed() {
		animationDelay = SpeedSlider.getValue();
		startAnimation();
	}

	// Start or stop searching animation as appropriate
	private void togglePause() {
		if (myAnimation == null){
			return;
		}
		if (isPaused) {
			PauseButton.setText("||");
			myAnimation.play();
		}
		else {
			PauseButton.setText("|>");
			myAnimation.pause();
		}
		isPaused = ! isPaused;
	}

	// Make new random maze to solve
	private void newMaze() {
		/**
		 * resets everything.
		 * clears maze statistics and leaderboards,
		 * and creates a new maze
		 */
		MapStats.clear();
		resetStatLabels();
		first = true;

		myMaze.resetMaze();
		myMaze.createMaze();
		myTitle.setText(NO_SOLVER_TITLE);
		mySolver = null;
		redraw();
	}

	// Start given search algorithm for this maze
	private void setSearch (SearchAlgorithm solver) {
		/**
		 * runs the specified algorithm and resets counters to record stats
		 */
		stepCount = 0;
		AlgorithmPanel.setBackground(new Background(new BackgroundFill(WHITE, CornerRadii.EMPTY,
				Insets.EMPTY)));
		mySolver = solver;
		myTitle.setText(mySolver.toString() + " | Max Size: " + 0 + " | Backtracks: " + 0);
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
		myAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(animationDelay), e -> step()));
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
