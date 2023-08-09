# Lab Java Design Pattern Discussion
### Tarun Amasa tra23, Richard Deng rld39, Gordon Kim kk388, Samuel Li sbl28, Luis Pereda Amaya lip4, Albert Yuan aly11,


## Iterator

 * Like: restricts user to access collection without being able to modify contents. This 
 
 * Learned: iterators have issues when items are added/removed. To fix this, we can either disallow 
 modification to the collection, or add methods to insert after/before and remove.


## Factory (static methods)

* Like: these interfaces define methods that work with any subclass.
  
* Learned: This is just an interface for creating objects so the type of object created doesn't matter when
  a method is called
## Factory (class)

* Like: creates objects without needing to know how to instantiate them. 
* Learned: this returns a subclass casted to the superclass so that every subclass  operates the same way 


## Strategy

* Like: you can call any strategy in the same manner
* Learned: this works by having a bunch of classes share an interface. Then when you want to use one,
  you can use them all by calling the same method


## Template Method

 * Like: allows for flexible algorithms by leaving room for changes
 
 * Learned: The template class is an subclass of an abstract class with 1 public method that calls everything. 
This works by having the public method call the other private methods, which get overwritten.

## Builder

 * Like: builds multiple different types of objects based on how the interface is overridden.
 
 * Learned: the builder class works by having a builder and director. The builder implementing an 
interface and builds a certain type of object. The director calls the builder and then calls
builder.get() to get the product. You can change the builder class to get a different output
 
## Observer

 * Like: Allows for a bunch of classes that depend on 1 class to all react to changes at once
 
 * Learned. Sets all classes to depend on the Observer, which depends on the observable object. When
the observable object changes, the Observer smooths out the changes so that the classes that depend 
on the Observer don't need to change


## Decorator

 * Like: Allows for a bunch of classes to all add a functionality
 
 * Learned: Works by making all classes call the Decorator. This decorator then calls a bunch of functions.
If a new function is added, we just call it in the decorator.

