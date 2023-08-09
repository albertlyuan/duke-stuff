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
		super(TITLE,maze);
	}

	/**
	 * @see SearchAlgorithm#step()
	 */

	@Override
	protected void markNext(Spot next) {
		next.markAsPath();
	}

	@Override
	protected boolean startCheck(){
		return false;
	}
	@Override
	protected boolean endCheck() {
		return isSearchOver();
	}

	@Override
	protected void updateCurrent(Spot next) {
		myCurrent.markAsVisited();
		myCurrent = next;
	}

	@Override
	protected Spot getNext(List<Spot> neighbors) {
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
			return Randomness.getRandomElement(empties);
		}
		// guaranteed to be at least one possible, even if it is last spot visited
		return Randomness.getRandomElement(possibles);

	}

}
