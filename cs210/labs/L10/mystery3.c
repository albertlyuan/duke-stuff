#include <stdio.h>
#include <stdlib.h> // for atoi



// leaf procedure since it does not call any other procedures/functions
long llf(long a, long *b){
    long val = 0;
    *b = a - *b;    // changes what a points to
    val = *b + 2;   // uses new value to compute return value
    return val;
}

// call leaf1 to both return a value and modify a local variable.
long f1(long x) {
    long val = 3;
    long tmp = 6;
    val = val + llf(x,&tmp) + tmp;  // pass address of tmp so leaf1 can modify as needed
    return val;
}

long f2(long a, long *b, long c, long d, long e, long *f, long g, long h){
    long val = 0;
    val = a + *b -c + d + e - *f + g - h;

    return val;
}

// recursion
long f3(long a){
    long val = 0;
    if (a <= 1)
        return 1;
    else
        val = f3(a-1) + a - 1;
    return val;
}


int main(int argc, char **argv)
{
    long t1, t3, foo, a1,a2,a3,a4,a5,a6,a7,a8;
    long f1arg, f3arg1;
    long f2arg1, f2arg2, f2arg3, f2arg4, f2arg5, f2arg6, f2arg7, f2arg8;
    int test;
    t1 = 5; t3 = 6;
    a1 = 5; a2 = 4; a3 = 4; a4 = 1; a5 = 10; a6 = 2; a7 = 3; a8 = 20;
    
    if (argc == 2)
    {
        test = atoi(argv[1]);
    }

    if (test == 1 || test == 0)
    {
        printf("Enter an integer that causes f1 to return the value %ld\n", f1(t1));
        if (scanf("%ld", &f1arg) != 1)
        {
            printf("Error in input\n");
            return 1;
        }
        if (f1(f1arg) == f1(t1))
        {
            printf("Success!\n");
        }
        else
        {
            printf("Sorry try again...exiting\n");
            return 1;
        }
    }
    if (test == 2 || test == 0)
    {
        printf("Enter 8 integers that causes f2 to return the value %ld\n", f2(a1,&a2,a3,a4,a5,&a6,a7,a8));
        if (scanf("%ld %ld %ld %ld %ld %ld %ld %ld", &f2arg1, &f2arg2, &f2arg3, &f2arg4, &f2arg5, &f2arg6, &f2arg7, &f2arg8 ) != 8)
        {
            printf("Error in input\n");
            return 1;
        }
        if (f2(f2arg1, &f2arg2, f2arg3, f2arg4, f2arg5, &f2arg6, f2arg7, f2arg8) == f2(a1,&a2,a3,a4,a5,&a6,a7,a8))
        {
            printf("Success!\n");
        }
        else
        {
            printf("Sorry try again...exiting\n");
            return 1;
        }
    }
    if (test == 3 || test == 0)
    {
        printf("Extra Credit: Enter an integer that causes f3 to return the value %ld\n", f3(t3));
        if (scanf("%ld", &f3arg1) != 1)
        {
            printf("Error in input\n");
            return 1;
        }
        if (f3(f3arg1) == f3(t3))
        {
            printf("Success!\n");
        }
        else
        {
            printf("Sorry try again...exiting\n");
            return 1;
        }
    }
    return 0;
}
