This is your fifth reverse engineering lab where you use gdb to answer some questions.
This lab gets more involved as it deals with examining data and memory layout for strings,
arrays of structs, lists, and the stack with an example buffer overflow problem.

The main task of this lab is to reverse engineer two functions in program mystery5. There are also two more optional functions in mystery5 to reverse engineer.

To help, we have provided a program prog5.c which has similar or identical functions
so that you can inspect their assembly translation.

---

**prog5.c**

To get started, build the program prog5 by typing `make` at the command prompt.
Then use gdb to disassemble the functions count_chars, set_if_in_array, clear_if_in_list 
and my_bug.  Look at their source code in prog5.c.

Use the full power of gdb to explore the program, memory, etc.  

You can display the
string passed to count_chars by using the gdb command
```bash
(gdb) x/s <ADDRESS>
``` 
where `<ADDRESS>` is the memory address of the string.

In my_bug, note the location of the local `foo` and `array` variables on the stack
and observe how they are initialized in the disassembled code. Try running my_bug
and entering various strings to simulate a buffer overflow. See section 3.10.3 of
the CS:APP textbook if you'd like a reminder about how it works.

---

**mystery5**

The main task of this lab is to reverse engineer the first two functions in program mystery5. You may also reverse engineer the two optional functions.

Run the program mystery 5:
```bash
./mystery5
```

It prompts for some inputs and you need to figure out the
correct inputs by examining the instructions of the corresponding functions.

For this lab, the functions
are identical to those in prog5.c except for my_bug, which has a differently sized
array and different initial value for `foo`. mystery5's main is definitely different from that of prog5, and naturally you don't have access to any mystery5 source code.


You can run the program interactively (you enter answers on the command line)
for initial testing of your program.  

You can run each test individually by passing an integer 
argument to mystery5 (e.g., `./mystery5 1` will perform the first test) or just run `./mystery5` and it will progress
through all tests in order.

Ultimately for final testing and submission you need to 
place your answers in files called:

1. solution_f1.txt
2. solution_f2.txt
3. solution_f3.txt
4. solution_f4.txt 

where f1 corresponds to count_chars,
f2 to my_bug, f3 to set_if_in_array, and f4 to clear_if_in_list. These files should have your answers
on a single line with spaces between the integer values.

For example, if the solution to count_chars is '1', then solution_f1.txt should be
```
1
```

Then you can use file redirect to execute the 
program with your solution file. For example, to test your solution for count_chars run: 
```bash
./mystery5 1 < solution_f1.txt
```
This is exactly how the test kit and autograder use your solution files.

---
**Final Local Testing Before Submission**

test your results by running
```bash
python3 test_kit.py mystery5
```

If your files are not properly named, the last line of the error will look something like this:

```bash
FileNotFoundError: [Errno 2] No such file or directory: 'solution_f1.txt'
```

Please follow the naming format we provided for you above, and make sure all four solution files exist in your lab directory.

---

**Submission**

1. Please run the below before submitting to Gradescope to delete your compiled executables (NOTE: mystery5 is provided to you and SHOULD NOT be deleted):

```bash
make clean
```

2. Submit your solution .txt files to Gradescope via Gitlab submission.
