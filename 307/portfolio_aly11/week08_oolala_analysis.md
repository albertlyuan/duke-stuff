# Journal : OOLALA Project Analysis
## Albert
## 5
## 10/14/21


Design Review
=======

### Adding new Turtle Graphics
To add a new turtle app, we would create a new class that is a child class of our current model and 
a child class of our current view. The command functions would be added as private
methods that are named as the command's input string. Depending on how the app is started up,
the view setup/step methods may or may not be modified. 

For new types data/code files, we would need to change how the data files update information in the model,
which is something that the current code doesn't do well. This is because certain behaviors might 
need to update the view in ways that aren't just moving a single turtle (ie telling multiple turtles
to do a certain command)

Overall, adding a new turtle app would be a little difficult. Building a new model and view
would be fine but the issue that our current set up has is that it relies heavily on specific 
getter/setter methods to update the view with new information from the model. Because of this, 
a considerable amount of time would have to be used to call getters/setters between the model and view.

### Overall design
Our code works by having a view that displays the turtles and lines
and every step, sends the turtle's information to model. In the model
the new location/state of the turtle is calculated depending on the command inputted.
Then the view calls model.get methods to get the new state, and update the display.

The view has multiple subclasses like commandHistory, commandLine, fileReader, and Turtle. For the most part,
these were made to make the building of view a little prettier. The turtle class was created to store 
the state of a certain turtle, which was needed when multiple turtles existed.
The model had no subclasses.
### Design Principles

#### Clean Code

* Good or Needs Improvement

We used inheritance to cut down on the amount of duplicate code, since all 3 models
had a lot of similar concepts. This was especially useful for the views, which all had reset,inputFile,
buttons, and a command line.


#### Single Responsibility

* Good or Needs Improvement

We had a class for fileReader that handled all files and translated them into 
arrays of strings. The commandHistory class also did this well as all we had to do was give it a 
command, and it updated our history.

I think we could've done this better in the view and model since
these two mega classes had many responsibilities. For example,
the view added drawn objects to the canvas, set up classes, and moved information
between the model and turtle. 

#### Open Closed

* Good or Needs Improvement

I don't think we did this well. We constantly had to make changes to our base view and model.
This led to a lot of merge conflict errors, and wasted time rewriting things.
I think we could've made this better by simplifying our classes and implementing the single 
responsibility principle better.



### Code Design Examples

#### Made easier to implement by the code's design

* Feature - Any new turtle command

* Justification - I was able to save a ton of time implementing new comamnds.
Using reflections, all I had to do was just create the new method (or just use super.command()) 
and name it the command's input string. I never had to update anything in model except for getters/setters


#### Good Example **Quentin** implemented

* Design - The turtle adding method for the tell command

* Evaluation - It was easy to read and it made a lot of sense. Plus, it utilized a lot of code that already
existed (he called startAnimation with 0 frames to call the next instruction - very clever). When I was
working on the Darwin app, I used this and although some functionalities were different, it was very easy
to change because all I had to do was @Override 1 or 2 methods.


#### Needs Improvement Example **Quentin** implemented

* Design - implementation of the tell command

* Evaluation - First off, it worked so props to him for solving a pretty tricky problem.
However, it was really confusing to read and understand. There was a lot of duplicate code (
like tryTellcommand and tryCommand) and I'm pretty sure if we had to implement another similar but different
function, it wouldn't be easy to implement. I think what we could've done instead 

The code works like this:
- pull the next instruction from our instructionQueue
- run the Command if the command is 'tell' (duplicate)
- if tell, add turtle to activeTurtles and if it's a new turtle, add it to the TotalTurtle, and pull the next instruction
- if not, run the command again
Meanwhile in the fileReader, we check for each command after tell and duplicate it in the instructionQueue so that
as we cycle through the active turtles we just pull a command.

I think a more ideal way of handling it is to modify the turtle class to hold instructions.
This means, after a tell command is used, we call all selected turtles and add instructions to their own
instruction stack, then instead of pulling instructions, we just pull from active turtles and call 
turtle.getNextInstruction. 

## Your Design

### Design Challenge
I was creating the control panel, which had the commandLine, reset button, file upload button, and 
turtle state display. 

* Trade-offs - I was left with many classes that were essentially data classes, and the code for control panel
is very dense and hard to read. 

* Alternate designs - I could've made each element its own class, but the problem was that
the elements depended on the view's instance variables and methods. This scared me away from making 
all of them have their classes. 

* Solution - I ended up using a mix. Some had their own classes, some didn't. The turtle state display 
worked well as its own class. The command line had its own class (this was pretty much just a 
data class) but had a lot of (e -> {...}) lambdas in view.  


* Satisfaction - I'm not that satisfied. I think at the moment, my priorities were on functionality.
I think if I took the time, it could've made it work with all the elements being their own classes.


### Code Design Examples

#### Good Example **you** implemented

* Design - the use of reflection to call commands 

* Evaluation - This was by far the most clever thing I did. With reflection, we didn't
have to use switch/case or big if-else chains to call commands. This meant all we had to do we just 
write the commands as a function named 'command' and then the code would work. 


#### Needs Improvement Example **you** implemented

* Design - The abundance of getters/setter methods used in the Turtle and Model classes

* Evaluation - I think I relied too heavily on getters and setters, which made it necessary for the main
Turtle/Model to have a bunch of do-nothing methods that got overwritten. I think I could've generalized the
names for methods that did similar things or utilize the new thing that Professor Duvall showed us
with lambdas and .apply()/accept(). 



## Conclusions

#### What part(s) of the code did you spend the most time on?
I definitely spent the most time on Darwin and the drawing animation functionality of the base code. 

#### How has your perspective on and practice of coding changed?
My eyes have been opened real wide to the benefit of single responsibility classes. I think if
we adheared to this rule better, it would've been a lot easier to make new apps.

#### What specific steps can you take to become a better designer?
I think overall, I need to be better at making very general and flexible superclasses, and
be better at extracting responsibilities into classes. 