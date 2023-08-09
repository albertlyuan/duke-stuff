#include <stdio.h>
#include <stdlib.h> // for atoi

// for
long f1(long a){
    long val = 0;
    int i = 0;
    for (i = 0; i < a; i++){
        val += a*2;
    }
    return val;
}

// switch
long f2(int a){
    long val = 0;
    switch(a) {
        case 0:
            val = 6;
        case 1:
            val = val + 2;
            break;
        case 2:
            val = 4;
            break;
        case 3:
            val = 16;
            break;
        default:
            val = 6;
    }
    return val;
}

// while
int f3(int a){
    int val = 0;
    while (val < a) {
        val = val + 2;
    }
    return val;
}

// if
int f4(int a, int b, int c){
    int val = 0;
    if (c >= b)
        val = a;
    else
        val = b - b;

    return val;
}

int main(int argc, char **argv)
{
    long t1 = 3; 
    int t3 = 4;
    int t2 = 3, t4 = 20, t5 = 13, t6 = 36;
    long f1arg, f2arg1;
    int f3arg1;
    int f4arg1, f4arg2, f4arg3;
    int test = 0;

    // f1 = for (long), f2 = switch (long), f3 = while (int), f4 = if (int)

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
        printf("Enter an integer that causes f2 to return the value %ld\n", f2(t2));
        if (scanf("%ld", &f2arg1) != 1)
        {
            printf("Error in input\n");
            return 1;
        }
        if (f2(f2arg1) == f2(t2))
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
        printf("Enter an integer that causes f3 to return the value %d\n", f3(t3));
        if (scanf("%d", &f3arg1) != 1)
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
    if (test == 4 || test == 0)
    {
        printf("Enter three integers that causes f4 to return the value %d\n", f4(t4,t5,t6));
        if (scanf("%d %d %d", &f4arg1, &f4arg2, &f4arg3) != 3)
        {
            printf("Error in input\n");
            return 1;
        }
        if (f4(f4arg1,f4arg2,f4arg3) == f4(t4,t5,t6))
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