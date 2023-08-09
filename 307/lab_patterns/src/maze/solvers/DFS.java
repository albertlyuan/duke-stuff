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

	public DFS (Maze maze) {
		super(maze, TITLE, new Stack<>());
	}

	// TODO: refactor into smaller methods called by superclass version of step() then REMOVE this implementation!

	@Override
	protected void markNextAndUpdateCurrent(Spot next) {
		Stack<Spot> frontier = (Stack<Spot>)getSearchFrontier();
		// mark next step, if it exists
		if (next != null) {
			next.markAsPath();
			frontier.push(next);
		}
		else {
			getCurrent().markAsVisited();
			frontier.pop();
		}
		// update current spot
		setCurrent(frontier.peek());
	}

	@Override
	protected Spot getNextSpot(List<Spot> neighbors) {
		Spot next = null;
		for (Spot spot : neighbors) {
			if (spot.getState() == Spot.EMPTY) {
				next = spot;
				break;
			}
		}
		return next;
	}

}
