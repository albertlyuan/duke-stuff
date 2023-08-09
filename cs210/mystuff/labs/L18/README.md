This lab gives a little experience with the linux fork and exec interfaces
for processes and process management. There is also an optional task dealing
with the linux signal interface.

Fork and Exec:
Your objective is to modify main in the files fork1.c, fork2.c and fork3.c as described below. You must use our provided functions to invoke
fork, wait and execv. They are called `my_fork()`, `my_wait()`, and `my_execv()` respectively and they provide some additional checking and hooks for grading. Please take a look at the `my_fork.h` header file to see how to use the functions provided. 

---

**fork1.c** 

Perform a single `my_fork` call and
print "Hello from child\n" if in the child process and
print "Hello from parent, child pid = `<pid number>`" if in the parent process.

In addition to my_fork.h, see the [linux man page for fork](https://man7.org/linux/man-pages/man2/fork.2.html) for information about how to interpret the value returned by `my_fork`.

**Example output:**
```bash
$ ./fork1
Hello from parent, child pid = 93459
Hello from child
```

**NOTE:** the output format for each line must match exactly or 
you won't pass the autograder. For fork1 the output lines may appear in any order. 

---
**fork2.c**

Perform a single `my_fork` call and have the parent wait (using provided function `my_wait()`) for the child to finish
before printing its output.  Print the same output as for fork1.c, except that now the child
should *always* print before the parent.

See the [linux man page for wait](https://man7.org/linux/man-pages/man2/wait.2.html) for information about what arguments to use for
for `my_wait`.

**Example output:**
```bash
$ ./fork2
Hello from child
Hello from parent, child pid = 93722
```

---

**fork3.c**

Building on fork2.c you should modify the child process so that it uses exec to launch another program 
using the `my_execv` call. 

execv is described at https://linux.die.net/man/3/execv. You should execute the command at path "/bin/ls" and provide it the command line argument "/bin". 

**HINT**: 
- You should create a myargv array of strings. The three elements of that array is myargv[0] = "/bin/ls", myargv[1] = "/bin", myargv[2] = NULL.
- A way to make an array of strings:
```c++
char* myargv[] = {"String 1", "String 2", NULL};
```

Running `/bin/ls` will list all the files in /bin. The child should print its hello statement and then perform the `my_execv` call.
The parent should wait for the child to finish before printing its hello statement.

**Example output:**
```bash
$ ./fork3
Hello from child
bash          chacl          egrep     lesskey        pidof       su            zcmp
bunzip2       chgrp          false     lesspipe       ps          sync          zdiff
bzcat         chmod          fgrep     ln             pwd         tar           zegrep
bzcmp         chown          findmnt   login          rbash       tempfile      zfgrep
bzdiff        cp             getfacl   ls             readlink    touch         zforce
bzegrep       dash           grep      lsblk          rm          true          zgrep
bzexe         date           gunzip    mkdir          rmdir       umount        zless
bzfgrep       dd             gzexe     mknod          run-parts   uname         zmore
bzgrep        df             gzip      mktemp         sed         uncompress    znew
bzip2         dir            hostname  more           setfacl     vdir
bzip2recover  dmesg          kill      mount          sh          wdctl
bzless        dnsdomainname  less      mountpoint     sh.distrib  which
bzmore        domainname     lessecho  mv             sleep       ypdomainname
cat           echo           lessfile  nisdomainname  stty        zcat
Hello from parent, child pid = 96194
```

---

**Optional: Signals**

**sig1.c**

The file sig1.c shows how to register a signal handler, and this specific
case it is for the SIGSEGV (segmentation fault) scenario. Run that program to
see how it enters the signal handler when the erroneous access to *foo occurs.

--- 
**sig2.c (Optional)**

Your task is to modify sig2.c to handle interrupt signals that are created with 
^C on the keyboard. Specifically, you are to make the user enter ^C three times
before the program terminates by calling exit(1). Each time a ^C is entered you will print a
different string, as shown in the example output.

Do not change anything in main.

**HINT:** a local static variable in sigint_action or a global variable is needed.
Start with calling exit(1) on the first ^C typed and build up to the three from there. Also,
every string should be printed with a newline character at the end.

**Example output:**
```
$ ./sig2
Can you stop me?
^CHmm trying to stop this program are you?
^CYou are persistent!
^COK you win.
```

You will print the above strings without the ^C, that's just an echo of the input.

**IMPORTANT NOTE:** sig2 has an infinite loop in main. I have included a limit of 20 seconds
on execution time before the operating system terminates the program using the
setrlimit system call.  Do not change this. If you don't properly terminate
the process it will run forever and consume cycles, even if you exit your terminal and reconnect. If you have an infinite loop that doesn't terminate, you should use linux
command line utilities to ensure that sig2 is not running.
First run 
```bash
ps
```
to print your currently running processes. If sig2 is running, note down its PID from the table of processes and then use either
```bash
pkill sig2
```
or use the process's PID in
```bash
kill <PID>
``` 
to terminate the process.  

---

**Local Testing and Submission**

to run the test harness use the following command:
```bash
python3 test_kit.py ALL
```

Submit your completed fork1.c, fork2.c, fork3.c and sig2.c to gradescope.