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
int bomb_id = 0;

/* 
 * phase1b.c - The user's input must match the specified string 
 */
void phase_1(char *input)
{
#if defined(PROBLEM)
    if (strings_not_equal(input, "Houston we have a problem.") != 0)
	explode_bomb();
#elif defined(SOLUTION)
    printf("Houston we have a problem.\n");
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
	x = 't';
	if(num != 728) {
	    explode_bomb();
	}
	break;
    case 1:
	x = 'i';
	if(num != 462) {
	    explode_bomb();
	}
	break;
    case 2:
	x = 'u';
	if(num != 952) {
	    explode_bomb();
	}
	break;
    case 3:
	x = 'n';
	if(num != 316) {
	    explode_bomb();
	}
	break;
    case 4:
	x = 'j';
	if(num != 228) {
	    explode_bomb();
	}
	break;
    case 5:
	x = 'g';
	if(num != 126) {
	    explode_bomb();
	}
	break;
    case 6:
	x = 'f';
	if(num != 317) {
	    explode_bomb();
	}
	break;
    case 7:
	x = 'y';
	if(num != 224) {
	    explode_bomb();
	}
	break;
    default:
	x = 'x'; /* keep gcc happy */
	explode_bomb();
    }
  
    if (x != letter) {
	explode_bomb();
    }
#elif defined(SOLUTION)
    printf("n 316 3\n");
#else
    invalid_phase("3c");
#endif
}

/* 
 * phase4c.c - Access a linked list, return the value with the specified index
 * User inputs index and value
 */
listNode node6 = {652, 6, NULL};
listNode node5 = {128, 5, &node6};
listNode node4 = {565, 4, &node5};
listNode node3 = {164, 3, &node4};
listNode node2 = {669, 2, &node3};
listNode node1 = {892, 1, &node2};

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

    if (ptr->value != 565)
        explode_bomb();
   
#elif defined(SOLUTION)
    printf("4 565\n");
#else
    invalid_phase("4c");
#endif
}

