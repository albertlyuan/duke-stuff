package maze.solvers;

import java.util.ArrayList;
import java.util.List;
import maze.model.Maze;
import maze.model.Spot;
import maze.util.Randomness;


/**
 * This class represents a random maze search algorithm.
 *
 * @author YOUR NAME HERE
 */
public class RandomWalk extends SearchAlgorithm {
	public static final String TITLE = "Random Walk";
	public final double EXPLORE_BIAS = 0.999;


	public RandomWalk (Maze maze) {
		// For Random, data structure is a list (of 1 item really)
		super(maze, TITLE, new ArrayList<>());
	}

	// TODO: refactor into smaller methods called by superclass version of step() then REMOVE this implementation!

	@Override
	protected void markNextAndUpdateCurrent(Spot next) {
		next.markAsPath();
		// update current spot
		getCurrent().markAsVisited();
		setCurrent(next);
	}

	@Override
	protected Spot getNextSpot(List<Spot> neighbors){
		List<Spot> empties = new ArrayList<>();
		List<Spot> possibles = new ArrayList<>();
		for (Spot spot : neighbors) {
			if (spot.getState() == Spot.EMPTY) {
				empties.add(spot);
			}
			if (spot.getState() != Spot.WALL) {
				possibles.add(spot);
			}
		}
		// guaranteed to be at least one possible, even if it is last spot visited
		Spot next = Randomness.getRandomElement(possibles);
		// prefer exploring empty paths over visited ones
		if (! empties.isEmpty() && Randomness.isRandomEnough(EXPLORE_BIAS)) {
			next = Randomness.getRandomElement(empties);
		}
		return next;
	}
}
