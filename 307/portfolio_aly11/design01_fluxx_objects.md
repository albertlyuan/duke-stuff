# Fluxx Objects Design
## Albert Yuan

### High Level Design Ideas

This class's purpose is to be the class for the game board. This class is what each player will directly
interact with. Every player will interact with the same board. There will be buttons to end you turn, 
draw card(s), play new rules, and set new goals. The board class will keep track of which cards are in 
the deck and which cards are discarded.

|Board| |
|---|---|
|void nextTurn()         |Player|
|void drawCard()      |Card|
|void playCard() ||
|void setRule(rule)    ||
|void setCurrGoal()|


This class's purpose is to be the base class for each card type. Every card will have a type and description
and owner. Sometimes the owner will be none because it'll be in the deck or in the discard pile.

|Card| |
|---|---|
|String getType()         |Keeper|
|String getDescription()      |Action|
|String getOwner()    |NewRule|
| |Goal|

This class's purpose is to represent keeper cards. This is a child class of Card.
This will have an image of the object, and the type such as 'cookie'.

|Keeper| |
|---|---|
|String getType()         |Player |
| |Card|

This class's purpose is to represent Action cards. This is a child class of Card.
This will have an image of the action, and the type such as 'switch cards'. Action cards 
will also have actions that affect players' hands.

|Action| |
|---|---|
|void doAction(action)         |Player|
|String getDescription()       |Card|


This class's purpose is to represent NewRule cards. This is a child class of Card.
This will have an image of the new rule, and the type such as 'draw 2 cards'. This will 
call setRule in Board(). 

|NewRule| |
|---|---|
|String getRule()         |Board|
|String getDescription()         |Card|

This class's purpose is to represent Goal cards.This is a child class of Card.
This will have an image of the object, and the type such as 'milk and cookies'. This will
call setCurrGoal in Board().

|Goal| |
|---|---|
|String getGoal()         |Board|
|String getDescription()         |Card|

This class's purpose is to keep track each player's hand and played keeper cards.
If the player draws a card using the method in board, a addCard will be used. 
If an action modifies the hand, discard and addCard will be used to move cards around.

|Player| |
|---|---|
|String getHand()         |Board|
|String keepers()    |Card|
|void discard(card) ||
|void addCard(card)|
