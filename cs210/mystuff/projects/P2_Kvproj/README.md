#### Objectives:
- Gain familiarity with data representation and memory layout of data.
- Gain practical knowledge of pointer and memory manipulations in C.
- Gain practical knowledge of dynamic memory allocation and data deserialization.

You will interact with a compact memory (serialized) representation of key value pairs in a buffer and the implementation of linked lists.

#### Format:
This project uses a custom memory buffer representation of key-value pairs and requires you to
perform pointer arithmetic and memory operations for data deserialization along with linked list implementations. The file **kv_store.c** contains skeleton functions for you to complete, each with comments describing its behavior. 

#### Key-Value Deserialization:

The serialized buffer contains key value pairs in the following memory layout.

```
|key|size|value string|key|size|value string|....
```
Note that 'key' is 8 bytes and size is a 4-byte unsigned integer.

Deserialization is the process of reading through this buffer, starting at the very beginning,
and extracting the relevant information. In this case, it is reading key value pairs.

The buffer is specified by its starting address in memory and its total size in bytes. The size
of each value string is specified by the size value immediately preceding the string.  Your job
is to read each key-value pair from this buffer and insert it into a linked list. **You must copy the string values from the memory buffer to a new memory location**. The C library function memcpy is good for this.

The linked list is comprised of nodes of the type KVPAIR, a structure defined in **kv_store.h** and included here:
```C++
typedef struct KVP {
    long key;           // the key 
    unsigned int size;  // the size of the value string
    char *val;          // pointer to the value string
    struct KVP *next;   // pointer to the next node
} KVPAIR;
```

Make sure you fully understand the buffer memory layout and KVPAIR structure. If you have any questions, please post on the message board or come to office hours before you begin working on the code.

Once you understand the data representation, you can begin to implement the skeleton functions in **kv_store.c**. You have three functions to complete: 
1. deserialize
2. lookup
3. delete

Once complete these three functions you should be able to pass all tests in the **kv_test** executable! 

#### Checking your Work
We have included a few tools to help you check your work.

- We have included the function **print_list**, which takes a pointer to list of KVPAIRS and prints the list. This may be useful in debugging your code, but its use is optional and **all calls to this function should be commented out in your final submission.**

- You can also use printf to debug your functions. Its use is optional, and **all calls to the function should be commented out in your final submission.**

- **kv_test** is a program that checks the functional correctness of the code in **kv_store.c**. To build and use it, type the following two commands at your prompt (the $ represents the linux prompt):
```
$ make
$ ./kv_test
```
Note that you must rebuild **kv_test** each time you modify your code. (You can rebuild it by typing `make`) You may find it helpful to work through the functions one at a time, testing each one as you go. You can use the **-f** flag to instruct **kv_test** to test only a single function and the **-n** flag to change the number of key-value pairs tested:
```
$ ./kv_test -f lookup
$ ./kv_test -n 256 -f delete
```
- **kv_test** is used with the testkit `hwtest.py` to test your solutions various cases, and then on a wide range of inputs.
- Each function is tested separately to allow for the opportunity for partial credit should you only complete some of the functions.
- Some test cases used for autograding in Gradescope may be hidden.

You should run the local test script before submitting to Gradescope:

```
$ python3 hwtest.py ALL
```
#### Submission

- Submit your project on Gradescope using the GitLab submission process.

- ***Team specific instructions*** 
  - Teams should only do one submission via group submission.
  - Teams must also edit the file called reflection.txt and include a detailed description of each team member's contributions to the project.
  