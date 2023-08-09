# Journal : OOGA Team Review
## 09
## Albert Yuan
## 12/6


### Contributions and Roles

* Describe your role(s) and your satisfaction
  * I created all classes related to reading a config file and then creating game specific objects. 
  Examples include the pieces, the end condition, and the powerups. I also wrote most of the end conditions and
  the end game view.
  I think overall I did a very good job. My code was very generalizable, and I thought my design was smart. 
  My boardBuilder was able to take in any file and spit out objects if the file was valid, or a caught
  exception if the file wasn't valid. I also completed my code in a timely manner and with proper testing
  and very little bugs.

* Describe your team mate(s) role(s) and their satisfaction

    * tarun - Didn't really do his part. He signed up to built the model but ended up building none of it.
    He also signed up to build the endConditions but took 2 more days than he said he'd take and then 
    showed up with code that wasn't well-designed. His code often had a ton of commented out method, 
    hardcoded values, and usually never integrated well with our code. It often looked like he didn't 
    spend time to read the rest of our code to see how his changes should mesh with ours. He did build
    the powerups.
    
    * gordon - By far the MVP of our team. Not only did he help complete the basic view in 2 days, he also
    took charge on designing the entire model and editor mode. Words can't really describe the amount of 
    work he did since he was always either a part of the design or the discussion of a design.  

    * richard - He did his part. He helped Gordon complete the basic view in 2 days, and then handled 
    any problems/questions relating to the view. He also handled the timer stuff.

    * sam - He did his part. He helped Gordon build the model, specifically helping with the logic of 
    of the chess moves. 

    * luis - He did his part. He built the config writers/downloaders, the login screen, and helped with the
    boardbuilder stuff. He really bounced around the entire project doing whatever odd jobs there were to do.

* Roles beyond coding you took on and their benefit
  * I'd like to think I took a leadership role when it came to the configuration stuff. I often consulted
  with the Model people and asked what information their objects needed, and also consulted Luis for his
  thoughts as well. I also took the lead in designing all the config classes as well

* People who generally took on work "between assigned responsibilities" and outcome for the team
  * In my opinion this was everyone except for Tarun. Since he didn't do much until the end of the 
  project, we found ourselves working "between assigned responsibilities" to make up for his lack of 
  production.


### Team Events

* Describe when you felt the most satisfaction
  * The most satisfying part was working on the endconditions because it was cool to see our chess
  game accept a radically different way of winning. It was also really satisfying to see the
  end game view pop up showing the correct outcome of the game

* Describe when you felt the team made the biggest leap ahead
  * the team definitely took the biggest leap ahead when we finalized our design on how determine
  how pieces can move. We used to have a movefactory that I'm frankly completely unfamiliar with. 
  Gordon and Sam then thought of Move classes which fixed our problem of not being able to implement 
  castling and other weird moves.

* Describe way(s) the team has worked well
  * I think our team worked well when it came to understand multiple areas of the project. In previous
  projects, I often had little idea of how a certain part worked. In this project, I felt like most of
  us had a good understanding on how most of the program worked, which meant that bug fixing was a lot easier
  since you could bounce ideas between multiple people
  

* Describe problem(s) with the way the team worked together
  * We had a tough time communicating. We'd make large refactoring changes to important parts of code, 
  and then only when merging would someone realize that their edits were for a depreciated version of 
  code. For example, I was working on changing how checkmates work and then as I was merging, I saw 
  that our board.movePiece method no longer returned a Turn object, but instead return a list of Piece 
  objects, which made my changes not work since I utilized the Turn object.


### Learning Opportunities

* Your biggest strength as a teammate
  * My biggest strength is that I do my work in a timely manner. When people ask me to do something,
  I get it done and I get it done efficiently.

* Your biggest weakness as a teammate
  * I think for this project, it took me a while to figure out what was going on. I think this was a
  reason why my code at the start was messy, and why my code isn't the easiest to read. I think making my
  code cleaner is something that I have the ability to do, but the question is whether or not I have the 
  discipline to do it.

* Something you have learned about team collaboration/communication
  * I learned that communication is very important. I took for granted my previous group's ability
  to constantly text updates about what changed.

* A specific thing you can do to ensure successful team outcomes in the future
  * I will ensure that communication happens. When large changes are merged, we need to have texts or 
  some way of effectively notifying everyone of the changes