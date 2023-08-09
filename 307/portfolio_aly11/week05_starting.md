# Journal : Starting
#### Albert Yuan
#### 9/27/2021


## Starting a Big Project

### First design challenge
The first design challenge was deciding how we could make it so that only the view calls the model. 
We had trouble figuring out how we were going to process the user inputs (via the command line) and the file
inputs. We knew that this had to be done in the view, then get sent to the model, and somehow return information 
about the input. The last step made us think that we were going to call the view from the model.
However, we figured out that we could just alter the turtle states (stored in model) in model and then 
in view, just update the turtle and lines to match the new turtle states.

### First piece of code
The first piece of code was just the setup function in view. It helps to see what's going on when we will
likely have bugs, so by building all the visual stuff for the turtle first, we could see if the "fd" function
really worked.

### Hardest part or biggest obstacle
The hardest part was definitely figuring out how to make the view call the model as stated before.
Besides that, we are currently having trouble animating the turtle. The problem is that currently we are
reading 1 command, then executing it (which just changes values in model), and then calling startAnimation() which
checks the values in model and updates the turtle to match them. It's supposed to end animation when the turtle is done 
performing the command, but it just executes the next command before it finishes. 

### First coding session insights
I started on Thursday afternoon and spent about 2-3 hours on the setup and starting bits. At the end I had a working fd
command and that's it. I think it was an overall success since I accomplished what I wanted to accomplish, but that's
also because it didn't work on anything particularly complex or unfamiliar. 

### Successful outcome
A successful outcome has a GUI looks similar to the given examples on the project page, all the 
functions are added and work, there's proper testing created, and the code is clean and has no duplicate code.

### Worst possible outcome
The worst possible outcome would be a dysfunctional app. When it runs, it either works incorrectly or errors out. 


## Ethics Essay
What happens when AI/robots replace humans doing a specific job 
(such as factory workers, truck drivers, or radiologists)?
### Ethical questions regarding AI/Robots
I think from purely human advancement perspective, it's a good thing. We're automating jobs that can be literally 
perfected in ways that humans can't do. I'm not familiar with self-driving cars, but I'm pretty sure it's impossible
for them to crash. This means that overall, processes will be faster, and theoretically, we can allocate resources to 
advance humanity in other ways. For example, instead of paying a guy to drive a truck, we can pay him to do something
else that robots can't do. 
However, the reality is that this will just put people out of work. I think one of the worst things about our current
society is how greedy everyone is. It's understandable since evolution favors the survival of the fittest, so everyone
is always trying to make themselves the 'fittest'. Due to greed, robots will just be a way to save money for large
cooperations. It'll be a large initial investment but down the line they won't have to pay salaries, benefits, and any
lawsuits for human errors. This money won't get allocated to pay people to do other things, but instead just pile up in 
the executives' bank accounts. 
