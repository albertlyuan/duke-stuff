#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#ifndef BUFFER_SIZE
#define BUFFER_SIZE 4
#endif

// define gets
extern char gets(char *);

void check_string(char *s1)
{
    char s2[] = "Use the force Luke";

    if (!strcmp(s1,s2))
        printf("Success! You correctly hacked the program\n"); 
    else {
        printf("Failed to hack the program\n");
        exit(1);
    }
  
}

void my_bug()
{
    char mystr[] = "This should not be here";
    char array[BUFFER_SIZE] = "ABC";
    gets(array);
    check_string(mystr);
}


int main (int argc, char** argv) {

    my_bug();
    return 0;
}
