package maze.solvers;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import maze.model.Maze;
import maze.model.Spot;


/**
 * This class represents a Breadth-First maze search algorithm.
 *
 * @author YOUR NAME HERE
 */
public class BFS extends SearchAlgorithm {
	public static final String TITLE = "Breadth-First";

	private Maze myMaze;
	// data structure used to keep search frontier -- use a queue
	private Queue<Spot> myFrontier;
	// current spot being explored
	private Spot myCurrent;
	// trail of all spots can be used to recreate chosen path
	private Map<Spot, Spot> myPaths;


	public BFS (Maze maze) {
		super(TITLE,maze);
		myMaze = maze;
		myFrontier = new LinkedList<>();
		myCurrent = maze.getStart();
		myCurrent.markAsPath();
		myFrontier.add(myCurrent);
		myPaths = new HashMap<>();
	}

	/**
	 * @see SearchAlgorithm#step()
	 */
	@Override
	public boolean step () {
		if(isSearchOver()) return true;
		List<Spot> neighbors = findPossibleSpots();
		Spot next = getNextSpot(neighbors);
		markStep(next);
		updatePath();
		return false;
	}

	private void updatePath() {
		myCurrent = myFrontier.peek();
	}

	private void markStep(Spot next) {
		if (next != null) {
			next.markAsPath();
			myFrontier.add(next);
			myPaths.put(next, myCurrent);
		}
		else {
			myCurrent.markAsVisited();
			myFrontier.remove();
		}
	}

	private Spot getNextSpot(List<Spot> neighbors) {
		// choose next spot to explore
		Spot next = null;
		for (Spot spot : neighbors) {
			if (spot.getState() == Spot.EMPTY) {
				next = spot;
				break;
			}
		}
		return next;
	}

	private List<Spot> findPossibleSpots() {
		List<Spot> neighbors = myMaze.getNeighbors(myCurrent);
		return neighbors;
	}


	// Search is successful if current spot is the goal.
	// Search is unsuccessful if there are no more frontier spots to consider
	private boolean isSearchOver () {
		markPath();
		return myFrontier.isEmpty() || (myCurrent != null && myCurrent.equals(myMaze.getGoal()));
	}

	// When the search is over, color the chosen correct path using trail of successful spots
	private void markPath () {
		Spot step = myMaze.getGoal();
		while (step != null) {
			step.markAsPath();
			step = myPaths.get(step);
		}
	}
}
