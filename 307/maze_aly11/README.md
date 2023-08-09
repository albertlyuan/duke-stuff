maze
====

This project implements a maze solver.

Name: Albert Yuan

### Timeline

Start Date: 9/12/21
 
Finish Date: 9/13/21

Hours Spent: 8


### Tutorial and other Resources Used


### Resource Attributions


### Running the Program

Main class: Main.java

Data files needed: n/a

Key/Mouse inputs: Mouse or Keyboard input to select new maze,
pause, and type of search algorithm.

Known Bugs: n/a


### Notes/Assumptions


### Impressions
There were two things that were hard about this project. First, it was hard figuring out
how to generalize the step function in each of the search algorithms. All the algorithms were very
similar so it made it very evident that it could be deduplicated. However they all had little differences that
made the generalization in the super class difficult. For example, Greedy might have 1 line in an IF statement that
DFS didn't have. To remedy this, I defined the step function into 6 general steps: startCheck, getNeighbors, getNextSpot
markNextSpot, updateCurrentSpot, and endCheck. When one search algorithm didn't have a certain step, the method was 
overwritten to do nothing. Also the fact that each of the search algorithms had different 'myFrontier' types made it so
that each time myFrontier was called, the method was overwritten to account for the fact that I generalize it in the 
super class.

The second hardest thing was figuring out the Magic/Greedy bug where it looped infinitely when drawing the final path.
I was able to figure out that the problem was that there was a spot that was getting pointed to another spot, which 
pointed back to the first spot. After waiting until I had finished refactoring the code (the project page said that 
refactoring might fix the bug), I just added a condition that said if 2 spots pointed at each other, break the while 
loop. 
