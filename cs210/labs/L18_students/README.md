# Lab 18

## Objectives

The main objective of this lab is getting you comfortable with direct-mapped
caches. This lab will have two main tasks:

- You will have to compute, by hand, the number of *tag*, *set index*, and
  *block offset* bits for a specified cache. This is a bit of math that will
  prepare you for the second part of the lab, which involves some programming
  work.

- You will have to complete the simple direct-mapped cache simulator, which is
  in the file `casim.c`.

---

### Overview

This lab will give you some practice when it comes to calculating the number of
bits for the *tag*, *set index*, and *block offset* for a specified cache. You
are free to use a calculator or write a C program to check your work.

Calculation of these values may very well be on your next exam (read: will
almost certainly be on your next exam) so make sure to understand the answers of
this lab and the way to get to the solution well.

You will also gain some experience when it comes to the implementation of a
simple direct-mapped cache simulator, which you can find in the file `casim.c`.
This is the second part of this lab, and it will be explained in more detail in
later part of this `README`.

---

### First Part

Your first task is to calculate the number of `tag`, `set index`, and `block
offset` bits for certain cache specifications. You will put the number of bits
for each part in the provided text files `q1.txt`, `q2.txt`, `q3.txt`, and
`q4.txt`. This assignment follows the notation used by the CS:APP book. We
recommend you read sections 6.4.1 and 6.4.2 before starting the lab. For
reference, the number of tag bits corresponds to *t* in the book, the number
of sets index bits corresponds to *s* in the book, and the number of block
offset bits corresponds to *b* in the book.

The following are the cache configurations you will be working with:

- `q1.txt` - 32-bit addresses, 1 KB direct-mapped cache, and 32-byte blocks.

- `q2.txt` - 64-bit addresses, 1 KB direct-mapped cache, and 32-byte blocks.

- `q3.txt` - 32-bit addresses, 512 B direct-mapped cache, and 32-byte blocks.

- `q4.txt` - 64-bit addresses, 512 B direct-mapped cache, and 32-byte blocks.

In each text file, please format your numbers with spaces in between:

```text
tag set_index block_offset
```

If the number of tag bits is 20, number of set index bits is 6, and number of
block offset bits is 6, then the text file would look like this:

```text
20 6 6
```

---

### `cachechecker`

This is a program written for you to check your answers for the first part of
the lab. This program will also be run on Gradescope to give your your final
score for this part of the lab.

You can run the program by giving it command-line arguments of the address size
(in bits), cache size (in bytes), and block size (in bytes). Below is an example
for `q1.txt`:

```bash
./cachechecker 32 1024 32
```

After you run `cachechecker` in this way, the program will ask you to input the
three numbers for tag bits, set index bits, and block offset bits.

After entering the three numbers, the program will tell you if your answers are
right or wrong.

This program will work with all reasonable combinations of address size, cache
size, and block size and will **only work for direct-mapped caches**. If you
need more practice, feel free to create your own combination of cache specs and
try to work out the number of bits for the tag, set index, and block offset and
test your answers with this program.

---

### Local Testing for First Part

1. Fill out `q1.txt`, `q2.txt`, `q3.txt`, and `q4.txt`.

2. Run the local Python testing script by running the following on the Unix
   command line:
   ```bash
   python3 test_kit_answers.py cachechecker
   ```
   This will test only the first part of the lab.

3. If all the tests are passing, you should proceed to the second part of the
   lab.

---

### Second Part

You must complete two functions, `get_tag` and `get_index`, and complete the
body of the `while` loop that performs a lookup in the cache structure to
determine if the access is a hit or miss.  Your loop body should increment the
value **hits** or **misses** based on the result of the lookup. See the `TODO`
comments in `casim.c` for where to put your code.

You can use the function `log2(x)` to get the log base `2` of `x`. You should
not have to write many lines of code for this lab.
 
A helpful debugging method is to use a block size of `16` and a cache size of
`256`. This makes it very easy to use `printf` to see index and tag values in
hex (I will let you think about why that is...)

---

### Local Testing for Second Part

1. In order to test the second part (your implementation of the simulator), you
   first should run `make` to compile `casim.c`.

2. Assuming there are no errors with the compilation, you should now check
   `settings.json` file, under the `test_suite` called `casim`. For each of the
   test cases, the strings in `args` are all of the command-line arguments, in
   order. For example, a line in the file `settings.json` that looks something
   like the following is what you ultimately want:
   ```json
   { "desc": "cols 16 1024",  "args": ["16", "1024", "col_trace.txt" ],  "diff": "normal", "infile": "", "outfile": "",  "valgrind": false, "points": 4.165 },
   ```
   The description (`desc`) is the first field, and you don't have to care about
   it. It is just used for internal bookkeeping. The second field is `"args"`,
   and it shows you exactly what the command-line arguments are for this test
   case.

3. You can run the simulator on the command line in the following way:
   ```bash
   ./casim [blocksize] [number of sets] [tracefile]
   ```
   An example of this would be something like
   ```bash
   ./casim 16 1024 col_trace.txt
   ```
   All three arguments are required. A "trace file" is just a file containing a
   list of memory accesses. We provide two trace files for you to use:
   `row_trace.txt` and `col_trace.txt`.

4. You can test the simulator fully by running the Python tester in the
   following way:
   ```bash
   python3 test_kit.py casim
   ```

---

### Final Testing and Submission

1. You should go through all the testing steps described above, for both the
   parts of the lab (the written piece and the simulator). A quick way of doing
   this is to just run, on the command line, the following:
   ```bash
   python3 test_kit.py ALL
   ```
   If the output here looks good (there are no errors reported), your lab should
   be correct.

3. Add, commit, and push the files to your Git repo:
   
   `q1.txt`, `q2.txt`, `q3.txt`, `q4.txt`, and `casim.c`.

4. Submit your completed lab to Gradescope via the GitLab submission button.
