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
int bomb_id = 40;

/* 
 * phase1a.c - The user's input must match the specified string 
 */
void phase_1(char *input)
{
#if defined(PROBLEM)
    if (strings_not_equal(input, "This is an easy phase.") != 0)
	explode_bomb();
#elif defined(SOLUTION)
    printf("This is an easy phase.\n"); 
#else
    invalid_phase("1a");
#endif    
}

/* 
 * phase2a.c - To defeat this stage the user must enter a sequence of 
 * 6 nonnegative numbers where x[i] = x[i-1] + i
 */
void phase_2(char *input)
{
#if defined(PROBLEM)
    int numbers[4];

    read_four_numbers(input, numbers);

    if (numbers[3] != 10)
	    explode_bomb();

    if (numbers[0] + numbers[1] + numbers[2] != numbers[3]) {
	    explode_bomb();
    }
#elif defined(SOLUTION)
    printf("2 3 5 10\n");
#else
    invalid_phase("2a");
#endif
}
/* 
 * phase3c.c - A long switch statement that the compiler should implement
 * with a jump table. The user has to enter both an index into the table
 * and the character stored at that position in the table as well
 * as a number to be compared. 
 */
void phase_3(char *input) 
{
#if defined(PROBLEM)
    int index, num, numScanned = 0;
    char x, letter;
  
    numScanned = sscanf(input, "%c %d %d", &letter, &num, &index);
  
    if (numScanned < 3) {
	explode_bomb();
    }

    switch(index) {
    case 0:
	x = 'n';
	if(num != 726) {
	    explode_bomb();
	}
	break;
    case 1:
	x = 'c';
	if(num != 567) {
	    explode_bomb();
	}
	break;
    case 2:
	x = 'b';
	if(num != 817) {
	    explode_bomb();
	}
	break;
    case 3:
	x = 'g';
	if(num != 526) {
	    explode_bomb();
	}
	break;
    case 4:
	x = 'c';
	if(num != 455) {
	    explode_bomb();
	}
	break;
    case 5:
	x = 'v';
	if(num != 512) {
	    explode_bomb();
	}
	break;
    case 6:
	x = 'i';
	if(num != 778) {
	    explode_bomb();
	}
	break;
    case 7:
	x = 'h';
	if(num != 497) {
	    explode_bomb();
	}
	break;
    default:
	x = 'p'; /* keep gcc happy */
	explode_bomb();
    }
  
    if (x != letter) {
	explode_bomb();
    }
#elif defined(SOLUTION)
    printf("g 526 3\n");
#else
    invalid_phase("3c");
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

    if (p != 5)
        explode_bomb();
   
 //   p = p & 0x0f;
 //   start = p; /* debug */

    sum = array[p].val1 + array[p].val2;

    if ((sum != result))
	explode_bomb();
#elif defined(SOLUTION)
    switch (5) {
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
