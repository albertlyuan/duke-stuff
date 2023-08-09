The purpose of this lab is to learn how to use gdb, a powerful debugging aid -- one we will use 
extensively later this semester.

This directory contains a program called debug1.c and the corresponding Makefile.
There are four functions defined in debug1.c (f1, f2, f3, f4) and one global array (array1)

We'll use GDB to examine the state of the program at different points during its execution.
We will use only the command line version of GDB, it has the ability to display source code to 
track where the program is in its execution using the -tui option.  To see source code within GDB programs need to be compiled with the -g option. GDB still works without this (and you will use that soon!).

**Part 1.**

1. First, build the program debug1 by typing make
2. start gdb with the -tui option (gdb -tui debug1), if you start gdb without -tui you can just type tui enable at the
gdb prompt. **NOTE** : The TUI window for gdb often becomes messed up
during the course of execution. It might show that you're executing on
multiple lines or you might see duplicate lines of code erroneously. To
fix this, simply type `tui disable` and then `tui enable` to restart
tui.
3. set a breakpoint at main
4. begin execution of the program by typing `r`
5. print array1
6. set a breakpoint at f1. Continue execution of the program until you
   reach f1.
7. use `s` to execute the lines of code in f1 until it returns to main
8. use `s` to step into the call to f2, and through f2 until it returns to main
use `n` to stop over the call to printf
9. step into f3, and step for a few lines of execution, print array1 to see how it is changing.
10. set a breakpoint at the next printf -- use b printf or b 37 (which stops at 
line 37 in the current file, b printf will stop at every call to printf) Note you are still in the function f3
11. use c (continue) to continue executing until the next breakpoint. If
    you used `b printf` in the previous step, you might notice that
    you're now inside the `printf` function and can't see much of
    anything. Type `bt` to show your stack trace. Notice that the top
    entry shows that you're in `printf`, which was called from `main`.
    You can execute until the completion of the call using `finish`. Now
    you're back in `main`.
12. print array1 to display its values
13. now use the x command to display array1 (e.g., x/10dw array1)
14. stop over the call to printf and then step into f4.
15. step a few lines of code in f4, examing array1 to see how it is changing.
16. use finish to continue execution until f4 returns.
17. print array1 to see it's final values.
18. type c to continue exection and exit the debugger.

If you mess up, it's OK, you can just restart at the beginning by executing the gdb command at the terminal prompt.
You can either try to exactly execute above or use breakpoints to stop at the function where you left off.
Experiment with different breakpoints and examining variables.

If you ever need to enable/disable any of your breakpoints, you might
find the following commands helpful:
- `info b` - lists the current breakpoints you have
- `enable <breakpoint number>` enable the breakpoint with that ID number
  (you can find ID numbers with `info b`
- `disable <breakpoint number>` disable the breakpoint with that ID
  number

The gdb info command is sometimes useful for exploring data structures and binaries. For example,
right after start gdb you can type info functions and it will display the declared functions. You 
ignore the items after the "Non-debugging symbols:" output line.

**Part 2.**
Now you need to find variables and values in an executable that you do not have the source code for.
The file debug2 is a binary with debugging symbols included. Like debug1.c this program has the 
same four function names and global variable named array1
as debug1, however some things in the program are different.  You need to use gdb to answer the following
questions, and enter the answers into gradescope.
1. What value is the argument a and return value of f1?
2. What values are passed in a and b to f2, and what is its return value?
3. What are the initial values in array1, and what is the return value of f3?
4. What argument is passed to f4?
5. What are the final values in array1?

Submit your answers in gradescope
