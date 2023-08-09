#include <stdio.h>
#include <stdlib.h>
int main(int argc, char *argv[])
{
    int d;
    float f;
    char s[20];
    FILE *in = fopen("input1.txt","r");
    fscanf(in, "%d %f %s", &d,&f,s);
    fclose(in);

    FILE *out = fopen("output1.txt","w");
    fprintf(out,"%d\n%f\n%s",d,f,s);       
    fclose(out);
    return 0;
}