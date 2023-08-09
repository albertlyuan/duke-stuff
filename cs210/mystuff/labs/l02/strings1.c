#include <stdio.h>
#include <stdlib.h>
#include <string.h>
int main(int argc, char *argv[])
{
    char str1[32];
    char str2[32];
    char outp[32];

    printf("Enter string 1.\n");
    scanf("%s",str1);
    printf("Enter string 2.\n");
    scanf("%s",str2);
    
    printf("%s,%s,%s%s\n",str1,str2,str1,str2);
    return 0;

}