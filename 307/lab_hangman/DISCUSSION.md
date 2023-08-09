# Lab Discussion
## Albert Yuan aly11
## Samuel Li sbl28


### Original Code

 * What pieces of code help versus obscure your understanding?
   * setupdisplay and playguess
 * What comments could be removed and replaced with better variable names or by calling shorter methods?
   * instance variables are not well named
 * What Code Smells can you find?
   * duplicate code
   * uncommunicative variable names/excessive comments 


### Making Game Between New Kinds of Players

 * Describe as concretely as you can, what lines of a current game class need to be changed to implement a game between two new players.
   * auto game:
     * myindex and myletters variables (43-45)
     * iterates through letters to guess
   * interactive game:
     * handles user input

### Extracting Player Classes

 * What parts of the game classes' code is the same?
   * First 4 lines in constructors
   * instance variables except for myindex and myletters
   * start methods
   * setupdisplay except for handlekeyinput in interactive
   * playguess
 

 * What parts of the game classes' code is different?
   * handlekeyinput in interactive
   * getinput and make guess in auto
 
 * The method ```playGuess()``` is composed of several steps, which ones make sense as part of the game's rules and which as part of the two kinds of players?
   * all the steps but endgame check make sense as part of the game's rules
   * endgame check makes sense as part of the two kinds of players
 
 
 * Which class should be responsible for the instance variable ```mySecretWord```?
   * ```HangmanGame```, ```Guesser```, or ```SecretKeeper```
     * Secret Keeper
 * Which class should be responsible for the instance variable ```myNumGuessesLeft```?
   * ```HangmanGame```, ```Guesser```, or ```SecretKeeper```
     * hangmangame

 * Which class should be responsible for the instance variable ```myDisplayWord```?
   * ```HangmanGame```, ```Guesser```, or ```SecretKeeper```
     * hangmangame

 * Which class should be responsible for the instance variable ```myLettersLeftToGuess```?
   * ```HangmanGame```, ```Guesser```, or ```SecretKeeper```
     * guesser
   

### Refactored Code

 * In what ways is the refactored code simpler?

 * In what ways is the refactored code more complex?

 * What trade-offs did you make when refactoring the original code?

 * Which code do you prefer and why?
