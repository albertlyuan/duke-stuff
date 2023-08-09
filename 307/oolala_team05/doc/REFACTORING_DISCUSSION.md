## Lab Discussion
### Team
## Quentin MacFarlane qtm, Scott Tremblay sjt40, Albert Yuan aly11


### Issues in Current Code
* We have a couple of long methods
* We don't use abstraction very well
* We have a couple of duplicate code bits
* We have a bunch of magic numbers/strings

#### Method or Class
* Model.translate 
  * Is very long, has a bunch of magic strings that can be refactored to be more generalized.  
  * It also shouldn't use a super long switch statement

### Refactoring Plan

* What are the code's biggest issues? 
  * Our code isn't very polymorphic. So it'll be kind of difficult to generalize our code for the other 2 applications. 
  * We also have a lot of duplicate code and magic variables

* Which issues are easy to fix and which are hard? 
  * Easy: Variable renaming, removing static variables, method extraction/simplifying, removing duplicate code, exception handling
  * Hard: Making our code polymorphic 

* What are good ways to implement the changes "in place"?
  * Test our code often
  * Add methods rather than modify existing methods




### Refactoring Work

* Issue chosen: Fix and Alternatives

  * issue: Our translate function in model is very long and not polymorphic
  * solution: we will use method reflection to call our command methods. this will help make our code polymorphic, and 
  make it easier in the future to add/modify new commands. 