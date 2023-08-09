package maze.solvers;


import maze.model.Maze;
import maze.model.Spot;

import java.util.LinkedList;
import java.util.Stack;

/**
 * This class represents the abstraction of a maze search algorithm.
 *
 * @author YOUR NAME HERE
 */
public class SearchAlgorithm {
	// name for this search algorithm
	private final String myDescription;
	private Maze myMaze;
	// current spot being explored
	private Spot myCurrent;


	/**
	 * Create an algorithm with its name.
	 */
	public SearchAlgorithm (String description, Maze maze) {
		myDescription = description;
		myMaze = maze;
		myCurrent = maze.getStart();
		myCurrent.markAsPath();
	}

	/**
	 * Take one step searching for solution path for the maze.
	 * @return true if goal has been found or no more paths possible
	 */
	public boolean step () {
		return false;
	}

	/**
	 * @see Object#toString()
	 */
	@Override
	public String toString () {
		return myDescription;
	}
}