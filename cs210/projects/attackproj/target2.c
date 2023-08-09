#include <stdio.h>
#include <stdlib.h>

#ifndef BUFFER_SIZE
#define BUFFER_SIZE 4
#endif

// define gets
extern char gets(char *);


void callme() {
    printf("Success! You hacked the program!\n");
    exit(1);
}

void my_bug()
{
    char array[BUFFER_SIZE] = "ABC";
    gets(array);
}

int main (int argc, char** argv) {

    my_bug();
    printf("Failure to hack the program\n");

    return 0;
}
