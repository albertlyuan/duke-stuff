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
int bomb_id = 114;

/* 
 * phase1b.c - The user's input must match the specified string 
 */
void phase_1(char *input)
{
#if defined(PROBLEM)
    if (strings_not_equal(input, "Pointers make my head hurt!") != 0)
	explode_bomb();
#elif defined(SOLUTION)
    printf("Pointers make my head hurt!\n");
#else
    invalid_phase("1b");
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
	x = 'd';
	if(num != 809) {
	    explode_bomb();
	}
	break;
    case 1:
	x = 'u';
	if(num != 394) {
	    explode_bomb();
	}
	break;
    case 2:
	x = 'j';
	if(num != 960) {
	    explode_bomb();
	}
	break;
    case 3:
	x = 'e';
	if(num != 836) {
	    explode_bomb();
	}
	break;
    case 4:
	x = 'j';
	if(num != 211) {
	    explode_bomb();
	}
	break;
    case 5:
	x = 'd';
	if(num != 979) {
	    explode_bomb();
	}
	break;
    case 6:
	x = 'p';
	if(num != 635) {
	    explode_bomb();
	}
	break;
    case 7:
	x = 'l';
	if(num != 567) {
	    explode_bomb();
	}
	break;
    default:
	x = 'c'; /* keep gcc happy */
	explode_bomb();
    }
  
    if (x != letter) {
	explode_bomb();
    }
#elif defined(SOLUTION)
    printf("e 836 3\n");
#else
    invalid_phase("3c");
#endif
}

/* 
 * phase4a.c - Traverses an array from starting index and sum the values.  User inputs
 * location to start and sum.
 */
void phase_4(char *input)
{
#if defined(PROBLEM)
    static int array[] = {
     2,
     4,
     3,
     7,
     0,
     6,
     5,
     1
    };

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

    sum = 0;
    while(p < 8) {
	    sum += array[p];
        p++;
    }

    if ((sum != result))
	explode_bomb();
#elif defined(SOLUTION)
    switch (6) {
    case 0: printf("0 28"); break;
    case 1: printf("1 26"); break;
    case 2: printf("2 22"); break;
    case 3: printf("3 19"); break;
    case 4: printf("4 12"); break;
    case 5: printf("5 12"); break;
    case 6: printf("6 6"); break;
    case 7: printf("7 1"); break;
    default:
	printf("ERROR: bad index value in phase4a\n");
	exit(8);
    }
    printf("\n");
#else
    invalid_phase("4a");
#endif
}

