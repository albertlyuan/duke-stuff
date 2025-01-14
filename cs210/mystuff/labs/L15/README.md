This lab is for exploring locality and impact on performance. The main
objective is to answer a few accompanying questions on Gradescope.
The lab closely accompanies section 6.2.1 of the CS:APP textbook--the functions
here are the same as or similar to those in the text.

Compile all three files: sumarrayrows.c sumarraycols.c and summaray3d.c. You can use the included Makefile.

Use the linux time command to see how long each program takes to execute:
```bash
time ./sumarrayrows
time ./sumarraycols
time ./sumarray3d
```
Take note of the runtimes for each program. Focus on the time labeled "user".

Next, make a copy of sumarray3d.c (call it sumarray3dnew.c) and change 
the loop order to achieve better spatial locality.
Time your modified program:

```bash
time ./sumarray3dnew
```

Note how long it takes to execute.

Answer a few questions on gradescope about locality and these programs.
