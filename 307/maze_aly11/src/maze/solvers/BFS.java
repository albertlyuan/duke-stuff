package maze.solvers;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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

	// data structure used to keep search frontier -- use a queue
	private Queue<Spot> myFrontier;



	public BFS (Maze maze) {
		super(TITLE, maze);
		myFrontier = new LinkedList<>();
		myFrontier.add(myCurrent);
		myPaths = new HashMap<>();
	}

	/**
	 * @see SearchAlgorithm#step()
	 */

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
		if (myFrontier.size() > maxSize){
			maxSize = myFrontier.size();
		}
		myCurrent = myFrontier.peek();
	}

	@Override
	protected boolean emptyFrontier() {
		return myFrontier.isEmpty();
	}

}
