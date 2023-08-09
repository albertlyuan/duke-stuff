package maze.solvers;

import java.util.Collection;
import java.util.List;
import java.util.Stack;
import maze.model.Maze;
import maze.model.Spot;


/*
 * This is the super class for the search algorithms
 * It includes a generic template for searching 
 */
public abstract class SearchAlgorithm {
	// maze being solved
	private final Maze myMaze;
	// data structure used to keep frontier spots
	private final Collection<Spot> myFrontier;
	// current spot being explored
	private Spot myCurrent;
	// name for this search algorithm
	private final String myDescription;


	public SearchAlgorithm (Maze maze, String description, Collection<Spot> structure) {
		myDescription = description;
		myMaze = maze;
		myCurrent = myMaze.getStart();
		myFrontier = structure;
		myFrontier.add(myCurrent);
		myCurrent.markAsPath();
	}

	/*
	 * Overall template method for a search.  Hopefully
	 * subclasses only need to override pieces of the algorithm
	 * and can keep this overall structure.
	 */
	public boolean step () {
		// stop searching after goal is reached or determined impossible
		if (isSearchOver()) {
			cleanUp();
			return true;
		}
		// TODO: Complete the rest of this method by making calls to existing or abstract methods implemented by subclasses
		List<Spot> neighbors = getNeighbors();
		Spot nextSpot =  getNextSpot(neighbors);
		markNextAndUpdateCurrent(nextSpot);

		return false;
	}

	protected abstract void markNextAndUpdateCurrent(Spot next);

	protected abstract Spot getNextSpot(List<Spot> neighbors);

	protected List<Spot> getNeighbors() {
		List<Spot> neighbors = getMaze().getNeighbors(getCurrent());
		return neighbors;
	}
	
	@Override
	public String toString () {
		return myDescription;
	}

	/*
	 * What to do when the search is over
	 */
	protected void cleanUp () {
		// do nothing by default, some algorithms will override
	}


	protected Maze getMaze () {
		return myMaze;
	}

	protected Collection<Spot> getSearchFrontier () {
		return myFrontier;
	}

	protected Spot getCurrent () {
		return myCurrent;
	}

	protected void setCurrent (Spot current) {
		myCurrent = current;
	}

	// Search is over and unsuccessful if there are no more fringe points to consider.
	// Search is over and successful if the current point is the same as the goal.
	protected boolean isSearchOver () {
		return myFrontier != null && myFrontier.isEmpty() ||
			   myCurrent != null && myCurrent.equals(myMaze.getGoal());
	}
}
