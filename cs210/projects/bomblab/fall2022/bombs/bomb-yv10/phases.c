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
int bomb_id = 139;

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
	x = 'e';
	if(num != 262) {
	    explode_bomb();
	}
	break;
    case 1:
	x = 'b';
	if(num != 234) {
	    explode_bomb();
	}
	break;
    case 2:
	x = 'h';
	if(num != 72) {
	    explode_bomb();
	}
	break;
    case 3:
	x = 'm';
	if(num != 559) {
	    explode_bomb();
	}
	break;
    case 4:
	x = 'j';
	if(num != 919) {
	    explode_bomb();
	}
	break;
    case 5:
	x = 'm';
	if(num != 162) {
	    explode_bomb();
	}
	break;
    case 6:
	x = 'z';
	if(num != 699) {
	    explode_bomb();
	}
	break;
    case 7:
	x = 'b';
	if(num != 295) {
	    explode_bomb();
	}
	break;
    default:
	x = 'o'; /* keep gcc happy */
	explode_bomb();
    }
  
    if (x != letter) {
	explode_bomb();
    }
#elif defined(SOLUTION)
    printf("m 559 3\n");
#else
    invalid_phase("3c");
#endif
}

/* 
 * phase4c.c - Access a linked list, return the value with the specified index
 * User inputs index and value
 */
listNode node6 = {280, 6, NULL};
listNode node5 = {104, 5, &node6};
listNode node4 = {579, 4, &node5};
listNode node3 = {338, 3, &node4};
listNode node2 = {375, 2, &node3};
listNode node1 = {999, 1, &node2};

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

    if (ptr->value != 579)
        explode_bomb();
   
#elif defined(SOLUTION)
    printf("4 579\n");
#else
    invalid_phase("4c");
#endif
}

