#include <stdio.h>
#include <stdlib.h>

// multiply by 4
long f1(long a)
{
    return (a * a);
}

// add
long f2(long a, long b)
{
    long val = 0;
    val = (a * 2) + b;
    return val;
}

// three way swap (rotate)
void f3(long *a, long *b, long *c)
{
    long t0 = *a;
    long t1 = *b;
    long t2 = *c;
    *a = t1;
    *b = t2;
    *c = t0;
}

int main(int argc, char **argv)
{
    long r1, r2 = 0;
    long t1 = 4, t2 = 50, t3 = 25, t4 = 10, t5 = 13, t6 = 7;
    long f1arg, f2arg1, f2arg2, f3arg1, f3arg2, f3arg3;
    int test = 0;

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
        if (f1arg == t1)
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
        printf("Enter two integers that cause f2 to return the value %ld\n", f2(t2, t3));
        if (scanf("%ld %ld", &f2arg1, &f2arg2) != 2)
        {
            printf("Error in input\n");
            return 1;
        }
        if (f2(f2arg1, f2arg2) == f2(t2, t3))
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
        f3(&t4, &t5, &t6);
        printf("Enter three integers (a1, a2, a3) that result in values a1 = %ld a2 = %ld a3 = %ld after f3 is executed.\n", t4, t5, t6);
        if (scanf("%ld %ld %ld", &f3arg1, &f3arg2, &f3arg3) != 3)
        {
            printf("Error in input\n");
            return 1;
        }
        if (f3arg1 == 10 && f3arg2 == 13 && f3arg3 == 7)
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