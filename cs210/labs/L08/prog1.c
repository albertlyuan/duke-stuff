#include <stdio.h>

long math_64(long a, long b, long c){
    long val = 0;
    val = a + b;
    val = val * c;
    return val;
}

int math_32(int a, int b, int c){
    int val = 0;
    val = a + b;
    val = val * c;
    return val;
}

void swap_long(long *a, long *b){
    long t0 = *a;
    long t1 = *b;
    *a = t1;
    *b = t0; 
}

int main (int argc, char** argv){
    long x, y, z, foo;
    int i1, i2, i3, bar;
    x = 6; y = 12; z = 7;
    i1 = 6; i2 = 12; i3 = 7;

    foo = math_64(x,y,z);
    printf("x %ld, y %ld, z %ld, foo %ld\n", x,y,z,foo);

    bar = math_32(i1,i2,i3);
    printf("i1 %d, i2 %d, i3 %d, bar %d\n", i1,i2,i3,bar);

    swap_long(&x,&y);
    printf("x %ld, y %ld\n", x,y);
    return 0;
}
