# Journal : OOGA Project Analysis

#### Albert Yuan

#### 09 checkerz

#### 12/12/2021

## Design Review

### Design Description

Our program has 3 main parts: view, controller, and board (model). The view called and was called by
controller, and the model was only called by the controller. Within the controller, we had a
boardbuilder, which parsed a game.json and returned game-specific objects that were sent to the
model and view to run the game. Within the view, there was various UI objects that helped organize
the class, and a boardView object, which was an abstraction for the grid of display pieces. The
model got all of its objects from the boardbuilder. This includes the players (which holds the
pieces), the pieces (which hold moves), moves (which determine how a piece moves), end conditions (
which determine how the game ends) and powerups (which change certain game states).

The game ran like so: upon loggin in, a controller is made, which loads a default json into
boardbuilder. The boardbuilder returns objects that are displayed in view, and objects that are used
in model. When a player selects a piece, we check for all the legal moves in model and display them.
When the user selects a valid move, we execute the move in model, and then return an updated list of
pieces to the view. We also check to see if the game is over, and if so, we create a pop-up that
notifies the user.

### Change Scenarios

* Adding new game variation
    * To add a new game variation, all one has to do is create a new game json file. Right now
      people people can change how the pieces move (which means either creating a new Move class or
      changing an existing move class's parameters), which & where pieces are on the board (by
      changing the json's csv), the board shape, what powerups are where(by changing the json's
      powerups json), and how the game ends (by changing the json's rule json). The fact that we can
      change any of these just by changing the game jsons's data shows:
        * Single reponsibility - the powerups, end conditions, move, and other classes all have one
          job, thus making it very easy to make changes to them without affecting the functionality
          of other classes. For example we have multiple different types of powerups and end
          conditions that can be edited without affecting how the model activates them.
        * Open-closed principle - the powerups, end conditions, move, and other classes all are
          extendable as shown by their ability to change in drastic ways. For example, we have
          powerup and move inheritance hierarchies that allow for them to be easily extendable.
        * Liskov Substitution - the powerups, end conditions, move classes are all substitutable,
          which is crucial because a certain game may or may not call certain subclasses. For
          example, in the Pawn piece, we don't have jump move but instead an Enpassant move, which
          is the exact opposite of the Knight piece. Regardless these are both represented by the
          piece class which iterates through all Move type objects. This also showcases how our move
          class displays polymorphism.
* Changing data file format
    * To change the format of the data from JSON to XML for example, we would only have to change
      our JsonParser class. We rely heavily on the JsonObject class that's provided by org.json as
      it's essentially a hashmap, but we could easily build our own JsonObject class that acts the
      same way. Either way, since our JsonParser encapsulates the entire file to JsonObject process,
      we can just rewrite it to convert XML to JsonObject and continue running our program as is.
      This also shows how we abstracted the parsing process as all we do is just call
      JsonParser.BuildJson(file_object) and get a JsonObject in return.
* Add new Player view
    * Our current Game/Editor views and controllers utilize the following SOLID principles:
        * Interface Segregation Principle - Our editor and game controllers display this as the
          editor controller contains methods relating to creating pieces on the board, whereas the
          game controlelr contains methods relating to updating the timer. The way that this is
          implemented is that the editor and game controller interfaces inherit common methods from
          a ControllerInterface interface. This way, both controllers don't contain methods that
          aren't used, and we minimize duplicate code by inheriting from the controller superclass.
        * Dependency Inversion Principle - editor and game views take in a controller, which each
          have methods specific to them. Thus, we make the views dependent on
          EditorControllerInterface and GameControllerInterface respectively. This is so that we are
          guaranteeing the implementation of methods that we are calling in editor and game view,
          which displays dependency inversion Principle. To add a new View, one must create the view
          as well as a corresponding controller. This means making the view extend View superclass,
          and creating a controller that extends Controller superclass and implements an interface
          that extends ControllerInterface

### Design Pattern Example

The EndConditionRunner and the EndConditionInterface classes display an example of the Strategies
design pattern. The way our program works is that the runner is being called to check all end
conditions. This works because all end conditions implement the EndConditionInterface, which ensures
that all end conditions can be called using .isSatisfied(). Thus, the context is the runner, the
strategy is the EndConditionInterface, and the concrete strategies are the various end conditions.

### API Examples

#### Good

* I think the View API was something stayed pretty constant. This API's purpose was to change things
  on the display. We had a bunch of methods that were never used (everything except show error and
  update/initialize display). In my opinion these could've been made private, but this opens the
  door for config files to set the initial board color, style, theme, and language. However, these
  should probably be put into a separate interface or something:
    * void updateDisplay(List<PieceViewBuilder> pieceViewList);
    * void initializeDisplay(List<PieceViewBuilder> pieceViewList, List<Location>
      specialPieceLocations, Location bounds);
    * void changeBoardColor(Color color1, Color color2);
    * void changePieceStyle(String style);
    * void changeTheme(String theme);
    * void changeLanguage(String language);
    * static void showError(String message);

* This API was very easy to use. The method's didn't change very much as the View was made very
  early and very well. The only changes we had were the parameters for update/initialize display,
  but these were very easy to understand since it makes sense that we would be passing in updated
  piece data. This API is also very difficult to misuse as all of these methods don't have any
  methods that expose private fields to the public.

* This API does a good job at encapsulating the process of updating the display. I think it lends
  itself to extension well since having pieces as arguments potentially allows for other board games
  since many board game's work by just moving pieces/cards.

#### Needs Improvement

* Engine - Engine is the API for our model and it contains methods:
    * void movePiece(Location start, Location end);
    * List<Location> getLegalMoves(Location location);
    * boolean canMovePiece(Location location);
    * List<PlayerInterface> getPlayers();
    * List<PieceInterface> getPieces();

* This API was pretty difficult to use since it was often changed. It also doesn't follow Bloch's
  principles for preventing misuse, as we return the exact copies of players and pieces, so this
  might cause problems since changes to the players/pieces will affect the players/pieces in Engine.
  We can easily fix this by making an immutable list or by just returning a copy of each
  player/piece. I think we could've made this API better by having a better understanding of APIs.
  We were very unfamiliar with how to design APIs, and we should've known been better at realizing
  that the basis of the model is to move a piece and return data to the display. In retrospect this
  is very easy to see, but at the time we were very blind to this.

* This API does a good job of encapsulating the processes of the game's piece movement, and lends
  itself nicely to extension as seen by the GameBoard and EditorBoard classes, which check for a win
  and add additional pieces to the board respectively. This API did change during the sprints, (many
  methods were added and removed) but these methods more or less stayed the same. The one major
  change that happened was movePiece's return value. We used to use a Turn object that was sent to
  the View to display, but now we execute the move and then send the Pieces using the getter method
  to the view to display. The reason for this change was to help the implementation of castling and
  other multi-piece moves, as well as to simplify things for other teammates, since the Turn object
  was a little hard to understand.

### Code Design Examples

#### Good Example **Gordon** implemented

* Moves - Gordon made the move class which is defined by parameters set in a piece's data file. The
  way it works is that each piece has 1 or many Move objects, and each Move object is used to
  determine which squares are valid moves, and if a valid move is selected, the appropriate Move
  object execute the move. Moves are an inheritance hierarchy as there is a Move abstract class and
  many subclasses such as TranslationMove and JumpMove.

* I thought this design was very good as it encapsulates the complex logic of determining legal
  moves and executing the move. In our model, all that we see is piece.updateMoves() which updates
  the legal moves, and move.executeMove() which executes the move. As someone who didn't work very
  much on the design of this, it was very easy to learn how to use it, since these functions are
  named appropriately. It also displays good polymorphism as no matter what Moves a piece has, the
  piece is functional, and it provides a way for more moves to be made. I remember when there were
  only Translation and Jump moves, but given this design, I bet it was probably very easy to make
  the other move classes since they all share a bunch of methods from the superclass, and
  efficiently override necessary methods.

#### Needs Improvement Example **Tarun** implemented

* Powerups - Tarun made a powerup class which is defined by parameters set in the powerups json
  file. The way it works is that each powerup has 1 or many Locations and if a player places a piece
  on a powerup location, the powerup calls a method on the piece used or the Player.

* I think the way that powerups are implemented does not lend well to adding more variations of
  powerups. With the current set up, every time a new powerup is added, there must be an added
  method to Piece or Player, which will lead to the interface being crowded with many public
  methods. I think a better implementation is one similar to my implementation of end conditions.
  This would look like so:
    * When we read in the powerups and locations from the data file, we create one object for each
      powerup at each location, and then store them in a runner. Now every time a powerup is
      activated, we execute the powerup and then remove it from the runner (or keep it in the runner
      if that's how they work).
    * We make all powerups affect the player OR piece, not both. In my opinion, it's probably better
      to do player, since this allows us to access pieces.
    * We execute the powerup in the powerup object instead of the having all the logic in
      piece/player. If we make all powerups use a player, we can make edits to the player and
      encapsulate all powerups logic in the object. Now if we add a new powerup (ie. a powerup that
      gives you a free teleport to any space), we can add it to the game without making a
      Piece.teleport or Player.teleport method and instead, create a new powerup class that executes
      player.getPiece(piece that activated the powerup).movePiece(the new teleport location)
      and then call piece.updateMoves(piece) with every piece. This will move the piece to the
      teleport location, and then update every piece's legal moves.

## Your Design

### Design Challenges

#### Design #1

* When making PieceBuilder, I initially had it so we passed in the bottom team. This was to help
  make the Moves because black team (the top team) moves down (positive Y) and the white team (
  bottom team)
  moves up (negative Y), so when making pawns, we need to do some logic. However, we use
  PieceBuilder in Model when we promote pieces (or use powerups). So the tradeoff was either to make
  separate piece jsons for each team (doubling the data files) or pass in a PieceBuilder into model
  with the correct bottom team.

* Alternate designs - Pass in a PieceBuilder into model from boardbuilder with the correct bottom
  team. This would add to the getter methods that boardbuilder has, as well as the amount of
  parameters in the constructor of board.

* Solution - we chose to double the amount of data files

* Justification or Suggestion - Passing in a Piecebuilder would be messy as it would muck up the
  Interfaces of the boardbuilder and board. Also, by making it so that no bottom color is needed, we
  were able to make it into one big static method, which allowed us to use it more easily in the
  board and powerups. It also set the precident for making the EndconditionBuilder and
  PowerupsBuilder static as well. I think this was one of our smarter design choices since it
  encapsulates the building processes and makes it so that there is less setup necessary to use the
  builders.

#### Design #2

* When checking for if the game is over, we initially had it so that the end Game conditions were
  checked, and then checkmate was checked. Checkmate was not an end condition because Checkmate is
  innate to all of our chess variations. The trade off was to add checkmate to all rules data files
  and create a checkmate end condition, or to just have checkmate as a board method.

* Make a checkmate end condition and add it to all rules data files (thus making it more complex to
  make your own data files)

* We chose to make a checkmate end condition and add it to all rules data files.

* By making a checkmate End Condition, we accomplished many things. Firstly, we made board a lot
  cleaner. Board has a lot going on, and we simplified a method by encapsulating everything in the
  end condition runner. Secondly, we were able to reduce duplicate code since both Moves and
  checkmate share this process of looking for checks (it's illegal to move your piece and cause
  check on your king). This led us to make MoveUtility.isCheck().

### Abstraction Examples

#### Good

* EndConditionInterface

* We wanted to be able to build and run any end conditions that were defined in the data files. This
  was possible by making end conditions that exhibited polymorphism since reflection requires you to
  know the constructor, and the runner relies on all end condition having the same methods. The
  abstraction of end condition allowed us to be able to build and run any end conditions. This means
  the way our program checks to see if the game over, is completely data driven

#### Needs Improvement

* Config exceptions

* I think I could've done a better job at designing the confix exceptions. For each one, I just extended
the base exception, whereas I think it would've been smart to create a ConfigException and extend that 
for each of my exceptions. One thing that I wanted to do was make the exceptions tell the user exactly 
what and where was wrong in the json file. This could've worked by having the exception hold information 
about the what and where, and then when we catch it in `boardbuilder.build()`, we throw a ConfigException
that takes the thrown exception as an argument. This can then be used to tell the user what type of error
was thrown, why it was thrown, and where in the json caused it.

### Code Design Examples

#### Good Example **you** implemented

* EndConditionInterface - This interface was used to define the methods for all interfaces. We
  assume that all endCondition work with 2 players, and return one of them (when the game is over)
  or `Gamestate.running` (when the game is not over). We also assume that each has been set up upon
  instantiation, as all constructors call for a map and a list of players. The map contains any
  arguments from the data file, and the list of players provides a way to access all pieces.

* This method encapsulates all the logic to determine a winner and allows for easy expansion as we
  built is to that any end condition we can think of can be made and run this way. I also think it
  was good design to make the end condition runner which encapsulated the entire process of
  iterating and checking all end conditions present for the game. This was important because it
  reduced the amount of clutter in our model, which was getting messy and very complicated

#### Needs Improvement Example **you** implemented

* BoardBuilder - This class's purpose was to build all objects for the view and model. It had 4 or 5
  main parts:
    * It parsed the file that was inputted, and extracted all the data from it.
    * It iterated through the CSV and created pieces with the PieceBuilder
    * It created all end conditions with the EndconditionBuilder
    * It created all powerups with the PowerupsBuilder To run this, we call .build(), which returns
      void and then we use getters to get the pieces, end conditions, and powerups.

* I think the getters really spoil the design of this class. If we wanted to add something new like
  some data file specific graphic, we would need another getter method. I think a better way of
  doing it might require only 2 getter methods. First, we have a getter method that returns all the
  things that were created. This would be in the form of a list of strings. Then a second getter
  method accept a String and returns an object using reflection. I'm not sure exactly how this would
  work, but I imagine that it would be possible. Now no matter how many things are created by the
  board builder, we only have 2 getter methods.

## Conclusions

#### What part(s) of the code did you spend the most time on?

I spent most of my time building most of the entire Config package, most of the enconditions, and
the game over screen. The boardbuilder (and Piecebuilder, EndConditionBuilder, and PowerupsBuilder)
took a lot of time because I was letting Gordon and Sam determine what information datafiles needed
to have and since they changed their design often, I was also. It took even more time for me because
I had to update tests, and datafiles.

#### What is your biggest strength as a coder/designer?

I think my biggest strength is that I create code that mostly follows SOLID and the 4 pillars of
OOPS in an efficient time frame.

#### Thing #1 you have done this semester to improve as a programmer/designer

I think the first thing I've done to improve is that I separate out methods/classes a lot more. In
the past I found no real purpose of doing this, but now I really understand the value of making code
easy to read and code that lends itself well to expansion.

#### Thing #2 you have done this semester to improve as a programmer/designer

I'm a lot more cognizant of the importance of testing. I really found it annoying to think of tests
and to create them, but now I really enjoy being able to run tests after making a significant change
to check that I didn't break the functionality of anything.

#### What is your favorite part of working on "large" team software projects?

I really enjoy working on my part, and then when I merge, seeing how it connects with other people's
parts. This was something that my cell society team was really good at and that's why I think they
were probably my favorite people to work with.