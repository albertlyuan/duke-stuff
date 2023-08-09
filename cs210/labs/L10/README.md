# Lab 10

## Objectives

- Learn about how the compiler turns C function calls into x86-64 assembly code.
- Solve the inputs to a mystery program by disassembling some functions and
  stepping through programs in gdb.

---

### `prog3.c`

To get started, build the program prog3 by typing `make` at the command prompt.

Then use gdb to disassemble the functions `leaf1`, `caller`, `myargs`, and
`my_recur`.

To do this, start gdb (i.e., `gdb prog3`) and then at the prompt type

```
disas caller
```

to see the x86-64 instructions for the function `caller`.  Be sure to understand
what each instruction is doing. Do the same for the other functions. Look at the
source code in `prog3.c` and chapter 3.7 of CS:APP (Procedures) to help your
understanding.

---

### `mystery3`

The main task of this lab is to reverse engineer two functions in the program
`mystery3`. There is also an optional third function to investigate.

Run the program `mystery3` (using `./mystery3`). The program prompts for some
inputs and you need to figure out the correct inputs by examining the
instructions of the corresponding functions. The functions are cleverly named
`f1`, `f2`, and `f3`. You can run the program interactively (you enter answers
on the command line) for initial testing of your program.  

You can run each test indidually by passing an integer argument to `mystery3`
(e.g., `./mystery3 1` will test function `f1`) or just run `./mystery3` and it
will progress through all tests. Ultimately for final testing and submission you
need to place your answers for each part in files called:

1. `solution_f1.txt`
2. `solution_f2.txt`
3. `solution_f3.txt` (Optional)

These files should have your answers on a single line with spaces between the
integer values and ending with a newline character.

For example, if the solution to `f2` is "1 2 3", then the contents of
`solution_f2.txt` should be:

```text
1 2 3
```

You can use file redirection on the command line to execute the program with
your solution file (i.e., `./mystery3 1 < solution_1.txt`).

---

### Final Local Testing Before Submission

Test your results by running:

```bash
python3 test_kit.py mystery3
```

If your files are not properly named, the last line of the error will look
something like this:

```bash
FileNotFoundError: [Errno 2] No such file or directory: 'solution_f1.txt'
```

Please follow the naming format we provided for you above, and make sure all
three solution files exist in your lab directory.

---

### Submission

1. Please run the below before submitting to Gradescope to delete your compiled
   executables (NOTE: `mystery3` is provided to you and **SHOULD NOT** be
   deleted):

   ```bash
   make clean
   ```

2. Submit your solution .txt files to Gradescope via GitLab submission.
