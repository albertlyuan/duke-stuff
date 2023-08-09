This lab requries you to write simple bit mainpulation routines.
See the file bits.c for instructions, which are contained in the comments.

Like the previous labs, fork and clone the repository to your container.

You are to write the code for 6 routines: isNegative, negate, getByte, byteSwap, rotateRight and addOK
using only bit-wise operations (no conditionals, while loops, function calls, etc.). The function prototypes
are defined in bits.c, but they simply return the value 2. You need to change the function bodies to return
the correct value, but you are limited to using only bit-wise operations.
Further information about correct functionality for each function is in bits.c.

The provided Makefile runs the program dlc on your bits.c file to check for any violations of the operator restrictions.

Execute at command line: ./btest _testname_ (e.g., ./btest _all_ will run all functions while ./btest _negate_ will run only the negate function).

test locally
run the following on the unix command line

python3 test_kit.py ALL

What to submit:
upload your completed bits.c to gradescope




