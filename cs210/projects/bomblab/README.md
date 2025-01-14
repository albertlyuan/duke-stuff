The nefarious Dr. Evil has planted a slew of "binary bombs" on our machines. A binary bomb is a program that consists of a sequence of phases. Each phase expects you to type a particular string on ``stdin`` (standard input). If you type the correct string, then the phase is defused and the bomb proceeds to the next phase. Otherwise, the bomb explodes by printing ``BOOM!!!`` and then terminating. The bomb is defused when every phase has been defused.

There are too many bombs for us to deal with, so we are giving everyone a bomb to defuse. Your mission, which you have no choice but to accept, is to defuse your bomb before the due date. Good luck, and welcome to the bomb squad!

**Objectives:**
- Gain basic familiarity with x86-64 assembly instructions and how they are used to implement comparison, loops, switch statements, recursion, pointers, and arrays.
- Gain experience using the ``gdb`` debugger to step through assembly code and other tools such as ``objdump``.

---

**Instructions:**

The binary bomb is an executable program that has 4 phases and you need to determine the
correct input for each phase such that the bomb doesn't explode. Each phase must be defused in sequence and you always have to defuse the early phases to reach the later stages.

Obtain your specfic binary bomb by downloading it from a specific URL. Follow the steps below to obtain your binary bomb.
1. Pointing your browser at the following URL ``https://courses.cs.duke.edu/compsci210d/fall21/student_bombs/<NetID>``, where ``<NetID>`` is your Duke NetID. This directory requires Duke NetID Shibboleth authorization, and you may be prompted to authenticate if you aren't already.
2. Save the file called bomb-``<NetID>`` to your local machine (right click "Save link as").
3. Copy the file ``bomb-<NetID>`` to your container using the scp command ``scp -P30002 bomb-<NetID> term@<container_name>.oit.duke.edu:~/<bombprojectpath>``. You need to substitute your actual container hostname and the path to this repository on your container.  This will copy the bomb file to your project directory on your container.
    - Alternatively, you can simply drag and drop the bomb file from your
    local machine to the file browser in VSCode. You can also fork this
    repository, upload the bomb to your repo using the GitLab web
    interface, and then use `git pull` on your container inside
    this repository to download the file.
4. Open a terminal on your container and cd to the directory with your bomb.
5. Change permissions to allow execute by using the linux chmod command ``chmod +x bomb-<NetID>``

You are now ready to start defusing your binary bomb, primarily using gdb.
You run your bomb by typing ``./bomb-<NetID>`` and you can directly type input on the keyboard.

The project repository contains a file called ***solutions.txt*** where you add the correct inputs to your program as you defuse each phase. You will submit this file to Gradescope.  You can also provide your solutions file as an argument (e.g., ``./bomb-<NetID> solution.text``) to save time typing inputs as you move to debugging the next phase.

Push the file ***solutions.txt*** to your remote repository frequently as backup.

To avoid accidentally detonating the bomb, you need to single-step through the assembly code in gdb and set breakpoints. You will also need to inspect both the registers and the memory states. One of the nice side-effects of doing the project along with the labs is that you will get very good at using a debugger. This is a crucial skill that will pay big dividends the rest of your career.

During this lab, we strongly recommend that you keep notes of the steps you took in solving each stage. This will be very useful in helping you to keep track of what's stored at important addresses in memory and in registers at different points in the program's execution. (A good strategy for this might be to keep a notetaking app open on your computer so you can copy and paste values between it and gdb.)

---

**Tools:** 

- ``gdb``: You will use the debugger in a variety of ways to step through programs, to examine register and memory state and to disassemble functions.
    - Disassembling within gdb provides the most detailed information. You can disassemble a specific function by specifying it (e.g, `disas phase_2`) or disassemble the current function just by entering `disas`. Notice the arrow pointing to the instruction that's about to execute.
    - You can run x86 instructions one at a time using the `nexti` command.
    - To print the value of a register, use `info register <register name>`. For example, to print the value of %rdi, use `info register rdi`.
    - To use command line arguments with gdb, run your program with `run <command line args>`. So to run your bomb with your solutions file, use `run solution.txt`.
    - Make sure to set breakpoints as in lab 6.
- ``objdump``: You can use this tool to print information about various parts of binary executable programs.
    - ``objdump -t bomb > symtab.out`` prints out the symbol table information in a program which contains the names of all functions and variables, this instance redirects the output to a file named symtab.out.
    - ``objdump -d bomb > asm.out`` disassembles the entire program and redirects the output to a file named asm.out
- ``strings -t x bomb > bomb_strings``: The strings utility prints the printable strings in your bomb and their offset within the bomb, the use of file redirection prints this information into a file called ``bomb_strings``.
- ``less``: Allows you you to look at the contents of text files in the terminal. Of course, you could always use VSCode to inspect the files instead.

---

**Hints:**

If you're still having trouble figuring out what your bomb is doing, here are some hints for what to think about at each stage. Some of them may apply to your bomb, and others might not.

- Comparison
- Loops
- Switch statements
- Recursion
- Pointers and arrays
- Linked lists

---

**Checking your Work:**

When you successfully defuse your binary bomb and run it with your 
solution.txt file, it will print the following:
```
Welcome to my fiendish little bomb. You have 4 phases with
which to blow yourself up. Have a nice day!
Phase 1 defused. How about the next one?
That's number 2.  Keep going!
Almost there, one more to go!
Congratulations! You've defused the bomb!
```

---

**Submission:**

- Submit your project on gradescope using the gitlab submission process.

- ***Team specific instructions*** 
    - *The student submitting must be for the bomb that you solved*.  That is, if solved
binary bomb ``bomb-ab1`` then student with Duke NetID ``ab1@duke.edu`` must be the one submitting to Gradescope.
    - Teams must also edit the file called reflection.txt and include a detailed description of each team members contributions to the project.
