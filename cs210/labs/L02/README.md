# Lab 02

## Objectives

* Familiarize yourself with arrays in C.
* Refresh your recursion skills in C.
* Perform some basic string manipulation in C.

---

### Preliminaries

For this lab, first follow the same steps as in **Lab 01** (repeated here),
which you will need to perform for all C coding labs:

1. Fork this repository and mark it private.

2. Clone your newly forked repository into a directory on your OIT 210 container
   or the directory accessible to your local container.

3. Use Visual Studio Code (VSCode) to create your files.

4. Use `make` to compile the programs. 

    - You could also build them one at a time by specifying the program name as
      a `make` target. For example, type `make ring` to build the `ring` program
      (see below). Look into `Makefile` for more details on the targets and the
      commands `make` uses to build them.

5. Run each program locally (on your container) to check for correctness.

### Overview

This lab involves three small programs: `ring`, `strings`, and `recurse`. Every
program must have a `main()`. For `ring` and `recurse`, we give you `main()` in
the files `ring.c` and `recurse.c`, respectively.  Do not modify these files. It
is optional to view these files.  You can see that they illustrate some details
of handling command line arguments. We will talk about that later. For
`strings`, we also give you a bare-bones file, just to avoid errors with the
`Makefile`. You will, however, have to basically write this file from scratch.
More about this later...

Your mission for the `ring` and `recurse` programs is to implement a specified
function in a separate file.  Put your code in the files `ring_student.c` and
`recurse_student.c`.    Then you can build each program by compiling its
`main()` source file and the file with your function separately, and link them
together. The `Makefile` and `make` command do this for you if you type `make
ring` or `make recurse`.  Again, do not modify or submit the files containing
`main()` for these programs.  We may replace these files with different versions
for autograding.

For the third program, `strings`, we essentially ask you to write the entire
program from scratch. It is easy because `strings` takes no arguments on the
command line, so its `main()` takes no arguments and there is no need to handle
or parse the command line.   However, `strings` must read input from `stdin`
safely.   You should clone the [little C
programs](https://coursework.cs.duke.edu/chase/little-c-programs) repository and
look at `stdin.c` for an example of how to do this.  The comments explain why it
is so important to do it right.

The `main()` for any C program should return a proper value.  If the program
succeeds then the proper return value from `main()` is `0` or the predefined
`EXIT_SUCCESS`.   Gradescope autograder assumes your program failed unless
`main` returns `EXIT_SUCCESS`.

For `ring` and `strings`, take care that your program allocates array space for
its strings and does not overflow the space.  Also, take care to terminate every
result string by putting a null character (terminator) after the last byte of
the string.

It is important to know that every C `char` occupies one byte of memory.  A
`char` has an integer value and can be accessed as an integer.  The null
terminator is a `char` value of `0`, also indicated as `'\0'`.

### `ring.c`

The point of this little exercise is for you to get more comfortable with
working with arrays, strings, and characters in C.  You should see the `ring.c`
file in your forked repository.   Most of the code you need for this exercise is
already there, written for you.   Don't change it!  Your mission is to fill in a
couple of functions in a separate file, `ring_student.c`:

1. `doEncode()` and
2. `doDecode()`.


These functions encode and decode an input string, using a simple [substitution
cipher](https://en.wikipedia.org/wiki/Substitution_cipher) such as a [Caesar
cipher](https://en.wikipedia.org/wiki/Caesar_cipher). The idea is simple. The
input is limited to alphanumeric characters.   In C, each `char` value is a
small integer.  You are given a mapping table (the `encode` array).  For an
input character with integer value `i`, `encode[i]` gives the `char` value to
substitute for that character in the ciphertext.   (In a Caesar cipher the
`encode` substitution is given by a simple shift, as in encoder/decoder ring
toys for children.)

These functions take four arguments each.   The first two arguments (the encode
array `encode` and the input string `str`) are constant character arrays.  The
type keyword `const` means that the function does not change them.   The third
argument (`res`) is the character array that stores the result of the encode or
decode.  Your code builds the output string in place in `res`.  We also refer to
`str` and `res` as the input and output *buffers* respectively.

The last argument (`len`) is the length of the input buffer or output buffer in
bytes. That means that the input string and output string must be less than
`len` characters to avoid overflowing the buffer.   The input is
null-terminated: the string of input characters is terminated with the null zero
character `'\0'` whose value is `0`. Additionally, the input may be followed by
a  a linefeed `'\n'` just before the null terminator, indicating the end of the
input line.   These special characters are not input characters: don't translate
them.  For each input character (before the linefeed or null terminator),
translate it and place the translated output character in the `res` buffer.

**Hint:** Be careful when working with null terminating characters! You should
always make sure that your strings are properly terminated and you do not
overflow either buffer: do not read past the end of the input buffer or write
past the end of the output buffer.  

The `ring` program follows a standard design pattern for Linux/Unix utility
programs.  It reads from `stdin` and writes to `stdout`.  Any error output
should be directed to `stderr`.  Don't generate any superfluous output on
`stdout`.   An argument switch `-d` tells it to decode (encoding is the default
behavior).  That means you can pipe the output of an encoder process to the
input of a decoder process, type whatever legal input you want (alphanumerics
only), and see the original input echoed back.  E.g.:

```bash
./ring | ./ring -d
<type input>
```

You can also use redirection to encode and decode files, as described for
`strings` below.

### `recurse.c`

Fill in a recursive function in `recurse_student.c`. The function takes an
integer argument `n` (converted in `main()` from the command line) and computes
the following:

```c
f(n) = 1  // n <= 1
f(n) = f(n-1) * (n + f(n-2))  // n > 1
```

Here is a simple terminal example to test `recurse.c`:

```bash
./recurse 3
```

The output should be:

```bash
12
```

---

### `strings.c`

In this exercise, you create a `strings.c` program that prompts the user for two
strings, one after the other, and then produces a new string that is the
concatenation of the two input strings.  You write `strings.c` from scratch.  We
give you a bare-bones source file to avoid unnecessary `make` errors.

You should use `fgets()` to read each input line, and you may use `sscanf()` to
match the input strings, following the model of `stdin.c` in Little C Programs.
Prompt the user for a first input line by printing to `stdout` (e.g., with
`printf()`) `"Enter string 1.\n"`, and for a second input line by printing
`"Enter string 2.\n"`. Read the input after each prompt.

Your program should allocate `char[N]` arrays as buffers to store the strings.
Use `"%32s"` format specifier for `sscanf()` to ensure that the input strings
are less than 32 characters in length.   You may discard any input beyond 32
characters. Note: `sscanf()` stops string scanning for `%s` at any space (' ')
or other whitespace in the input, including the linefeed ('\n') at the end of
the line, if any.   So it does what you want, but if you type input with spaces,
it scans only the first word.  We test only inputs with no whitespace.

The result string is a concatenation of the two input strings.   By
*concatenation* we mean that it is a new string whose value is the first string
followed by the second string.   You define a new char array that is big enough,
copy the characters, and handle the null terminators properly.  

Then: print the first string, the second string, and then the result of the
concatenation, using the following format: `"%s, %s, %s\n"`.

If you write your code right, you can also test it by reading strings from a
file, using file redirection.  We provide you with some example input files.
E.g.:

`./strings < strings_input_1.txt`; `./strings < strings_input_2.txt`, etc. Feel
free to also create your own text files!

**Important note**: You should not use **any** string library functions to
operate on the strings after input. For example, using `strcat()` or `strlen()`
is not allowed here.  Just use simple C code to store the strings in array
variables and process them character by character.

**Important note**:  When you type the program name to test it, be sure you
prefix it with `./`.  That says to run the `strings` program that is listed in
the current directory, as opposed to some other program file called `strings`.
If you forget, it may run a system utility called `strings`, which is
interesting but does something different. You can try it by running `strings
strings` after building your program.  What does this utility do?  Find out with
the command `man strings`.

Here's an example of running your `strings` on the command line, with user input
lines marked by a leading `#`:

```
# ./strings
```

Output should be:

```
Enter string 1.
# lions
Enter string 2.
# bears
lions, bears, lionsbears
```

---

### Local Testing and Submission

1. Run `make` to compile your program(s). Changing your program's C code will
   not have any effect until you save your changes and recompile/rebuild.

2. Run the local Python test script by running the following on the Unix command
   line:
   ```bash
   python3 test_kit.py ALL
   ```
   If all tests are passing, they should also pass on Gradescope. If a test is
   failing, you can see exactly which one and find its corresponding input file
   in the `tests` folder for further debugging.

3. You can also test individual components:
   ```bash
   python3 test_kit.py TEST_SUITE_NAME
   ```
   where `TEST_SUITE_NAME` is either `ring`, `recurse`, or `strings`.

   It is important that the output of your program matches exactly what is shown
   in each of the descriptions of the programs above (this are also provided in
   the `tests/*_expected_*.txt` files).

4. Add, commit, and push these files to your git repo:

   * `recurse_student.c`
   * `ring_student.c`
   * `strings.c`
    
   **Note:** You can add multiple files by appending the file names after each
   other in the `git add` command:

   ```bash
   git add <file1> <file2> <file3>
   git commit -m "Added three files."
   git push
   ```

   In future labs, you will be responsible for typing the commands to add,
   commit, and push your files.

   ```bash
   git add recurse_student.c strings.c ring_student.c
   git commit -m "Finished recurse, strings and ring."
   git push
   ```

   **Note**: The commit message, which comes after `-m`, can be whatever you
   want. It should describe the contents of what changes you are uploading to
   your Git repository.

5. Submit your completed lab to Gradescope via the GitLab submission button.
