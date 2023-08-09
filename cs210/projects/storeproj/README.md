# Project 01 -- Store Project

## Objectives:

* Gain familiarity with data representation at the level of bits.
* Gain practical knowledge of bit manipulation in C.
* Recognize differences in C datatypes and use C type casting.
* Gain practice using the C compilation worflow.

You will interact with a custom data representation for keeping track of things
in a store, implementing functions using bitwise operators, and arrays.

**Important note**: A lot of the instructions for this project can be found in
the the comments of the source-code files themselves. This `README` will give
you a broad summary of what the project is about and what you need to do.  For
specifics, you should refer to the source-code files that this `README` cites.

---

### Format:

This project uses a custom data representation of *aisles* in a store and
requires you to perform many bit manipulation operations. `aisle_manager.c` and
`store_manager.c` are the files that contain skeleton functions for you to
complete, each with some comments which describe the function's desired
behavior.

As you work through the lab, you are encouraged to test each function one by
one as you go (for information on how to do this, please see **Checking Your
Work** for instructions on how to just test one function). **We also encourage
you to use previously implemented functions and previously defined macros to
help you solve future functions!**  Please post on Ed if you are unsure whether
or not it is ok to use something.

---

### `aisle_manager.c`

Start by reading the description of an aisle's data representation at the top of
`aisle_manager.c.` Make sure you fully understand the layout of the
representation before continuing. If you have any questions, please post on Ed
or come see us in office hours.

Once you understand the data representation, you can begin to implement the
skeleton functions in `aisle_manager.c`. Do not be discouraged by the number of
functions! They build gradually in level of difficulty, and many of them are
similar to each other. Here's one recommended way of working through the file:

1. Start by filling in the macros at the top of the file with various bitmasks
   to be used later. These should be quite simple to fill out. The main thing
   you will need to complete this step is a solid understanding of hexadecimal
   numbers. If you cannot complete this step, you should definitely seek help
   either in office hours or on Ed.
2. Continue by implementing the `get_*` and `set_*` functions. Each of these functions
   will have comments above the function definition which will help you
   understand what the function is supposed to do, what arguments it takes,
   what it's supposed to return... Please make sure to read these comments
   carefully before starting the implementation itself. **Note**: Often, it
   will be the case that you can directly call a function you just implemented
   inside one of the subsequent functions. You should do this! It is good
   practice and it will make your code shorter, easier to read, and most
   importantly, easier to get right. **Another note**: When working with bit
   operations, make sure you understand what your data types are and what their
   sizes are. This is crucial in many use cases. In this project, however, it
   will probably be the most important when it comes to *bit shifting*. For
   example, think about what you should do when it comes to shifting small
   numerical types (for example, `short`'s) left or right. In particular, think
   about the sizes of data types involved, and whether they perhaps need to
   change.
3. Finish by implementing the rest of the functions related to manipulating the
   items in an aisle. Some of these functions might seem a bit more involved,
   but none of them should take you more than several lines of code. Remember
   the bit shifting operations that you have at hand, and try to play with some
   examples before starting your implementation. Again, if you get stuck, come
   to office hours or post your question on Ed.

At this point, assuming that you implemented all the functions in
`aisle_manager.c` correctly, you should be able to pass all tests in the
`aisle_test` executable! Now you can move onto `store_manager.c`.

---

### `store_manager.c`

`store_manager.c` has some functions for interacting with a group of aisles and
a *stockroom* in the store. It uses a global array with elements of type
`unsigned long` to represent multiple aisles. It uses a global array with
elements of type `int` to represent a stockroom for the store (items stowed
away that are not in the aisles). Unlike with the sections in an aisle, the
stockroom value for a given item id is just the number of items with that id in
the stockroom. You can use the global variables in any of the functions as
though they were local variables, without declaring them as local variables.
There are slight differences between global variables in C and Java, but they
are not relevant for this assignment, and we will discuss them at a later point
in the course.

For all the functions in `store_manager.c`, it is still the case that you get
very descriptive comments before the definition of each function. These
comments will explain to you what the function is supposed to do, and what the
return value should be. Read these comments! You should be comfortable when it
comes to understanding the function first, before doing any coding. Again,
simple paper and pen examples will probably be helpful here too.

Note that in these four functions, you will likely have to write more code than
you had to write for the functions in the `aisle_manager.c` file, but it will
be conceptually simpler code that will call on the routines defined previously,
in the `aisle_manager.c` file.

Once you finish implementing the functions in `store_manager.c`, you should be
able to pass all tests in the `store_test` executable. This means you are done
with the programming part of the assignment. If you are part of a team, also
please make sure to submit the reflection part.

---

### Functions To Implement

Here is a list of functions that need to be implemented:

In `aisle_manager.c`:

* `get_section()`,
* `get_spaces()`,
* `get_id()`,
* `set_section()`,
* `set_spaces()`,
* `set_id()`,
* `toggle_space()`,
* `num_items()`,
* `add_items()`,
* `remove_items()`,
* `rotate_items_left()`,
* `rotate_items_right()`.

In `store_manager.c`:

* `refill_from_stockroom()`,
* `fulfill_order()`,
* `empty_section_with_id()`,
* `section_with_most_items()`.

---

### Checking your Work

We have included a few tools to help you check your work.

* We provided the functions `print_binary_long()` and `print_binary_short()`,
  which take an `unsigned long` and `unsigned short`, respectively, and output
  its binary representation. These can be useful in debugging your code, but
  their use is optional and **all calls to the functions should be commented out
  in your final submission.** Here is a usage example for `print_binary_long()`
  (`print_binary_short()` works similarly except it takes an `unsigned short` as
  its argument):

```c
void print_binary_example (unsigned long argument) {
  // Print out binary view of argument for debugging.
  print_binary_long(argument);
}
```

* You can also use `printf()` to debug your functions, but you'll have to
  include a line with `#include <stdio.h>` at the top of your file. Its use is
  optional, and **all calls to the `printf()` function should be commented out
  in your final submission.**

* `aisle_test` and `store_test` are programs that check the functional
  correctness of the code in `aisle_manager.c` and `store_manager.c`,
  respectively. To build and use them, type the following two commands at your
  prompt (the `$` represents the Linux shell prompt):

```bash
$ make
$ ./aisle_test
$ ./store_test
```

Notice that you must rebuild `aisle_test` and `store_test` each time you modify
your code. You can rebuild them by typing `make` in the command line. You may
find it helpful to work through the functions one at a time, testing each one
as you go. You can use the `-f` flag to instruct `aisle_test` and `store_test`
to test only a single function (again the `$` represents the Linux shell
prompt):

```bash
$ ./aisle_test -f set_id
$ ./store_test -f section_with_most_items
```

Here, for `aisle_test`, we are testing the individual function `set_id()`. For
`store_test`, we are testing the individual function
`section_with_most_items()`.

* `aisle_test` and `store_test` first test your solution on specific cases, and
  then on a wide range of inputs. As a manner of limiting the test output, for
  the range of inputs the testing program will only print out the first input
  that your solutions are incorrect for (or **nothing** if you pass all cases).

* Note that each function is tested separately to allow for the opportunity for
  partial credit should you only complete some of the functions.

* Some test cases used for autograding in Gradescope may be hidden.

You can also run the autograding script to verify things before submitting to
Gradescope (again the `$` represents the Linux shell prompt):

```bash
$ python3 hwtest.py ALL
```

---

### Submission

* Submit your project on Gradescope using the GitLab submission process.

* **Team specific instructions**

  * Teams should only do one submission via group submission.
  * Teams must also edit the file called `reflection.txt` and include a
    detailed description of each team members contributions to the project.
