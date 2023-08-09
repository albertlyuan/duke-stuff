hangman
====

This project implements the game of Hangman.

Name: Albert Yuan 

### Timeline

Start Date: September 1, 2021

Finish Date: September 6, 2021

Hours Spent: 10


### Tutorial and other Resources Used
N/a

### Resource Attributions
N/a

### Running the Program

Main class: Main.java

Data files needed: lowerwords.txt

Key/Mouse inputs: Keyboard keys to input guess or to-be-guessed word

Known Bugs: 


### Notes/Assumptions
Change the GuesserType and SecretKeeperType variables in Main.java
to change the game player types

### Impressions
This was the first time that I thought about writing code that was easy to read.
I had fun identifying areas of code that could/should be extracted as methods to
improve readability. One section in particular is in HangmanGame.setupDisplay().
There used to be a ton of loops and code that set up the various displays but after extracting them,
the method is much more intuitive. It's very clear that the method creates displays for 
available letters, number of lives, and the current secret word. 
