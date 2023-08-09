#include <stdio.h>

// multiply by 2
int f1 (int a) {
    return(a*2);
}

// add 
int f2 (int a, int b) {
    return (a+b);
}

// array for use in f3 and f4
int array1[10] = {2, 4, 6, 8, 10, 12, 14, 16, 18, 20};

// sumarray
int f3 () {
    int retval = 0;
    for (int i=0; i < 10; i++)
        retval += array1[i];
    return (retval);
}

// add to array
void f4 (int n) {
   for (int i=0; i < 10; i++)
        array1[i] += n;
}

int main (int argc, char** argv){
    int r1, r2, r3 = 0;
    int t1 = 6, t2 = 210, t3 = 201, t4 = 5;
    r1 = f1(t1);
    r2 = f2(t2,t3);
    printf("f1(%d) = %d, f2(%d,%d) = %d\n",t1,r1,t2,t3,r2);
    r3 = f3();
    printf("Before f4, f3() = %d\n",r3);
    f4(t4);
    r3 = f3();
    printf("After f4, f3() = %d\n",r3);
    return (1);
}