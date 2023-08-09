# Overview

This directory contains source files of the binaries included in the tarball distributed to students (specifically `.aisle_util.o` and `.store_util.o`) which contain staff solutions to help test the correctness of student solutions. 

## Making Changes
If you need to update the solutions, first change the corresponding source file (note that the function names are appended with `_sol` intentionally to avoid namespace issues with student functions).

Then recompile that source file into an object file. We originally used the below `gcc` command to obscure the source code from disassembly as much as possible, and made the object file hidden to lessen the likelihood students would ever know it exists.
```
gcc -O3 -fvisibility=hidden -s -c aisle_manager_sol.c -o .aisle_util.o
gcc -O3 -fvisibility=hidden -s -c store_manager_sol.c -o .store_util.o
```

Then move the object files into the student lab1b directory (`labs/lab1b/lab1b`).
