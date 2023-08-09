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
	// data structure used to keep search frontier -- use a stack
	private Stack<Spot> myFrontier;



	public DFS (Maze maze) {
		super(TITLE, maze);
		myFrontier = new Stack<>();
		myFrontier.push(myCurrent);
	}

	/**
	 * @see SearchAlgorithm#step()
	 */

	@Override
	protected boolean startCheck(){
		if (myFrontier.size() > maxSize){
			maxSize = myFrontier.size();
		}
		return false;
	}

	@Override
	protected void markNext(Spot next) {
		if (next != null) {
			next.markAsPath();
			myFrontier.push(next);
		}
		else {
			myCurrent.markAsVisited();
			myFrontier.pop();
		}
	}

	@Override
	protected void updateCurrent(Spot next) {
		myCurrent = myFrontier.peek();
	}

	@Override
	protected boolean endCheck() {
		return isSearchOver();
	}

	@Override
	protected boolean emptyFrontier() {
		return myFrontier.isEmpty();
	}

}
