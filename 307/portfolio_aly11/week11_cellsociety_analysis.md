# Journal : Cell Society Project Analysis
## Albert Yuan
## 8
## 11/3/21


Design Review
=======

### Adding new Automata
On the backend, all that is required is a new cell and a new model class. To create the new type 
of cell, we would inherit the superclass Cell and set the appropriate names,colors,and numbers to each state.
Then to build a new model, we would just inherit the superclass Model and create a corresponding Rule
subclass. In the Rule, the majority of the algorithm would be written. If any searches of a certain
cell are required beforehand, this can be done in the constructor.

On the frontend, a new settings class must be created to allow the user to dynamically change 
simulation settings. 

Configuration wise, a new sim file must be created with the following lines:
`Parameters=#,#,...,#
`Type=[simulationType]`

If a new edgepolicy or neighborfinder type is necessary, it can easily be created in EdgePolicies or 
Neighborfinders.

### Overall design
Our program has a view, grid, and model that is all connected by a controller class.
The grid is an abstraction for a grid of Cell objects. Overall, this class held the actual grid 
that was being displayed and calculated by view and model respectively.
The view has multiple subclasses that are defined by their position on the UI. For example we have 
top,bottom,left,right,and center view packages. Overall, this class displayed the grid and gave the user
options to interact with the program by loading files, changing settings, and play/pausing the animation.
The model has only 3 subclasses, the rules (which holds the algorithm for determining the cells' next 
states), edgepolicies(which determines how the algorithm handles edge cells' neighbors), and NeighborFinder
(which determines which cells are considered as neighbors). Overall, this class calculated the next 
states of the grid.
The controller connects these three main classes together and sends information from one another.

### Design Principles
#### Liskov Substitution

* We utilized this very well. Our design had the same view and controller, but different model
depending on the simulation type. Because of this, we often maintained the Liskov Substitution Principle
by having calls to Model type objects that accepted all varieties such as GameOfLife or PredatorPrey. 
We also used this when we made our grid. Each simulation type had its own cell class. If we didn't do this,
each call to the model from controller or cell from grid would've needed reflection or if/else statements
to point to the correct model/cell type. Instead, by ensuring that each model/cell superclass could 
be replaced by its subclass, we can just make one call to Model/Cell.

#### Dependency Inversion

* We didn't do this well. I think we could've implemented it in the controller by making interfaces 
for the model and view and grid. Then instead of defining the types as Grid, MainView, and Model, we
could've defined the types as GridInterface, ViewInterface, and Model interface. This way, when 
we make changes to any of these classes, the public methods remain constant. 

#### Interface Segregation

* This was something that we didn't do. For the most part all of our models used the same public methods 
but there was one method, changeSettings(ArrayList newProb), that wasn't used by GameOfLife because 
that simulation didn't have any game settings to change (such as probCatch or Shark reproduction rate).
We could've used this to make the GameOfLife model not have this unused method by making a 
changeSettingInterface. This would require there to be no superclass since it would change the 
amount of public methods so we could get rid of the superclass, put all the inherited methods in 
a helper class to minimize duplicate code, and then use the changeSettingInterface to define 
GameOfLife model. 

### Code Design Examples

#### Made easier to implement by the code's design

* All the models were really easy to add as were all the different edge policies and neighbor finders

* All of our models followed the Liskov Substitution Principle, so they were able to be called in the 
same way as existing models. All models are called upon by updateModel(), which runs iterateGrid(). 
iterateGrid() runs currRule() which holds the code for each simulation's algorithm for determining
the cell's next state. Thus, the only work that I needed to do to create a new model was to create 
the code for the new rules. Everything else stayed the same. Same story goes for the edge policies 
and neighbor finders. After the changes were published, I refactored all the edge policies and 
neighbor finders into separate classes. Now getNearby() calls these classes so now in order to create 
a new edge policy or neighbor finder, all that is needed is to write the new algorithm and create a 
new EdgePolicies or NeighborFinder class. Edge policies and neighbor finders also follow the Liskov 
Substitution Principle so they can be instantly substituted in for other edge policies and neighbor
finders.


#### Good Example **Dane Erickson** implemented

* The Grid, which was able to be populated with any type of cell

* Dane used reflection to populate the grid with the correct type of cell based on the inputted file
cell. As the person who worked on the model, which often made calls to the grid, I had a really 
easy time making the different models since his design was flexible. The cells differed by having 
different number of states and different cell parameters (ie. PredatorPrey had sharkEnergy and 
sharkReproduction). To access parameters, I just had to use `grid.getCell(r,c).getCellStateParameter()`
to get the cell parameter and setCellStateParameter to write a new cell parameter. Although I didn't 
check the cell type in model, I easily could've because he had a method to access the number and value
of each cell state.


#### Needs Improvement Example **Dane Erickson** implemented

* The Grid, which assigned colors to cell states with a switch statement

* To assign colors to cell state numbers in each cell, he used the following method for when there 
were 3 cases and a separate but similar method for 2 cases: 
```java
private void switchThreeCases(int state, String name0, String color0, String name1, String color1,
      String name2, String color2) {
    switch (state) {
      case EMPTY_STATE -> {
        setStateColor(color0);
        setStateName(name0);
      }
      case 1 -> {
        setStateColor(color1);
        setStateName(name1);
      }
      case 2 -> {
        setStateColor(color2);
        setStateName(name2);
      }
    }
}
```
I think this is something that could've been replaced by a for loop and a couple of hashmaps.
We could've used a hashmap<Integer,ArrayList<String>> that holds the state as the key, and the state
color and the state name. Then we could iterate through the map.keyset():
```java
for (Integer i : map.keyset()){
  String stateColor = map.get(i).get(0);
  String stateName = map.get(i).get(1);
  setStateColor(stateColor);
  setStateName(stateName);  
}
```
This elimintates for the two switch[x]cases and the need for switch statements.

## Your Design
One thing that I wrestled with was separating the currRule method into its own class. 
For each type of simulation, we created a [simulation]Rule. This was a subclass of Rule which had two
methods: `determineState()` and `getState()`. determineState was basically the entire currRule method, and
getState got the state of the cell given a row and column. 
### Design Challenge

* Trade-offs
  * On one hand, we have another class when this could've easily been a bunch of private methods in 
  [simulation]Model. On the other hand, we make the [simulation]Model class a lot more readable (
  especially for PredatorPrey) since there are a lot less methods. It also encapsulates the complexity
  of the algorithm

* Alternate designs
  * The alternate, as stated before, was to keep all the algorithm methods in the model. This includes
    the algorithm itself and all its helper methods. For simulations like GameOfLife, there isn't
    much code, but for PredatorPrey there's about 100 lines.

* Solution
  * We ultimately decided to keep the algorithm methods and helper methods in the Rule's class.

* Justification or Suggestion
  * I think it makes our code cleaner (the model code is quite dense), and makes it so that if changes to the rule algorithms are necessary,
    we don't have to touch the model class. This also upholds the principle of encapsulation. Also, 
    it makes it a lot easier to test. Since currRule is a private method, we would have to use reflection 
    whereas these rule classes use public methods. 


### Code Design Examples

#### Good Example **you** implemented

* Design
  * I used lambdas and inheritance to iterate the through the grid in multiple ways with only one 
  for loop method:
```java
protected void iterateGrid(Function<Integer, Consumer<Integer>> gridIterationAction) {
    int currRow = 0;
    int currCol = 0;
    while (true) {
      try {
        currGrid.getModelCell(currRow, currCol).getCellProperty(stateNumber);
        gridIterationAction.apply(currRow).accept(currCol);
        currCol += 1;
      } catch (IndexOutOfBoundsException | KeyNotFoundException xOutOfBounds) {
        try {
          currCol = 0;
          currRow += 1;
          currGrid.getModelCell(currRow, currCol).getCellProperty(stateNumber);
        } catch (IndexOutOfBoundsException | KeyNotFoundException yOutOfBounds) {
          break;
        }
      }
    }
  }
```
This iterator used exceptions to find when to go to the next row (so this would theoretically work with
diamond shaped grids) and took a lambda function to determine what to do with each cell. For example,
the most common application of this was in `updateModel()` which is called at every step of the animation:
```java
iterateGrid(row -> col -> {
      String currState = null;
      try {
        currState = currGrid.getModelCell(row, col).getCellProperty(stateNumber);
      } catch (KeyNotFoundException e) {
        //TODO: handle exception
        System.out.println("Invalid Property");
      }
      int stateAsInt = parseInt(currState);
      updateCell(row, col, stateAsInt);
    });
```
Here we pass in a lambda that gets the current row and column, finds the current cell state, and then 
runs the simulation algorithm on it to determine its new state. Other uses include in Segregation, I 
pass in a lambda that only accounts for empty cells, to find all empty cells. In PredatorPrey, I iterate
for sharks and then again for fish but this time checking to see if a shark 'ate' the fish. 
* Evaluation
  * I thought this bit of code showcases my ability to use lambdas to reduce duplicate code. If I 
  didn't use lambdas, I would've had to create an entirely new loop method (which would have been the same 
  as this one except for which function it calls) multiple times. I think I also showcase some clever
  design by using exceptions to go to the next row instead of assuming that the number of columns 
  per row is constant.


#### Needs Improvement Example **you** implemented

* Design
  * Every Model has the same 4 methods: `setEdgePolicy()`, `getEdgePolicyType()`, `setNeighborFinder()`.
    and `getNeighborFinderType()`. These methods are used to change the Edge Policy Type from toroidal to
    finite and the Neighbor Finder from TriangleComplete to SquareComplete for example. This means that
    there are 5 copies of these 4 methods in every model, which is duplicate code.

* Evaluation
  * These 4 methods need to be abstract methods in base Model. The problem I was having was that when I called
  setEdgePolicy() the edge policy didn't change because it could access the private edgePolicies object
  in the subclass. This is a symptom of me not knowing how to avoid protected instance variables. With
  protected instance variables, this wouldn't have been a problem. However, when I changed it to use private 
  instance variables, I used getter methods to copy them from the superclass *when* the subclass was instantiated.
  What I should've done was to just use getEdgePolicy() whenever I needed the edgePolicy,(which was during
  the call to the Rule class) instead of just creating a copy of it at the start. 



## Conclusions

#### What part(s) of the code did you spend the most time on?
I spent the most time refactoring the neighborfinder class, and building the
rules classes for each model. Building the rules classes was not that difficult except for the PredatorPrey
one. Minus the constructor and the 4 methods I mentioned above, this is pretty much all I had to do 
for most of the models plus build the actual Rule object:
```java
protected Integer currRule(int currRow, int currCol, int state, List<Integer> nearby) {
    int newState = myRule.determineState(currRow, currCol, state, nearby, currGrid, edgePolicy);
    if (myRule.relocationStatus()) {
      relocate(state);
      emptySpots.add(currRow * numCols + currCol);
    }
    return newState;
  }

  /**
   * takes a random empty cell from emptyspots and sets its state to state
   */
  private void relocate(int state) {
    int idx = random.nextInt(emptySpots.size());
    int r = emptySpots.get(idx) / numCols;
    int c = emptySpots.get(idx) % numCols;
    emptySpots.remove(idx);
    addNewUpdates(r, c, state);
  }
```
I had a hard time figuring out how to keep track of all the different information like the
shark state, shark reproduction, shark energy, and shark energy gain. If you look at the code for it,
it's probably a little difficult to read and that's also something that I could've worked to improve on.
It was also a little hard because 'moving' the shark means coping the sharks current information, 
then pasting it in the destination cell. Then if reproduction happened, the current cell would have
to be edited with new shark information.

Neighborfinder refactoring took a long time because initially I found the n neighbors' states. This 
worked for GameOfLife because the cell's don't 'move' to a destination. PredatorPrey on the other hand
'moves' cells, so I had to change it so that the neighborinder returned coordinates, which would then 
be used to find states. This sounds simple, but it took a long time because this was changed after a 
all the models and tests were using the old neighborfinder. The rationale for this change is that 1) 
it made it easier to implement neighborfinders for non-square neighbors (ie triangles) and 2) it eliminated
the need for switch/case statements. Before I was relying on the order of the neighbors to determine 
which state was which neighbor. For example, the first element in the SquareComplete neighbor list was the
top left. 
```java
protected List<Integer> getSquareComplete(int row, int col) {
    
    ArrayList<Integer> neighbors = new ArrayList<>();
    addSquareNeighbors(row, col, x, y, neighbors);

    return neighbors;
  }
private void addSquareNeighbors(int row, int col, ArrayList<Integer> neighbors){
    int[] x = {-1, 0, 1, -1, 1, -1, 0, 1};
    int[] y = {-1, -1, -1, 0, 0, 1, 1, 1};
    int idx=0;
    while(idx<x.length){
        neighbors.add(grid.getCell(row+y[idx], col+x[idx]).getCellStateParameter("StateNumber");
        idx++;
    }
    //neighbors = [top left, top middle, top right, left middle, right middle, etc ]
}
```
In retrospect this was a terrible way of doing it, but at the time I wanted to be able to 
have just one element in the list per neighboring cell. Instead, by putting coordinates, there are two 
elements per neighboring cell in the list.
```java
private void addSquareNeighbors(int row, int col, ArrayList<Integer> neighbors){
    int[] x = {-1, 0, 1, -1, 1, -1, 0, 1};
    int[] y = {-1, -1, -1, 0, 0, 1, 1, 1};
    int idx=0;
    while(idx<x.length){
        neighbors.add(row+y[idx]);
        neighbors.add(col+x[idx]);
        idx++;
    }
}
```


#### How has your perspective on and practice of coding changed?
This project definitely helped me figure out how to use lambdas effectively to minimize
duplicate code in loops/if/switch statements. I think it also has opened my eyes to using interfaces,
which is still something that I have a hard time figuring out the use of. However, I think I am getting the
gist of it as it is basically an assurance that a certain method(s) will be there. I think I've also 
come to figure out how to avoid protected instance variables.

#### What specific steps can you take to become a better designer?
There are many things that I've learned from this project. For one, to avoid protected instance variables,
just leave them private, and use getters whenever needed. Second, use interfaces and make classes depend
on interfaces. This essentially means that instead of depending on a class, which has multiple methods, 
we are making our programs depend on methods. Lastly I've learned to start leaning away from inheritance
and shift towards using interfaces. This is something that my TA, Ha, mentioned to me but I didn't 
really understand at the time. Instead of having a superclass that basically restricts
what public methods a subclass can have, we can move all the inherited code to a helper class and then 
call that as we use interfaces to define what public methods our class will have.