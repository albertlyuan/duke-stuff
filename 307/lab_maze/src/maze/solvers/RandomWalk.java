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

	private Maze myMaze;
	// search frontier - current spot being explored
	private Spot myCurrent;


	public RandomWalk (Maze maze) {
		super(TITLE,maze);
		myMaze = maze;
		myCurrent = maze.getStart();
		myCurrent.markAsPath();
	}

	/**
	 * @see SearchAlgorithm#step()
	 */
	@Override
	public boolean step () {
		List<Spot> neighbors = findPossibleSpots();
		Spot next = null;
		next = getNextSpot(neighbors);
		markStep(next);
		updatePath(next);
		return isSearchOver();
	}

	private void updatePath(Spot next) {
		// update current spot
		myCurrent.markAsVisited();
		myCurrent = next;
	}

	public void markStep(Spot next) {
		next.markAsPath();
	}

	private Spot getNextSpot(List<Spot> neighbors) {
		Spot next;
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
		// prefer exploring empty paths over visited ones
		if (! empties.isEmpty() && Randomness.isRandomEnough(EXPLORE_BIAS)) {
			next = Randomness.getRandomElement(empties);
		}
		// guaranteed to be at least one possible, even if it is last spot visited
		next = Randomness.getRandomElement(possibles);
		return next;
	}

	private List<Spot> findPossibleSpots() {
		// find possible next steps
		List<Spot> neighbors = myMaze.getNeighbors(myCurrent);
		return neighbors;
	}


	// Search is over and unsuccessful if there are no more fringe points to consider.
	// Search is over and successful if the current point is the same as the goal.
	private boolean isSearchOver () {
		return myCurrent != null && myCurrent.equals(myMaze.getGoal());
	}
}
