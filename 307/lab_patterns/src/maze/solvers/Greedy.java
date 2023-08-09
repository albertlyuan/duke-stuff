package maze.solvers;

import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import maze.model.Maze;
import maze.model.Spot;


/**
 * This class represents a Greedy maze search algorithm.
 *
 * @author YOUR NAME HERE
 */
public class Greedy extends BFS {
	public static final String TITLE = "Greedy";

	public Greedy (Maze maze) {
		this(maze, TITLE);
	}

	protected Greedy (Maze maze, String description) {
		super(maze, description, new PriorityQueue<>(15));
	}


	// TODO: refactor into smaller methods called by superclass version of step() then REMOVE this implementation!

	@Override
	protected Spot getNextSpot(List<Spot> neighbors) {
		// sort in order of closest to goal
		Collections.sort(neighbors);
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
}
