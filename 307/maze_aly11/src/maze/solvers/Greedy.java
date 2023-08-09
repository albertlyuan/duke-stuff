package maze.solvers;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import maze.model.Maze;
import maze.model.Spot;


/**
 * This class represents a Greedy maze search algorithm.
 *
 * @author YOUR NAME HERE
 */
public class Greedy extends SearchAlgorithm {
	public static final String TITLE = "Greedy";
	// data structure used to keep search frontier -- use a priority queue
	private PriorityQueue<Spot> myFrontier;

	public Greedy (Maze maze) {
		super(TITLE,maze);
		myFrontier = new PriorityQueue<>();
		myFrontier.add(myCurrent);
		myPaths = new HashMap<>();
	}


	/**
	 * @see SearchAlgorithm#step()
	 */

	@Override
	protected List<Spot> getNeighbors() {
		if (myFrontier.size() > maxSize){
			maxSize = myFrontier.size();
		}
		List neighbors = myMaze.getNeighbors(myCurrent);
		Collections.sort(neighbors);
		return neighbors;
	}

	@Override
	protected void markNext(Spot next) {
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

	@Override
	protected void updateCurrent(Spot next) {
		myCurrent = myFrontier.peek();
	}

	@Override
	protected boolean emptyFrontier() {
		return myFrontier.isEmpty();
	}

}
