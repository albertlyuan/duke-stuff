This is your first reverse engineering lab where you use gdb to solve some input questions.

***

**prog1.c**

To get started, build the program prog1 by typing `make` in the Terminal.

Then use gdb to disassemble the three functions math_64, math_32 and swap_long.

To do this start gdb (i.e., `gdb prog1`) and then at the gdb prompt type `disas math_64` to see the x86_64 instructions for the function math_64. 

Be sure to understand what each instruction is doing. 
Do the same for math_32.  

Notice the difference between the instructions in these two functions? Look at the source code in prog1.c to help 
your understanding.

We include a swap_long function also just so you can see the same things we cover in class.
It may be interesting to change swap_long to swap_int or swap_short by changing the data types
(of course you will have to change things in main as well.)

***
**mystery1**

The main task of this lab is to reverse engineer three functions in program mystery1.

Run the program mystery1 (type `./mystery1`) it prompts for some inputs and you need to figure out the
correct inputs by examining the instructions of the corresponding functions.  

The functions are cleverly named f1, f2, and f3.  You can run the program interactively (you enter answers on the command line) for initial testing of your program.  

You can run each test indidually by passing an integer 
argument to mystery1 (e.g., `./mystery1 1` will test f1) or just run `./mystery` and it will progress
through all three tests.

Ultimately for final testing and submission you need to 
place your answers in files called 

1. solution_f1.txt
2. solution_f2.txt
3. solution_f3.txt

These file should have your answers on a single line with spaces between the integer values. 
For example, if the solution to f2 is "10 100", then the contents of `solution_f2.txt` should be:
```text
10 100
```

Then you can use file redirect to execute the program with your solution file (i.e., `./mystery1 1 < solution_f1.txt`)

***

**Final Local Testing Before Submission** 

Test your results by running:

```bash
python3 test_kit.py mystery1
```

If your files are not properly named, the last line of the error will look something like this:

FileNotFoundError: [Errno 2] No such file or directory: 'solution_f1.txt'

Please follow the naming format we provided for you above, and make sure you have created all three solution files in your lab directory.

***

**Submission**

1. Please run the below before submitting to Gradescope to delete your compiled executables (NOTE: mystery1 is provided to you and SHOULD NOT be deleted):
```bash
make clean
```

2. Submit your solution_f1.txt, solution_f2.txt, and solution_f3.txt files to Gradescope via GitLab submission.
