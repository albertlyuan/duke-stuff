package maze.solvers;


import maze.model.Maze;
import maze.model.Spot;

import java.util.List;
import java.util.Map;

/**
 * This class represents the abstraction of a maze search algorithm.
 *
 * @author Albert Yuan
 */
public class SearchAlgorithm {
	// name for this search algorithm
	private final String myDescription;

	protected Maze myMaze;
	// current spot being explored
	protected Spot myCurrent;
	// trail of all spots can be used to recreate chosen path
	protected Map<Spot, Spot> myPaths;

	//counter variables
	protected int maxSize=0;
	protected int countBacktrack=0;

	/**
	 * Create an algorithm with its name.
	 */
	public SearchAlgorithm (String description, Maze maze) {
		myDescription = description;
		myMaze = maze;
		myCurrent = maze.getStart();
		myCurrent.markAsPath();
	}

	/**
	 * Take one step searching for solution path for the maze.
	 * @return true if goal has been found or no more paths possible
	 */
	public boolean step () {
		if (startCheck()){
			return true;
		}
		// find possible next steps
		List<Spot> neighbors = getNeighbors();
		// choose next spot to explore
		Spot next = getNext(neighbors);
		// mark next step, if it exists
		markNext(next);
		// update current spot
		updateCurrent(next);
		return endCheck();
	}

	protected boolean startCheck(){
		/**
		 * For Magic, Greedy, and BFS.
		 * Overwritten in DFS, and Random
		 *
		 * Checks if maze solution is found
		 */
		if (isSearchOver()) {
			markPath();
			return true;
		}
		return false;
	}

	protected List<Spot> getNeighbors() {
		return myMaze.getNeighbors(myCurrent);
	}

	protected Spot getNext(List<Spot> neighbors) {
		/**
		 * selects the next spot to test
		 * used by BFS, DFS, Greedy
		 * Overwritten in Magic and Random
		 */
		for (Spot spot : neighbors) {
			if (spot.getState() == Spot.EMPTY) {
				return spot;
			}
		}
		countBacktrack += 1;
		return null;
	}

	protected void markNext(Spot next) {
		//do nothing
	}

	protected void updateCurrent(Spot next) {
		//do nothing
	}

	protected boolean endCheck() {
		return false;
	}


	// When the search is over, color the chosen correct path using trail of successful spots
	protected void markPath () {
		/**
		 * Marks the correct path to take in the maze
		 */
		Spot step = myMaze.getGoal();
		while (step != null) {
			step.markAsPath();
			step = myPaths.get(step);
			if (myPaths.get(myPaths.get(step)) == step){
				break;
			}
		}
	}

	// Search is successful if current spot is the goal.
	// Search is unsuccessful if there are no more frontier spots to consider

	protected boolean isSearchOver () {
		/**
		 * determines if goal has been reached or if goal is impossible to reach
		 */
		return emptyFrontier() || (myCurrent != null && myCurrent.equals(myMaze.getGoal()));
	}
	protected boolean emptyFrontier() {
		return false;
	}

	//methods for MazeDisplay
	public int getMaxSize(){
		return maxSize;
	}
	public int getCountBacktrack(){
		return countBacktrack;
	}
	/**
	 * @see Object#toString()
	 */
	@Override
	public String toString () {
		return myDescription;
	}
}
