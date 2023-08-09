This lab gets you more experience with pointers and dynamic memory allocation (the infamous malloc and free). Thanks to Mike Hewner for his work on these!

Fork and clone the lab as with previous labs.
The lab has one required part and one optional part.

***

**pointers2.c**

Modify four functions (and one tester function) in pointer2.c to correctly implement the specified functionality.

1. stringcat
2. test_stringcat (the tester function)
3. sum_linked_list
4. linked_list_equals
5. make_chain_in_memory

Testing:

Everytime you make changes in pointers2.c, you have to make/compile pointers2.c with:

```bash
make pointers2
```

You can then test all of the functions by running:
```bash
./pointers2 all
```

You can test an individual function by passing the function name as a command line argument. e.g.(./pointers2 sum_linked_list):
```bash
./pointers2 sum_linked_list
```
(or)
```bash
./pointers2 stringcat
```

You may use any of the libraries that are included at the top of pointers2.c. This includes string.h, which provides the following useful (but not strictly necessary) functions:
- `strlen(char* str)`: Returns the number of characters in the provided string, not including the terminator.
- `strcat(char* dest, char* src)`: Concatenates string `src` onto the end of string `dest`. The buffer pointed to by `dest` must be large enough to contain the result, or else an error may occur. 

***

**image2.c** **(OPTIONAL)**

Modify the image program from L04 to dynamically allocate the array for the image structure based on the values you read in the header portion of the .ppm file.

As with L04, your program needs to take two command line arguments and needs to be called image2. You are provided
the PPM file sample.ppm so an example program execution would be:
```bash
./image2 sample.ppm new.ppm
```
this would execute your program and write a new file called new.ppm

Again use scp to copy  the image files from your OIT container so you can view them, or view them via local access to your local container. Or download the ppm file from VS Code.

**Important**: Once you are done, please run make clean to delete your residual .ppm output files. (Gradescope has limited storage, if you resubmit your GitLab repository with a bunch of .ppm files enough times, your Gradescope storage will fill up and you won't be able to resubmit anymore.)

```bash
make clean
```

***

**Final Local Testing Before Submission** 

To test both programs with the autograder, run the python autograder script:
```bash
python3 test_kit.py ALL
```

To test individual components:
```bash
python3 test_kit.py pointers2
```
```bash
python3 test_kit.py image2
```
***

**Submission**

1. Please run the below before submitting to Gradescope to delete your output.ppm files and compiled executables:
```bash
make clean
```

2. Submit your pointers2.c file to Gradescope via GitLab submission.
3. (Optionally): Also submit your image2.c file to Gradescope




