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
int bomb_id = 103;

/* 
 * phase1c.c - The user's input must match the specified string 
 */
void phase_1(char *input)
{
#if defined(PROBLEM)
    if (strings_not_equal(input, "Why is 210 now an alternative to 250?") != 0)
	explode_bomb();
#elif defined(SOLUTION)
    printf("Why is 210 now an alternative to 250?\n");
#else
    invalid_phase("1c");
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
	x = 'q';
	if(num != 938) {
	    explode_bomb();
	}
	break;
    case 1:
	x = 'h';
	if(num != 939) {
	    explode_bomb();
	}
	break;
    case 2:
	x = 'u';
	if(num != 344) {
	    explode_bomb();
	}
	break;
    case 3:
	x = 'z';
	if(num != 546) {
	    explode_bomb();
	}
	break;
    case 4:
	x = 'x';
	if(num != 818) {
	    explode_bomb();
	}
	break;
    case 5:
	x = 'a';
	if(num != 688) {
	    explode_bomb();
	}
	break;
    case 6:
	x = 'h';
	if(num != 266) {
	    explode_bomb();
	}
	break;
    case 7:
	x = 'e';
	if(num != 969) {
	    explode_bomb();
	}
	break;
    default:
	x = 'n'; /* keep gcc happy */
	explode_bomb();
    }
  
    if (x != letter) {
	explode_bomb();
    }
#elif defined(SOLUTION)
    printf("z 546 3\n");
#else
    invalid_phase("3c");
#endif
}

/* 
 * phase4c.c - Access a linked list, return the value with the specified index
 * User inputs index and value
 */
listNode node6 = {653, 6, NULL};
listNode node5 = {994, 5, &node6};
listNode node4 = {237, 4, &node5};
listNode node3 = {951, 3, &node4};
listNode node2 = {53, 2, &node3};
listNode node1 = {202, 1, &node2};

void phase_4(char *input)
{
#if defined(PROBLEM)
    listNode *ptr;
    int idx, value;
    int numScanned;

    numScanned = sscanf(input, "%d %d", &idx, &value);
    
    if (numScanned < 2)
        explode_bomb();

    if (idx != 4) // hard code with value above
        explode_bomb();
    
    ptr = &node1;
    while(ptr != NULL) {
        if (ptr->index == idx && ptr->value == value)
            break;
        ptr = ptr->next;
    }
    if (!ptr)
        explode_bomb();

    if (ptr->value != 237)
        explode_bomb();
   
#elif defined(SOLUTION)
    printf("4 237\n");
#else
    invalid_phase("4c");
#endif
}

