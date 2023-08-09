## P6: Allocproj
---
### Objectives:
- Gain familiarity with a simulated segregated heap allocator

You will implement a segregated heap allocator that keeps track of the memory used.

---

### Format:
This project has three functions in need of implementation. The three functions you should modify are in `alloc.c`

You do not need to (and really shouldn't) modify any other files in the project. Doing so may cause certain tests and compilation commands to break.

---

### Understanding Helper/Util Files:
For every project, we always give you certain files like `.h` header files and `.c` util files to help you gain a better grasp of the code elements/data structures in the project. Before working on the project, we strongly recommend reading the explanations below.

**`alloc.h`**

This file contains the function headers and struct definitions for elements in `alloc.c`

The first two function headers tell `alloc_test.c` (which uses functions and variables from `alloc.c` and is also the wrapper program that helps run tests) what functions and variables are accessible/can be called or used.

Also, notice that `NUM_HEAP_BLOCKS` is defined at the bottom as exactly 8. This means that every time the `allocate_slab()` function is called, 8 blocks will be allocated. More on when `allocate_slab()` should be called and what it is supposed to do below.

In addition, please take a good look at the `blk_header_t` struct. This will go before every block allocated. This struct provides us with information of the size of the block it's in front of and a pointer to the next block. An example of a set of allocated blocks in memory is shown below (The memory/linked list is displayed and connected vertically):

|bin0|
|:---:|
|blk_header_t|
|block|
|blk_header_t|
|block|
|...|
|blk_header_t|
|block|
|blk_header_t|
|block|

**NOTE:** Since `blk_header_t`'s next field points to the next `block` instead of the next `blk_header_t`, we need to do some pointer arithmetic to access the next `block`'s `blk_header_t` information. Also be mindful to always cast variables to the proper types. Blocks are `void*` and headers are `blk_header_t*`.

Hopefully you noticed that the heap is a `void*` array (aka. an array of `void*`s). The tester will access this array to check the linked list of blocks at each array index (or bin). 

The final thing to note is the function `void count_heap_blks()` will be used in the test so DO NOT modify this function. The other two functions `print_bin()` and `print_heap()` are there for you to use in `alloc.c` for debugging. ***They must be removed/commented out before running tests or submitting to Gradescope***

---

### What you are implementing...

**`alloc.c`**

There are three functions you should implement. They are also listed in the code in the order which you should implement them.

Before implementing the functions, please review CS:APP 9.9.14

1. `void *allocate_slab(unsigned int size)`
   - This function is called if `my_alloc` doesn't have enough free blocks. It will
   allocate and return a new slab of memory of the
   desired size
   - The returned slab will not have any
   free list data set. Configuring this data
   is the job of the calling function
   - It will also return the malloc'd memory as a void pointer or NULL if the allocated memory would exceed `max_heap_size`

2. `void *my_alloc(unsigned int size)`
    - This function will take in a size specified by the test, match the size with the correct bin of the heap array to get the free list.
    - After, it will determine the list is NULL (there are no more blocks in the free list) to call `allocate_slab` or not to allocate more blocks. 
    - To calculate the slab size, use basic arithmetic with the bin number to get segregated size, add on the blk_header_t size (because every block needs a header allocated), and multiply that computed size with the `NUM_HEAP_BLOCKS` to get total size for the slab.
    - ***Important!*** If the requested allocation (from `allocate_slab()`) exceeds the maximum heap size your code should print `"Malloc failed, out of memory!\n"` and exit with 0 value without doing anything else. 
    - It will then remove the the first block from the heap and return that block.

3. `void my_free(void *ptr)`
   - This function will take the pointer to the block and insert it into the front of a particular bin calculated by the `blk_header_t` info before the block in memory. (Hint: pointer arithmetic to get the header of that block)
   - You will have to modify `heap[bin]` and insert the given block (`ptr`) into the `heap[bin]`

---

### How to test your code

We have included a python tester and a settings.json file as well as all of the test input files. Below will go over methods to test your compiled code.

1. Run `make` to compile your `alloc.c` with a wrapper called `alloc_test.c`

2. The Python Tester runs all of the tests listed in `settings.json` and reports a point value for each test.
    ```bash
    python3 hwtest.py ALL
    ```
3. You can run individual tests by typing each value of a particular test's `args` field of the `settings.json` and pasting them into the terminal after `./alloc_test` and comparing the printed results with the expected file. Below are examples for tests 0 and 2.
   
    `-f <filename>` sets the file to read 
    
    `-s <max_heap_size>` sets the max heap size
    ```bash
    ./alloc_test -f t1.txt
    ./alloc_test -f t3.txt -s 2048
    ```
    
---

### Submission

- Submit your project on Gradescope using the GitLab submission process.

- ***Team specific instructions*** 
  - Teams should only do one submission via group submission.
  - Teams must also edit the file called reflection.txt and include a detailed description of each team member's contributions to the project.
  