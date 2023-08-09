# Journal : Improvement Analysis
### Albert Yuan


## Looking Back

### Practice of coding

#### What are three things you think about differently when coding because of this course?
1) Code readability
2) Minimizing duplicate code
3) Making methods/variables private/public

#### What is a coding habit you have changed for the better because of this course?
I definitely extract my methods more.

#### What is a feature from any of the exercise specifications you felt was made easier to implement by the code's design and why?
The maze exercise really helped me figure out how to generalize classes to a super class since all of them had
very similar methods that were just off by a little.

### Design issue

#### What trade-offs were you considering?
I noticed all the step functions had 3 basic steps but some solvers had extra steps before or after those steps.
I had to decided if I wanted to have methods that didn't do anything or have more duplicate code.

#### What alternatives did you consider?
Since the extra steps weren't always happening, I could either @override that solver's step function or add methods
in the solvers that didn't have the extra steps that were overwritten to do nothing.

#### Why did you make the final decision you did?
I chose to do the add do-nothing methods, since I already had to @override a lot of 
methods. 


### Actively working to improve

#### What is a coding habit you are still working on changing?
I'm still working on being able to make classes that are more dynamic and understanding
how to and when to add certain methods and functionalities to classes. 

#### What part(s) of the code did you spend the most time on?
So far I spent the most time refactoring code to remove duplicate code.
However after the maze exercise, I feel like I can now do it a lot faster since the hard part
was thinking about how to refactor. 

#### What is something not yet addressed that you want to know more about or do better?
I feel like everything that is brought up in class is something that I haven't thought about,
but is something that I realize is very useful to know.


### Transition Feelings

#### How have you felt having your code reviewed? Is the feedback clear and is it making a positive impact?
I've enjoyed the feedback from my TA mentor Ha. She's given me a lot of small and useful pointers about code formatting
and code design tips.

#### How has the act of refactoring your code felt? Are the goals clear and is it making a positive impact?
I think it's tedious to do, but at the end of doing it, I feel incredibly satisfied. It's really cool
how much cleaner my code can look after refactoring.

#### How have you felt thinking about and designing a program without actually writing any code?

* What was hard?
  * The hardest part is taking time to figure out what the written code does. There are many methods and many
  variable names that I need to familiarize myself with.

* What was easy?
  * For the most part (there are definitly exceptions), adding new functions has been pretty easy.

### Overall, how do you feel you are doing in the course?
Overall I feel like I'm doing fine in this course. The hardest part is definitely allocating time for
the project.

#### How could you improve your learn choices?
I could start the assignments a little earlier to make the workload on Monday
a little less chaotic.

#### How could the course be improved to better help meet your needs?
This isn't a big deal since I probably won't start the assignments until later, but the 
assignments could be posted earlier.

## Looking Ahead

#### Describe a good team experience you have had. What is something about that experience you can apply to create positive teams in the future?
I just played in an ultimate frisbee tournament with a bunch of our newer players and we placed 5th.
I tried to stay positive and calm at all times and provide as much feedback to the newer guys as one of the more
experienced players. I will try to apply that to the other teams that I'm a part of if I can.

#### Describe a bad team experience you have had. What is something about that experience you can apply to create positive teams in the future?
Me and 1 guy in a 4-person team were the only ones that did any work on a project.
I guess I'd ask them to do stuff since they might've had no idea where to start.

#### What are ways people can show they respect each otherâ€™s ideas and work?
By actually considering them and if they aren't good, providing a non-condescending reason why it might be wrong.

#### What are ways people can show they are committed to the team's success?
By doing work on time that isn't riddled with incorrect solutions.

#### What are ways people can be proactive in a team?
People can do their part and then if there's a next step, do it as well.

#### How can communication during meetings help or hurt your team experience?
Communication can help coordinate who is doing what and when.
It can also make people unhappy if people aren't being positive with their communication.

#### How can communication beyond meetings help or hurt your team experience?
It can help people solve problems like bugs more efficiently but also create confusion
if people are saying different things.

#### How can the size and timing of your GIT commits help or hurt your team experience?
GIT commits that are too big might cause errors since there might be changes that affect many things.
GIT commits that are done too soon might cause errors when people try to push since they'll constantly have to pull.
GIT commits that are done too late might lead to problems when people build code that depends on the uncommitted commit.