package maze.solvers;

import java.util.List;
import java.util.Stack;
import maze.model.Maze;
import maze.model.Spot;


/**
 * This class represents a Depth-First maze search algorithm.
 *
 * @author YOUR NAME HERE
 */
public class DFS extends SearchAlgorithm {
	public static final String TITLE = "Depth-First";

	private Maze myMaze;
	// data structure used to keep search frontier -- use a stack
	private Stack<Spot> myFrontier;
	// current spot being explored
	private Spot myCurrent;


	public DFS (Maze maze) {
		super(TITLE,maze);
		myMaze = maze;
		myFrontier = new Stack<>();
		myCurrent = maze.getStart();
		myCurrent.markAsPath();
		myFrontier.push(myCurrent);
	}

	/**
	 * @see SearchAlgorithm#step()
	 */
	@Override
	public boolean step () {
		List<Spot> neighbors = findtPossibleSpots();
		Spot next = getNextSpot(neighbors);
		markStep(next);
		updatePath();
		return isSearchOver();
	}

	private List<Spot> findtPossibleSpots() {
		List<Spot> neighbors = myMaze.getNeighbors(myCurrent);
		return neighbors;
	}

	private Spot getNextSpot(List<Spot> neighbors) {
		Spot next = null;
		for (Spot spot : neighbors) {
			if (spot.getState() == Spot.EMPTY) {
				next = spot;
				break;
			}
		}
		return next;
	}

	public void updatePath() {
		// update current spot
		myCurrent = myFrontier.peek();
	}

	private void markStep(Spot next) {
		// mark next step, if it exists
		if (next != null) {
			next.markAsPath();
			myFrontier.push(next);
		}
		else {
			myCurrent.markAsVisited();
			myFrontier.pop();
		}
	}

	// Search is successful if current spot is the goal.
	// Search is unsuccessful if there are no more frontier spots to consider
	private boolean isSearchOver () {
		return myFrontier.isEmpty() || (myCurrent != null && myCurrent.equals(myMaze.getGoal()));
	}
}
