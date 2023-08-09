OOLALA
====

This project implements a variety of applications that take advantage of drawing using a turtle.

Names: Albert Yuan, Scott Tremblay, Quentin MacFarlane


### Timeline

Start Date: 9/22/2021

Finish Date: 10/12/2021

Hours Spent: 100

### Primary Roles
Scott: Working on the functionality of the oolalaView class, creating the files in the utils package, working on the
CSS components of the project, writing tests.

Albert: Creating the Darwin program, refactoring, creating the file reader class, setting up the whole program at the start, working on the oolalaModel class, 
refactoring the code and sorting classes into packages, writing tests.

Quentin: Creating the L System program, refactoring, creating the image chooser class, adding command functionality, working on both the oolalaModel and oolalaView
classes, debugging code functionality, writing tests.

### Resources Used
* Browser Project
* TA mentor advice (for refactoring and creating tests)
* https://docs.oracle.com/javase/8/javafx/api/javafx/stage/FileChooser.html
* https://github.com/TestFX/TestFX/wiki/Getting-Started
* https://stackoverflow.com/questions/27785917/javafx-mouseposition
* https://docs.oracle.com/javase/8/javafx/api/javafx/scene/shape/Circle.html
* https://www.journaldev.com/848/java-file-path-absolute-canonical

### Running the Program

Main class: Main.java

Data files needed: All the files in the ./data folder found under the oolala_team05 directory

Features implemented: 
Implemented all three programs 


* User can input a file with a list of commands for the program to run. 
* User can change the image of the turtle that is displayed on the screen. 
* The turtle animates from one spot to the next when a command is inputted. 
* The user can also input commands one by one directly into the text box on the screen. 
* The user can hit the reset button to clear the screen and move the turtle back to the home spot. 
* If the user clicks anywhere on the screen, it sets that spot as the new home for the turtle. 
* There is a command history bar on the right side that lists all the commands that have been run so far, except it does not list commands that have been repeated. 
* The user can click on any of these buttons in the command history and the turtle will move according to that command again. 
* In the bottom right, the user can see the current position of the turtle in x,y coordinates and the heading of the turtle, in degrees. 
* Whenever there is an invalid command either inputted by the user or in a data file, there is a pop up in the window that says "invalid command".



### Notes/Assumptions

Assumptions or Simplifications: We assume that the turtle that pops up when you start the program is turtle number 1. 
This is for the tell command. Also, assume that the user has files and images on his/her local computer to upload to this program,
since we use the FileChooser class to implement the functionality of those buttons. The turtle in our program is a circle, 
since we were able to use the circle's built-in functions to get the coordinates of the turtle easily. 

Interesting data files: The grid.txt file is interesting, because it has lines that start with # and also lines that are 
empty. This makes it so that when scanning the file, the file reader needs to skip over the empty lines and lines with
a comment. There is also an images folder for images that the user could put for the turtle.

Known Bugs:

Extra credit:


### Impressions

This project allowed us to figure out how to work best as a team to accomplish the tasks. We split 
up the work in an efficient manner and worked well together to get it done. This project also opened
us up to using Git and learning how to code on different branches and handle merge conflicts. It was nice to be able to
write tests and get good line coverage for our code to make sure that everything was pretty much working as it should. 
Learned how to use packages to our advantage in order to clean up the file directory and make our lives easier as programmers. 