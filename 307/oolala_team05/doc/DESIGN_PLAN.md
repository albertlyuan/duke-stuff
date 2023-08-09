# OOLALA Design Plan
## Quentin MacFarlane qtm, Scott Tremblay sjt40, Albert Yuan aly11


### Team Roles and Responsibilities
Scott - view class
Quentin - model class
Albert - start building basic design 


### High Level Design
Classes
* view for displaying the drawing
* model for calculating the moves and reading the file


### CRC Card Classes

This class's purpose or value is to represent the view. 


|view| |
|---|---|
|makeScene|model|
|step| |
|makeObject| |
|showError| |
|handleClick| |
|handleButton| |
|changeColor| |


This class's purpose or value is to represent a view:
```java
public class view {
     public boolean makeScene();
     public boolean setUp();
     public boolean step();
     public boolean makeObject();

}
 ```

This class's purpose or value is to represent the model.


|model| |
|---|---|
|readFile|view|
|translateStep||
|sendToView| |
|readFromCommandLine|view|
|addToHistory||
|setHome||


This class's purpose or value is to represent a model:
```java
public class model {
     public boolean readFile();
     public boolean translateStep();
     public boolean sendToView();

 }
 ```
### Use Cases 

 * The user types 'fd 50' in the command window, sees the turtle move in the display window leaving a trail, and has the command added to the environment's history.
```java
model.readFromCommandLine();
model.addtoHistory(instruction);
model.translateStep(); //has a switch statement to decipher which command has been inputted
model.sendToView();
view.step();
view.makeObject();
```

 * The user loads a file of commands, sees the turtle move in the display window leaving a trail, and has the command added to the environment's history.
```java
model.readFile();
model.addtoHistory(instruction);
model.translateStep(); //has a switch statement to decipher which command has been inputted
model.sendToView();
view.step();
view.makeObject();
```

 * The user types '50 fd' in the command window and sees an error message that the command was not formatted correctly.
```java
model.readFromCommandLine(); //this method has a try catch block, so the catch will catch this error and call the showError() method from the view class
view.showError(); //this method will be in the catch block of the readFromCommandLine() method
```

 * The user clicks in the display window to set a new Home location.
```java
view.handleClick();
model.setHome(x,y);
```

 * The user changes the color of the environment's background.
```java
view.handleButton();
view.changeColor(); //this method will be called whenever a button is clicked telling what color the background should change to 
```

