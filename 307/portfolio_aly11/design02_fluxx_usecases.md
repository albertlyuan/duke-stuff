# Fluxx Objects Design
## Albert Yuan


### CRC Card Classes (Updated)
This class's purpose is to be the class for the game board. This class is what each player will directly
interact with. Every player will interact with the same board. There will be buttons to end you turn,
draw card(s), play new rules, and set new goals. The board class will keep track of which cards are in
the deck and which cards are discarded.

|Board| |
|---|---|
|void nextTurn()         |Keeper|
|void drawCard()      |newRule|
|void playCard() |Action|
|void setRule(rule)    |Goal|
|void setCurrGoal()||
|void ruleX()|
|void GoalY()|
|void ActionY()|

```java
public class Board {
     // changes the currPlayer variable
     public void nextTurn ()
       
     // takes random cards from the DECK and moves it to the player's hand. 
    // player hands and played cards(keepers) are stored in 2 hashmap<String,ArrayList>
     public void drawCard ()
    
  // changes moves card from currPlayer to the discard pile 
    public void discard ()
    
  // prompts the player to select a card to play
     public void playCard ()
     
  // changes the currentRule variable
     public void setRule (String rule)
     
  // changes the currentGoal variable
     public void setCurrGoal (String goal)
    
    //rule specific methods that change the rules
  public void rule1,rule2,...ruleX()
  //goal specific methods that change the goals
  public void goal1,goal2,...goalY()
  //goal specific methods that perform actions
  public void action1,action2,...actionZ() 
 }
 ```

This class's purpose is to represent keeper cards. 
This will have an image of the object, and the type such as 'cookie'.

|Keeper| |
|---|---|
|String getType()         |Board |
| ||
```java
public class Keeper {
     // returns the type of keeper card (ie. cookie)
     public String getType()
 }
 ```

This class's purpose is to represent Action cards.
This will have an image of the action, and the type such as 'switch cards'. Action cards
will also have actions that affect players' hands.

|Action| |
|---|---|
|void doAction(action)         |Board|
|String getDescription()       ||
```java
public class Action {
  // performs the action specified by the card (ie. swap card btwn 2 players)
  public void doAction()
  
  // returns the type description of the action
  public String getDescription()
 }
 ```

This class's purpose is to represent NewRule cards. 
This will have an image of the new rule, and the type such as 'draw 2 cards'. This will
call setRule in Board().

|NewRule| |
|---|---|
|String getRule()         |Board|
|String getDescription()         ||
```java
public class NewRule {
  // returns the type of newRule. used in board.setRule
  public String getRule()
  
  // returns the description of the rule
  public String getDescription()
}
 ```

This class's purpose is to represent Goal cards.
This will have an image of the object, and the type such as 'milk and cookies'. This will
call setCurrGoal in Board().

|Goal| |
|---|---|
|String getGoal()         |Board|
|String getDescription()         ||
```java
public class Goal {
  // returns the type of newGoal. used in board.setGoal
  public String getGoal()
  
  // returns the description of the goal
  public String getDescription()
}
 ```

### Game Loop

* After setting up the game, turns proceed like this:
    1. Draw the number of cards currently required (start with Draw 1)
    1. Play the number of cards currently required (start with Play 1)
    1. Discard to comply with any Limit rules in play (start with no limit)
    1. The game ends when a player meets the conditions of a current Goal card.


### Use Cases

* A player plays a Goal card, changing the current goal, and wins the game.
```java
Board.playCard() //shows hand;
if (selected instanceof Goal){
    Board.setGoal(newGoal);
}

//in Board.setGoal
card1 = newGoal.card1;
card2 = newGoal.card2;

//in step function
for (String player : players){
    if (hands.get(player).contains(card1) && hands.get(player).contains(card2)){
        endgame();
    }
}
        

```

* A player plays a Rule card, adding to the current rules to set a hand-size limit, requiring all players to immediately drop cards from their hands if necessary.
```java
Board.playCard() //shows hand;
  if (selected instanceof newRule){
      Board.setRule(newRule);
  }

Board.setRule(){
  playedRules.add(newRule)
  currRule = playedRules.get(playedRules.size()-1);
  switch (currRule){
      case nCardLimit: Board.nCardLimit();
      ...
  }
}

Board.nCardLimit(){
  handlimit = n;
}

//in step function
  if(hands.get(currPlayer).size() > handlimit){
      board.discard()
  }
  
Board.discard()
  //prompts player to select card to discard
  hands.get(player).remove(selected)
  discardPile.add(selected)
  
  ```

* A player plays a Rule card, changing the current rule from Play 1 to Play All, requiring the player to play more cards this turn.
```java
Board.playCard() //shows hand;
  if (selected instanceof newRule){
    Board.setRule(newRule);
  }

Board.setRule(){
  playedRules.add(newRule)
  currRule = playedRules.get(playedRules.size()-1);
  switch (currRule){
    case PlayAll: Board.playAll();
    ...
  }
}

Board.playall(){
    mustplay = ALL;
}

//in step function
if (mustplay == ALL){
  while(hands.get(currPlayer).size() > 0){
    Board.playCard()
  }
}else if(mustplay == ALLbut1){
  while(hands.get(currPlayer).size() > 1){
    Board.playCard()
  }    
}
else{
  playcount = mustplay
  while(playcount > 0){
    Board.playCard()
    playcount -= 1;
  }
}

Board.playCard()
//prompts player     to select card to discard
  hands.get(player).remove(selected)
  discardPile.add(selected)
//executes card based on if it's action/newrule/goal/keeper

```

* A player plays an Action card, allowing him to choose cards from the discard pile and play them.
```java
Board.playCard() //shows hand;
  if (selected instanceof Action){
    Board.doAction(action);   
  }

Board.doAction(action){
  switch (action){
    case playFromDiscard: Board.playFromDiscard();
    ...
  }
}

Board.playFromDiscard(){
  for (card : discardPile){
    //prompts the user to accept of decline
    if (accepted){
        hands.get(currPlayer).add(card)
        playCard(card)
    }
  }
}


Board.playCard(card)
  if (card){
      hands.get(player).removed(card)
      discardPile.add(card)
      //executes card based o n if it's action/newrule/goal/keeper
  }else{
      //prompts player     to select card to discard
      hands.get(player).remove(selected)
      discardPile.add(selected)
      //executes card based o n if it's action/newrule/goal/keeper
  }
```

* A player plays an Action card, allowing him to choose cards from another player's hand and play them.
```java
Board.playCard() //shows hand;
  if (selected instanceof Action){
    Board.doAction(action);
  }

Board.doAction(action){
  switch (action){
    case playFromDiscard: Board.stealCard();
    ...
  }
}

Board.stealCard(){
    //prompt player to select victim
  for (card : hands.get(victim)){
    //prompts the user to accept of decline
    if (accepted){
      hands.get(currPlayer).add(card)
      playCard(card)
    }
  }
}


Board.playCard(card)
  if (card){
    hands.get(player).removed(card)
    discardPile.add(card)
    //executes card based o n if it's action/newrule/goal/keeper
  }else{
    //prompts player     to select card to discard
    hands.get(player).remove(selected)
    discardPile.add(selected)
    //executes card based o n if it's action/newrule/goal/keeper
  }
```