package maze.solvers;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import maze.model.Maze;
import maze.model.Spot;
import maze.util.Randomness;


/**
 * This class represents a Magic maze search algorithm.
 *
 * @author YOUR NAME HERE
 */
public class Magic extends SearchAlgorithm {
	public static final String TITLE = "Magic";
	// data structure used to keep search frontier -- use a priority queue
	private PriorityQueue<Spot> myFrontier;



	public Magic (Maze maze) {
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
	protected Spot getNext(List<Spot> neighbors) {
		return Randomness.getRandomElement(neighbors);
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
