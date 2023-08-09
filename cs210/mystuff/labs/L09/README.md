This is your second reverse engineering lab where you use gdb to solve some input questions.

***

**prog2.c**

To get started, build the program prog2 by typing `make` at the command prompt.

Then use gdb to disassemble the functions my_if, my_for, my_while, and my_switch.  

To do
this, start gdb (i.e., gdb prog2) and then at the gdb prompt type 
```
disas my_if
``` 
to see the
x86_64 instructions for the function my_if.  Be sure to understand what each instruction
is doing.   Do the same for the other functions.  Look at the source code in prog2.c, chapter 3.6 (Control) of CS:APP, and 
the [x86 Assembly Reference Sheet](https://sites.google.com/cs.duke.edu/computer-science-210-f21/resources?authuser=0#h.u2u4dqixzaon) on the course
website to help your understanding.

***
**mystery2**

The main task of this lab is to reverse engineer four functions in program mystery2.

Run the program mystery2 (type `./mystery2`) it prompts for some inputs and you need to figure out the
correct inputs by examining the instructions of the corresponding functions.  

The functions are
cleverly named f1, f2, f3, and f4.  You can run the program interactively (you enter answers on the command line)
for initial testing of your program.  

You can run each test individually by passing an integer 
argument to mystery2 (e.g., .`/mystery2 1` will test f1) or just run `./mystery` and it will progress
through all tests.

Ultimately for final testing and submission you need to 
place your answers in a file called:

1. solution_f1.txt
2. solution_f2.txt
3. solution_f3.txt
3. solution_f4.txt

These file should have your answers
on a single line with spaces between the integer values

For example, if the solution to f4 is "1 2 3", then the contents of `solution_f2.txt` should be:
```text
1 2 3
```

You can then use file redirect to execute the 
program with your solution file (i.e., `./mystery2 1 < solution_1.txt`).
***

**Final Local Testing Before Submission** 

Test your results by running
```bash
python3 test_kit.py mystery2
```

If your files are not properly named, the last line of the error will look something like this:

```bash
FileNotFoundError: [Errno 2] No such file or directory: 'solution_f1.txt'
```

Please follow the naming format we provided for you above, and make sure you have created all four solution files in your lab directory.

***

**Submission**

1. Please run the below before submitting to Gradescope to delete your compiled executables (NOTE: mystery2 is provided to you and SHOULD NOT be deleted):
```bash
make clean
```

2. Submit your solution .txt files to Gradescope via GitLab submission.
