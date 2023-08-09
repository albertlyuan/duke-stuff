#include <stdio.h>
#include <stdlib.h>
int recurse(int n);
int main(int argc, char *argv[])
{
    if (argc != 2){
        printf("Error with input.\n");
        return 0;
    }
    int n = atoi(argv[1]);
    int ret = recurse(n);
    printf("%d\n",ret);
    return 0;    
    
}
int recurse(int n)
{
    if (n <= 1) {
        return 1;
    }
    else {
        return recurse(n-1) * (n + recurse(n-2));
    }
}