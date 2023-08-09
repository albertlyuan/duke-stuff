This is a simple get started project writing the classic hello world in C.  Please perform the following steps. 
You will repeat these steps (or slight variants) for each lab and project.

1. Fork this repository and mark it private.
2. Clone your newly forked repository into a directory on your OIT 210 container or the directory accessible to your local container.
3. Use Visual Studio Code (VSCode) to create a file in your repository called hello.c with the following contents.

    ```
    #include <stdio.h>

    int main (int argc, char **argv)
    {
    printf("Hello World!\n");
    return (0);
    }
    ```

4. Type _make_ at the command prompt to compile the hello.c file and create the hello exectuable program. You can also directly invoke the compiler by typing _gcc -o hello hello.c_ The make command is useful for more complex situations and we will discuss it in more detail later in the semester.
5. Run the program by typing ./hello at the command prompt.
6. Run the local test_kit by running the following on the unix command line:

    ``python3 test_kit.py ALL``


7. add, commit and push the file hello.c to your git repo:

    ``git add hello.c``

    ``git commit -m "my hello world"``

    ``git push`` 


8. Submit the hello.c file to Gradescope L01 (or use gitlab to submit your code)

What to submit:
Use the gitlab button to submit your solution to gradescope.


