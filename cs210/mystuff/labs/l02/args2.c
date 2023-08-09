#include <stdio.h>
#include <stdlib.h>
int main(int argc, char *argv[])
{
    printf("Enter an integer a float and a string\n");
    int d;
    float f;
    char s[20]; 
    int success = scanf("%d %f %s", &d, &f, s);
    if (!success){printf("Error with input.\n");}
    printf("%d, %f, %s", d, f, s);
    return 0;
}