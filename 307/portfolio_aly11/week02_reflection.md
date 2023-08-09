# CompSci Reflection
## Albert Yuan

I joined this class the Friday before the breakout game was due.
I didn't think I was going to be able to create a working game, on such short notice
but after a long night's work, I did it. Although there is plenty of room 
for improvement, I think this is one of the programs I am most proud of. 
I had to learn javafx from scratch, think about how what classes to define, and
think about how I was going to implement features like the ball's movement.
Creating classes like this was something that was new to me. In CS201, where I
was first introduced to the concept of classes, we just learned how to use them to call functions
like in python. One thing I'm particularly proud of is figuring out 
how to simulate the ball's movement and bouncing. Using the animation clock, I made
it so that the ball's velocity was added to the current position. This velocity stayed 
constant until the ball hit something. Then depending on which side (up/down or left/right)
the x or y velocity would change.

One thing I failed to do well was implementing a non-buggy collision. To detect collisions, I checked
if the ball's position + ball's velocity equals an object's boarder. If it did the ball would 'bounce' by changing the
velocity. However, this meant that the ball wouldn't quite reach the object because the velocity changed before it was 
added to the ball's position. Also if the ball wasn't going exactly up/down or left/right, the ball would clip into the 
object because the diagonal length is longer than the x and y length.

Something that I learned that I think will be very useful in the future is extracting methods. Last summer at my 
internship, I noticed that my code was often very difficult to read even for me. I didn't really know how to remedy it
but when Professor Duvall showed how we could extract methods to make reading code a lot easier, it really clicked for 
me. I just thought that my coding style was going be messy forever but this technique helped me figure out how I could
clean up my code.

This week was also the first time I did pair programming for the first time. In 201, I never worked with people because 
it was hard to meet new people in Zoom meetings. My partner was Sam and I really enjoyed working with him. I played the
role of a naive coder who just spat out any and all ideas, and Sam as the experienced Senior critiqued my ideas and code 
to minimize bugs. 