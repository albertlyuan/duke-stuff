# Fluxx Objects Design
## Albert Yuan


### Tracking Changes

#### How new design reduces future logic changes
* CurrGoal is determined by the first item in a stack of goals.
* all cards have a doAction class so card use can be generalized


### CRC Card Classes (Updated)
This class's purpose is to be the class for the game board. This class is what each player will directly
interact with. Every player will interact with the same board. The board will keep track of the 
discard pile and deck. It will also hold all the methods to change the goal/rule
|Board| |
|---|---|
|void nextTurn()         |Player|
|void ActionY()...      |Card|
|void GoalY()... |Goal|
|void ruleX()...  |Rule|

```java
public class Board { 
    // changes the currPlayer variable
  public void nextTurn ();
    
  //rule specific methods that change the rules
  public void rule1,rule2,...ruleX()
    
  //goal specific methods that perform actions
  public void action1,action2,...actionZ() 
 }
 ```
This the class to keep track of players' hands. It contains methods to modify the hands
as well.

|Player| |
|---|---|
|void drawCard() |Board |
|void playCard() |Action|
|void discard()|Card|
| | |

```java
public class Player {
  // takes random cards from the DECK and moves it to the player's hand. 
  // player hands and played cards(keepers) are stored in 2 hashmap<String,ArrayList>
  public void drawCard ()

  // changes moves card from currPlayer to the discard pile 
  public void discard ()

  // prompts the player to select a card to play
  public void playCard ()

 }
 ```

This the abstract class for all the different cards. it will hold the description, name


|Card| |
|---|---|
|String getType()         |Board |
| |Player|
| |Keeper|
| |Action|
| |Rule|
| |Goal|
| |Ungoal|
| |Creeper|


```java
public class Card {
     public description;
     public name;
     public type;
  
 }
 ```
This class's purpose is to represent keeper cards.

|Keeper| |
|---|---|
|void SpecialAbility()|Board |
|void getType()|Card |
|void passiveAbility|Player |
| |Creeper|
```java
public class Keeper extends Card {
  //optional special ablity of keeper when used 
  public void doAbility();
  //passive ability that keeper may have that is used every turn
  public void passiveAbility();
  //get Keeper type (ie booty vs ship)
  public void getType();
}
```


This class's purpose is to represent Action cards.
This will have an image of the action, and the type such as 'switch cards'. Action cards
will also execute the action.

|Action| |
|---|---|
|void doAction(action)         |Board|

```java
public class Action extends Card{
  // performs the action specified by the card (ie. swap card btwn 2 players)
  public void doAction()
 }
 ```

This class's purpose is to represent Rule cards.
This will have an image of the new rule, and the type such as 'draw 2 cards'. This will
call setRule in Board().

|Rule| |
|---|---|
|void SetRule() |Board|

```java
public class Rule extends Card{
  // changes the currentRule variable
  public void setRule ()
}
 ```

This class's purpose is to represent Goal cards.
This will have an image of the object, and the type such as 'milk and cookies'. This will
call setCurrGoal in Board().

|Goal| |
|---|---|
|void getGoal()         |Board|
|void checkWin()       ||
```java
public class Goal extends Card{
    //sets board.currGoal to this object 
  public void setCurrGoal ();

    //checks that currPlayer.hand contains the cards to win
  public void checkWin ();
}
 ```

This class's purpose is to represent Surpise cards. Works exactly like the action class but can be used out of turn.

|Surpise| |
|---|---|
|void doAction()|Board |
| |Card |
| |Player |
| |Keeper|

```java
public class Surpise extends Card {
    //sets currUngoal to this card.
     public void doAction();

 }
 ```


This class's purpose is to represent Creeper cards. 

|Creeper| |
|---|---|
|void SpecialAbility()|Board |
| |Card |
| |Player |
| | |

```java
public class Creeper extends Card {
    //for handling Creeper active ability when used.
     public void doAction();
  //for handling Creeper win/lose Condition
  public void winCondition();
  //for handling Creeper passive ability  
  public void PassiveAbility();
 }
 ```
### Game Loop (Updated)

* After setting up the game, turns proceed like this:
    1. Draw the number of cards currently required (start with Draw 1)
    1. Play the number of cards currently required (start with Play 1)
    1. Discard to comply with any Limit rules in play (start with no limit)
    1. The game ends when a player meets the conditions of a current Goal card.


### Use Cases (Updated)

* A player plays a Goal card, changing the current goal, and wins the game.
```java
Player.playCard()
        //player will select card from hand
        if (selected instanceof Goal){
            Player.discard(selected)
            selected.setGoal();
        }

selected.setCurrGoal()
        Board.goals.push(this());
        Board.currGoal = this();

Board Loop:
        currGoal.checkwin()
        
currGoal.checkwin()
        for (Player player : allPlayers){
            //checks win condition
            if (player.played.contains(goal1) && player.played.contains(goal2){
                endgame();
            }
            
        }
```

* A player plays a Rule card, adding to the current rules to set a hand-size limit, requiring all players to immediately drop cards from their hands if necessary.
```java
Player.playCard()
        //player will select card from hand
        if (selected instanceof Rule){
            Player.discard(selected)
            selected.setRule();
        }

//selected nCardLimit        
nCardLimit.setRule()
        Board.playedRules.add(this.name)
        currRule = playedRules.get(playedRules.size()-1);
        Board.nCardLimit();


Board.nCardLimit(){
  handlimit = n;
}
        
Board Loop:
        if (handlimit){
            while(currPlayer.hand.size() > handlimit){
                Player.discard()
            } 
        }
        
Player.discard()
  //prompts player to select card to discard
  hand.remove(selected)
  Board.discardPile.add(selected)
  
  ```

* A player plays a Rule card, changing the current rule from Play 1 to Play All, requiring the player to play more cards this turn.
```java
Player.playCard()
        //player will select card from hand
        if (selected instanceof Rule){
            Player.discard(selected)
            selected.setRule();
        }
        
//selected playAll
playAll.setRule()
        Board.playedRules.add(this.name)
        currRule = playedRules.get(playedRules.size()-1);
        Board.playAll();

Board.playall(){
        mustPlay = currPlayer.hand.size();
}

Board Loop:
        if (mustPlay){
            int playCount = mustPlay;
            while( currPlayed > 0){
                Board.playCard()
                playCount -= 1;
            }
        }

```

* A player plays an Action card, allowing him to choose cards from the discard pile and play them.
```java
Player.playCard()
        //player will select card from hand
        if (selected instanceof Action){
            Player.discard(selected)
            selected.doAction();
        }

playFromDiscard.doAction()
        for (card : Board.discardPile){
            //prompts the user to accept of decline
            if (accepted){
                currPlayer.hand.add(card)
                Player.playCard(card)
            }
         }

```

* A player plays an Action card, allowing him to choose cards from another player's hand and play them.
```java
Player.playCard()
        //player will select card from hand
        if (selected instanceof Action){
            Player.discard(selected)
            selected.doAction();
        }
        
playFromDiscard.doAction()
        for (Player player : players){
            //prompt player to select victim
        }
        for (Card card : victim.hand){
            //prompts the user to accept of decline
            if (accepted){
                hands.get(currPlayer).add(card)
                playCard(card)
            }
         }

```

### Use Cases (New)
* Player drew a Shackles Creeper but has a Captain's Hat keeper
```java
Board Loop:
        for (Card card : currplayer.played){
            card.passiveAbility();
        }

Shackles.passiveAbility(){
        if (currPlayer.played.contains(CaptainsHat)){
            currPlayer.hand.add(this());
        }   
}
        
Player.playCard()
        //player will select card from hand
        if (selected instanceof Creeper){
            selected.doAction();
        }

Shackles.doAbility(){
        //selects player from Players
        currPlayer.played.remove(this());
        selectedPlayer.played.add(this());
}
```

* Canceled Plans Surprise is used out of turn.
```java
Player.playCard()
        //player will select card from hand
        if (selected instanceof Goal){
            Player.discard(selected)
            selected.setGoal();
        }

Board Loop:
        for (Player player : Players){
            if (player != currPlayer){
                if(player.hand.contains(instanceof Surprise) && wantsToPlay){
                    player.playCard()
                }       
            }   
        }     

CanceledPlans.doAction(){
            Board.goals.pop();
            Board.currGoal = Board.goals.peek()
        }
```