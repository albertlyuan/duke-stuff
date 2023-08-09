This lab gives a little experience with virtual memory using the linux fork system call.
You are to modify main in the files fork1.c fork2.c and fork3.c in the following ways. Do not remove
or modify any of the existing lines of code.

Please see my_fork.h for function headers for `my_fork`, `my_waitpid`, and `my_execv`.

---

**fork1.c**

perform a single fork system call (using `my_fork`) and do the following in child and parent:

child:
1. print `value` and its address with the format `"Before: Hello from child, &value = 0x%p, value = %d\n"`
2. set `value` to 210
3. print `value` and its address with the format `"After: Hello from child, &value = 0x%p, value = %d\n"`

parent:
1. print the child pid, `value` and its address with the format `"Hello from parent, &value = 0x%p, value = %d, child pid = %d\n"`

**Example output**: (Note that the number values you print will probably be different)
```
./fork1
Hello from parent, &value = 0x0x7ffe14b3ba74, value = 1234, child pid = 75783
Before: Hello from child, &value = 0x0x7ffe14b3ba74, value = 1234
After: Hello from child, &value = 0x0x7ffe14b3ba74, value = 210
```
**NOTE** the output format for each line must match exactly or 
you won't pass the autograder. For fork1 the output lines are allowed to appear in any order and your `&value`
will change with each run of the program. 

---

**fork2.c**

Perform a single fork system call (`my_fork`) and have the parent wait (`my_waitpid`) for the child to finish
before printing its output. You don't need to provide any options for the third argument
of `my_waitpid`--you can just use 0 instead. Do the same operations as for fork1.c, but now the child
should always print before the parent.

child:
1. print `value` and its address with the format `"Before: Hello from child, &value = 0x%p, value = %d\n"`
2. set `value` to 210
3. print `value` and its address with the format `"After: Hello from child, &value = 0x%p, value = %d\n"`

parent:
1. print the child pid, `value` and its address with the format `"Hello from parent, &value = 0x%p, value = %d, child pid = %d\n"`

**Example output**: (with placeholders for the number values)
```
./fork2
Before: Hello from child, &value = 0x0x7ffe14b3ba74, value = 1234
After: Hello from child, &value = 0x0x7ffe14b3ba74, value = 210
Hello from parent, &value = 0x0x7ffe14b3ba74, value = 1234, child pid = 75783
```

---

**fork3.c**

Building on fork2.c, you are now to use the `mmap` system call to get memory from the operating
system that will be shared between the parent and child process. `mmap` returns a pointer, so your
code will be different than in fork1 and fork2 to account for this.  The `mmap` manual (man) page is available
at https://man7.org/linux/man-pages/man2/mmap.2.html. 

`mmap` has the following function header:
```
void *mmap(void *addr, size_t length, int prot, int flags, int fd, off_t offset);
```
Consult the man page in the link above in order to pass the following parameters to `mmap`:
- The kernel chooses the address
- The size is for a single integer
- The protection gives read and write permissions
- The flags are for shared memory and anonmyous memory
- The file descriptor is -1 
- The offset is 0

Keep in mind that constants for the flag and protection arguments such as `PROT_WRITE`
can be referenced directly in your code. They are just static constants declared in
libraries that the program imports.

Use the `munmap` system call in both parent and child to unmap the memory before returning from main:
```
int munmap(void *addr, size_t length);
```

Your parent should initialize the shared value to 1234 before calling fork. The child should print the
same information as in fork2, but should use the shared value and its memory address. The parent should now print the strings below, where `value` is the
shared value allocated via `mmap`.

The parent should do the following:
1. print child pid and info about `value` with the format `Before Wait: Hello from parent, &value = 0x%p, value = %d, child pid = %d\n"`
2. wait for child to terminate
3. print child pid and info about `value` with the format `"After Wait: Hello from parent, &value = 0x%p, value = %d, child pid = %d\n"`

The child print statements:
1. print `value` and its address with the format `"Before: Hello from child, &value = 0x%p, value = %d\n"`
2. set `value` to 210
3. print `value` and its address with the format `"After: Hello from child, &value = 0x%p, value = %d\n"`



**Example output**:
```
./fork3
Before Wait: Hello from parent, &value = 0x0x7f89a91db000, value = 1234, child pid = 82851
Before: Hello from child, &value = 0x0x7f89a91db000, value = 1234
After: Hello from child, &value = 0x0x7f89a91db000, value = 210
After Wait: Hello from parent, &value = 0x0x7f89a91db000, value = 210, child pid = 82851
```

---

**Local Testing and Submission**

To run the test harness, use the following command:
```bash
python3 ./test_kit.py
```
**NOTE:** The test kit will terminate with a Python error if any of your programs 
are missing any of their required `printf` statements. To fix this, you must
add the `printf` statements (using any values), compile the programs, and run each of them once on the command line:
```bash
make
./fork1
./fork2
./fork3
```

If your code isn't passing the Python tests, you can also run fork1, fork2, and fork3 directly to see
the output. Then you can compare the output to the example output in this
README to check if your ordering is correct.

Submit your completed fork1.c, fork2.c, fork3.c to gradescope.
