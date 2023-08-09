# Lab 08

## Objectives

* Get some practice with reverse engineering.
* Get better at using `gdb`.

### Overview

This is your first reverse-engineering lab where you use `gdb` to solve some
input questions. There are three source-code files in this lab. `prog1.c` is the
first file you should be looking at. You will have the source code for this
file, and you're supposed to use it as practice, before tackling the questions
about the other two files -- `mystery1` and `mystery2` -- which are just in the
form of an executable (you do not have the source code for them). Do note that
questions related to `mystery2` file are **optional** -- meaning not necessary
for full points on this lab. We do encourage you to still play with it, since
  it's a bit more challenging than `mystery1` and will serve as good practice.

---

### `prog1.c`

To get started, build the program `prog1` by typing `make` in your terminal
window. Then use `gdb` to disassemble the three functions from this file:
`math_64`, `math_32` and `swap_long`.

To do this disassembling, you should first start the debugger, provide the file
`prog1` as input to it, and then execute `disas math_64` in the debugger prompt,
to see the x86-64 instructions of the given function. Spend some time here
trying to understand what the function is doing...

Now do the same for `math_32`.

Are you noticing the difference between the instructions in these two functions?
You are free to look at the source code in `prog1.c` to help your understanding.

We included the third function -- `swap_long` -- so you can see the same things
we cover in class. It may be interesting to change `swap_long` to `swap_int` or
`swap_short` by changing the data types (of course you will have to change
things in `main` as well).

---

### `mystery1`

The main task of this lab is to reverse engineer three functions in the program
`mystery1`.

Start by running the program. You can do this by typing `./mystery1` in your
terminal prompt. The program should prompt you for some inputs and you need to
figure out what the correct inputs are by examining the instructions of the
corresponding functions.  

The functions are cleverly named `f1`, `f2`, and `f3`.  You can run the program
interactively (you enter answers on the command line) for initial testing of
your program. Naturally, you will probably need to disassemble all these
functions, by executing, for example, `disas f1` in the debugger prompt, in
order to finish this lab.

You can run each test individually by passing an integer argument to `mystery1`
(e.g., `./mystery1 1` will test `f1`) or just run `./mystery1` and it will
progress through all three tests.

Ultimately for final testing and submission you need to place your answers in
files called 

1. `solution_f1.txt`
2. `solution_f2.txt`
3. `solution_f3.txt`

These file should have your answers on a single line with spaces between the
integer values. For example, if the solution for `f2` is `10 100`, then the
contents of `solution_f2.txt` should be:

```text
10 100
```

Then you can use file input redirection to execute the program with your
solution file. For example: `./mystery1 1 < solution_f1.txt`.

---

### `mystery2` (Optional)

This is the optional program that you DO NOT need to work in order to get full
credit for the lab. There is only one function (besides `main`). Your job is to
figure out what it does and what inputs will result in the desired output.

We will not tell you the function name but when you disassemble `main`, there
are function names next to the function call instruction. That is one way you
can get the function name to disasssemble.

You should put the solution in the `solution_ec.txt` file.

Run `./mystery2` to test or `./mystery2 < solution_ec.txt` to test with
redirection.

---

### Final Local Testing Before Submission

Test your results by running:

```bash
python3 test_kit.py mystery1
```

If your files are not properly named, the last line of the error will look
something like this:

```bash
FileNotFoundError: [Errno 2] No such file or directory: 'solution_f1.txt'
```

Please follow the naming format we provided for you above, and make sure you
have created all three solution files in your lab directory.

---

### Submission

1. Please run the below before submitting to Gradescope to delete your compiled
   executables. **NOTE: mystery1 is provided to you and SHOULD NOT be deleted**.
   
   ```bash
   make clean
   ```

2. Submit your `solution_f1.txt`, `solution_f2.txt`, `solution_f3.txt`,
   `solution_ec.txt` (optional) files to Gradescope via GitLab submission.
