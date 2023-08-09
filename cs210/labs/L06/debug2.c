#include <stdio.h>

// multiply by 4
int f1 (int a) {
    return(a*4);
}

// add times 2
int f2 (int a, int b) {
    return (a+b*2);
}

// array for use in f3 and f4
int array1[6] = {1,3,5,7,9,11};

// sumarray
int f3 () {
    int retval = 0;
    for (int i=0; i < 6; i++)
        retval += array1[i];
    return (retval+1);
}

// add to array plus i
void f4 (int n) {
   for (int i=0; i < 6; i++)
        array1[i] += i+n;
}

int main (int argc, char** argv){
    int r1, r2, r3 = 0;
    int t1 = 4, t2 = 50, t3 = 25, t4 = 3;
    r1 = f1(t1);
    r2 = f2(t2,t3);
#ifndef STUDENT
    printf("f1(%d) = %d, f2(%d,%d) = %d\n",t1,r1,t2,t3,r2);
#endif
    r3 = f3();
#ifndef STUDENT
    printf("Before f4, f3() = %d\n",r3);
#endif
    f4(t4);
    r3 = f3();
#ifndef STUDENT
    printf("After f4, f3() = %d\n",r3);
#endif
    return (1);
}