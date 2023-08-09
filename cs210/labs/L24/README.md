This is your third lab learning about concurrency through the use
of posix threads.  This lab focuses on deadlock.  As before use the capital letter versions
of the thread library functions (e.g., `Pthread_create`).

There is one program for you complete in this lab: **array.c**. You need to fill in
the functions `update_two` and `create_and_wait`.  

---

**create_and_wait_threads** 

This function should create four threads that invoke `thread_worker`, which is a function
provided by the **mythreads.h** library, and wait for all of them to complete. 

There is one argument to `thread_worker`, an integer in the range [0-3] providing an identifier for the
thread. Do not use a pointer to a structure as the argument, just a pointer to the integer argument.

`thread_worker` just calls `update_two` many times in a loop, providing two arguments which are 
indicies into `array` for you to update.  

---
**update_two**

This function takes two indices as arguments. The lower valued index has its array value updated by calling `update_entry(idx)` and the larger valued 
index has its array value updated by adding the new value from lower index to it (`array[larger] += array[lower]`). `update_entry` is defined in a separate file we provide to you.

Since `update_two` is called from multiple threads, you need to **make it thread safe by introducing appropriate
locking**. The program needs to produce correct output, enable good performance for large arrays, and not deadlock. 
You are to use one lock per entry in the array. Do not use one big lock--that would produce the correct
output but it would not have good performance (we check for this). 

**Hint:** You can read up on Chapter 32 of OSTEP (linked from the course calendar) for a guide of deadlock prevention and order-violation bugs.

`update_two` may be called with any index
in the range 0 to SIZE-1 in either of the arguments.   The two argument indicies will never be equal. This 
problem is designed to quickly induce deadlock if it can occur in your program.

A correct program runs and produces output similar to that below. However, during
development your program may deadlock, and you may have to type ^C to terminate
it if it dosen't complete quickly.  Remember you can always run `ps` in
the terminal to see the
processes you have running and use the unix `kill` command to terminate them.

---

**Local Testing and Submission**

Run your program manually for debugging, etc. using the following
```bash
./array
```

to run the test harness use the following command
```bash
python3 test_kit.py ALL
```
Submit your completed array.c to Gradescope.
