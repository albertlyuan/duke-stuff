#include <stdio.h>
#include <stdlib.h>
int main(int argc, char *argv[])
{
    if (argc != 4){
        printf("Error not enough inputs!\n");
    }
    else{
        int d = atoi(argv[1]);
        float f = atof(argv[2]);
        printf("%d, %f, %s",d,f,argv[3]);
    }
    return 0;
}