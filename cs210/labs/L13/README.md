This lab has you learn about indirection using function pointers by making our own objects and about interpretation
by completing a portion of an interpreter for a simple stack-based instruction set processor.

**Indirection:**
For this portion we are experimenting with writing our own objects where functions (methods) are
declared with the data items. You can see in the source file (indirect.c) that there is 
already an add_ab function defined in the stucture.  Similarly you can see in the body of 
main where that function pointer is first set to a good add function (my_add_ab) and then 
changed to point to a bad add function (bad_add_ab).

What to do: 
1. modify the structure OBJ1 to add four new functions to the structure.
```
set_a that takes two arguments, a pointer to an object of type OBJ1 and an integer to set the field a to.
set_b that takes two arguments, a pointer to an object of type OBJ1 and an integer to set the field b to.
set_c that takes two arguments, a pointer to an object of type OBJ1 and an integer to set the field c to.
sum_all that takes one argument, a pointer to an object of type OBJ1, and returns the sum of fields a, b and c.
```
2. Write the above four functions.
3. Modify the main program set these function pointers appropriately.
4. Test your program so that it ouputs the correct values.

**Interpret: (Extra Credit)**
Your task here is to complete the main body of interpret.c such that it can perform correct interpretation
of the simple stack machine instruction set. The isa is defined as an 8-bit stack architecture, where
each instruction is only 8-bits long and defined with the following binary encoding
```
| opcode | operand | 
``` 
the upper (most significant) 3 bits are the opcode and remaining 5 are an operand.

The instruction set is comprised of the following eight instructions (see isa.h for instruction definitions)
```
push val
pop
add
sub
neg
xor
mul
out
```

Note that push is the only instruction that has an operand, it is a 5 bit signed integer. 
So be sure to sign extend the value in your interpreter. The out instrution is used to print the current value
at the top of the stack.  The format for the out instruction is as follows:
```
"TOS = %d\n" if the stack is not empty, print the value.
"TOS = empty\n" if the stack is empty.  
```
You must follow this format exactly.

The program ./asm is an assembler for this simple ISA that takes programs written in a simple
assembly as above (all lower case!) and produces a binary file of the instructions.  Several
small programs are provided (all the *.s files).  The make file includes the ability to assemble
these files for you. They are used for testing your interpreter.  You can assemble them yourself by running the ./asm command:
for example ./asm add.s would produce the file add.bin that can be used as input to your interepter.

Example output: 
```
./interpret add.bin
TOS = 3
TOS = 4
TOS = 7
```

This interpreter is a very simple example in the spirit of the java virtual machine instruction set.
There is a lot more to the java virtual machine, but the idea of interpretation is key.  Java goes
a step further and includes something called dynamic compilation or just-in-time compliation that
translates its' stack instructions to machine instructions, which in our class would be x86_64 instructions.


test locally
run the following on the unix command line

python3 test_kit.py ALL
or test individual components 
python3 test_kit.py TEST_SUITE_NAME [indirect or interpret]

What to submit:
upload your completed indirect.c and interpret.c (extra credit) to gradescope



