You need to write 6 short programs that help you become familiar with general
C programming, command line arguments, file input/output, refresh 
your recursion skills and do a simple string manipulation.  The programs 
and what they should do are below. Sample outputs are at the bottom of this file.  
All programs should have a proper return value from main (i.e., 0 or use 
the predefined EXIT_SUCCESS).

Follow the same steps as in Lab 01 (repeated here):
1. Fork this repository and mark it private.
2. Clone your newly forked repository into a directory on your OIT 210 container or the directory accessible to your local container.
3. Use Visual Studio Code (VSCode) to create your files.
4. Use make to compile the programs.
5. Run each program locally

**args1.c**: this program takes three command line arguments in the following order,
an integer, a float, and a string (not to exceed 64 characters including null 
terminating char).  If not enough arguments are provided the program should 
print "Error not enough inputs!\n", otherwise it should print the arguments in the
same order as entered with commas after each one.

Execute at command line: ./args1 1 0.3 foobar

**args2.c**: is similar to args1.c but instead of command line arguments it 
uses terminal input from the user to obtain the three values (int, float, string).
Your program should use the following prompt:
"Enter an integer a float and a string\n" 
and then use scanf to read the inputs.  If scanf fails to read the three 
inputs your program should output "Error with input.\n"

Execute at command line: ./args2 and type inputs in response to the prompt
or use file redirection with input1.txt: ./args2 < input1.txt. this is what the test_kit does.

**file1.c**: uses file input and output, it should open the file input1.txt, use fscanf
to read the int, float and string and then write the three values each on a separate
line to the file output1.txt

Execute at command line: ./file1

**file2.c**: you are to create a program that reads integers from a file into 
an arary of structs. Each structure has two integers and the array has 10 entries.
The program will use file input/output to read the input file input2.txt that has the
following format:
counter
index value1 value2
index value1 value2
...

the counter is an integer that indicates the number of remaining lines in the file
where each of those lines contains the index into the array and two integer values.

Your program should read the values and place them into the array of structs at the
corresponding index.  You can assume the index is always within range of the array,
but good coding practice would have a check to ensure that's the case.

Your program should iterate in reverse order (starting from the last array index)
and output the the sum of the two integer values  using the following format
"idx: %d sum: %d\n"

You should check for errors in any of your file open and close calls (be sure to
close the files!).

Execute at command line: ./file2

**recurse.c**: takes one integer command line argument and computes
```C
f(n) = 1  // n <= 1
f(n) = f(n-1) * (n + f(n-2)  // n > 1
```

print "Error with input.\n" if not correct input args
print the result using "%d\n" for correct inputs.

Execute at command line: ./recurse 3

**strings1.c**: Create a program that prompts the user for two strings one after the other
and then produces a new string that is the contatenation of the two input strings.
The input strings will be less than 32 characters and the output will not exceed 64
characters.  Prompt for the strings using the following formats.
"Enter string 1.\n"
"Enter string 2.\n"

For output print string1 string2 and the result using the following format.
"%s, %s, %s\n"

Execute at command line: ./strings1 or use file redirect: ./strings1 < string_input.txt


test locally
run the following on the unix command line

python3 test_kit.py ALL
or test individual components 
python3 test_kit.py TEST_SUITE_NAME where TEST_SUITE_NAME is one of [args1, args2, file1, file2, recurse, strings1]

** it is important that the output of your program matches exactly that shown below (this are also provided in the tests/*_expected_*.txt files)

What to submit:
upload the following source files to gradescope

args1.c
args2.c
file1.c
file2.c
recurse.c
strings1.c


Tests for this program are shown below

**args1 output for command line**: args1 2 3.75 hello
```
1, 3.750000, hello
```

**args2 output for command line**: args
```
Enter an integer a float and a string
1, 3.750000, hello
```

**file1 output for command line**: file1
```
1
3.750000
hello
```

**recurse output for command line**: recurse 6
```
128520
```

**file2 output for command line**: file2
```
idx: 9 sum: 18
idx: 8 sum: 0
idx: 7 sum: 0
idx: 6 sum: 0
idx: 5 sum: 15
idx: 4 sum: 0
idx: 3 sum: 0
idx: 2 sum: 30
idx: 1 sum: 0
idx: 0 sum: 2
```


**strings1 output for command line**: strings1
```
Enter string 1.
lions
Enter string 2.
bears
lions, bears, lionsbears
```

What to submit:
Submit your project using to gradescope using the gitlab button.