/*
 * CS:APP Binary Bomb (Autolab version)
 *
 * Copyright (c) 2004, R. Bryant and D. O'Hallaron, All rights reserved.
 * May not be used, modified, or copied without permission.
 */ 
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "phases.h"
#include "support.h"

/* Global bomb ID */
int bomb_id = 185;

/* 
 * phase1a.c - The user's input must match the specified string 
 */
void phase_1(char *input)
{
#if defined(PROBLEM)
    if (strings_not_equal(input, "Coach K is retiring.") != 0)
	explode_bomb();
#elif defined(SOLUTION)
    printf("Coach K is retiring.\n"); 
#else
    invalid_phase("1a");
#endif    
}

/* 
 * phase2b.c - To defeat this stage the user must enter the geometric
 * sequence starting at 1, with a factor of 2 between each number
 */
void phase_2(char *input)
{
#if defined(PROBLEM)
    int numbers[4];
    
    read_four_numbers(input, numbers);
    
    if (numbers[0] != 3)
	    explode_bomb();

    if (((numbers[0] * numbers[1]) - numbers[2]) != numbers [3]) {
	    explode_bomb();
    }
#elif defined(SOLUTION)
    printf("3 3 4 5\n");
#else
    invalid_phase("2b");
#endif
}
/* 
 * phase3b.c - A long switch statement that the compiler should
 * implement with a jump table. The user must enter both an index 
 * into the table and the sum accumulated by falling through the rest 
 * of the table 
 */

void phase_3(char *input)
{
#if defined(PROBLEM)
    int index, sum, x = 0;
    int numScanned = 0;

    numScanned = sscanf(input, "%d %d", &sum, &index);

    if (numScanned < 2)
	explode_bomb();

    switch(index) {
    case 0:
	x = x + 543;
    case 1:
	x = x - 1000;
    case 2:
	x = x + 399;
    case 3:
	x = x - 889;
    case 4:
	x = x + 908;
    case 5:
	x = x - 908;
    case 6:
	x = x + 908;
	break;
    default:
	explode_bomb();
    }

    if ((index > 4) || (x != sum))
	explode_bomb();
#elif defined(SOLUTION)
    printf("908 4\n");
#else
    invalid_phase("3b");
#endif
}
/* 
 * phase4b.c - Access an array of structs
 * User inputs index and sum of two fields in structure
 */

typedef struct mystruct {
    long val1, val2;
} MYSTRUCT;
void phase_4(char *input)
{
#if defined(PROBLEM)
    static MYSTRUCT array[] = {{10,20},{5,2},{3,1},{6,4},{15,30},{1,2},{12,12},{8,7}};

    int sum;
    int p, result;
    int numScanned;

    numScanned = sscanf(input, "%d %d", &p, &result);
    
    if (numScanned < 2)
        explode_bomb();

    if (p != 6)
        explode_bomb();
   
 //   p = p & 0x0f;
 //   start = p; /* debug */

    sum = array[p].val1 + array[p].val2;

    if ((sum != result))
	explode_bomb();
#elif defined(SOLUTION)
    switch (6) {
    case 0: printf("0 30"); break;
    case 1: printf("1 7"); break;
    case 2: printf("2 4"); break;
    case 3: printf("3 10"); break;
    case 4: printf("4 45"); break;
    case 5: printf("5 3"); break;
    case 6: printf("6 24"); break;
    case 7: printf("7 15"); break;
    default:
	printf("ERROR: bad index value in phase4b\n");
	exit(8);
    }
    printf("\n");
#else
    invalid_phase("4b");
#endif
}
