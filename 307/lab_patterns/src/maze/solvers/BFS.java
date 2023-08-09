package maze.solvers;

import java.util.Collection;
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

	// trail of all spots can be used to recreate chosen path
	private Map<Spot, Spot> myPaths;


	public BFS (Maze maze) {
		// For BFS, data structure is a queue
		this(maze, TITLE, new LinkedList<>());
	}

	protected BFS (Maze maze, String description, Collection<Spot> data) {
		super(maze, description, data);
		myPaths = new HashMap<>();
	}


	// TODO: refactor into smaller methods called by superclass version of step() then REMOVE this implementation!

	// Add next spot to possible paths
	protected void addToPath (Spot next) {
		myPaths.put(next, getCurrent());
	}

	// When the search is over, color the chosen correct path using trail of successful spots
	protected void cleanUp () {
		Spot step = getMaze().getGoal();
		while (step != null) {
			step.markAsPath();
			step = myPaths.get(step);
		}
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

	@Override
	protected void markNextAndUpdateCurrent(Spot next) {
		Queue<Spot> frontier = (Queue<Spot>)getSearchFrontier();
		// mark next step, if it exists
		if (next != null) {
			next.markAsPath();
			frontier.add(next);
			addToPath(next);
		}
		else {
			getCurrent().markAsVisited();
			frontier.remove();
		}
		// update current spot
		setCurrent(frontier.peek());
	}
}
