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
int bomb_id = 53;

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
 * phase2c.c - To defeat this stage the user must enter the first six
 * fibonacci numbers (0, 1, 1, 2, 3, 5)
 */
void phase_2(char *input)
{
#if defined(PROBLEM)
    int numbers[4];

    read_four_numbers(input, numbers);

    if (numbers[0] != 10)
	    explode_bomb();
	if (numbers[0] != numbers[1] - numbers[2] - numbers[3]) {
	    explode_bomb();
    }

#elif defined(SOLUTION)
    printf("10 30 15 5\n");
#else
    invalid_phase("2c");
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
	x = 'i';
	if(num != 398) {
	    explode_bomb();
	}
	break;
    case 1:
	x = 's';
	if(num != 280) {
	    explode_bomb();
	}
	break;
    case 2:
	x = 'q';
	if(num != 996) {
	    explode_bomb();
	}
	break;
    case 3:
	x = 'k';
	if(num != 654) {
	    explode_bomb();
	}
	break;
    case 4:
	x = 'r';
	if(num != 706) {
	    explode_bomb();
	}
	break;
    case 5:
	x = 'k';
	if(num != 138) {
	    explode_bomb();
	}
	break;
    case 6:
	x = 'f';
	if(num != 434) {
	    explode_bomb();
	}
	break;
    case 7:
	x = 'n';
	if(num != 437) {
	    explode_bomb();
	}
	break;
    default:
	x = 'v'; /* keep gcc happy */
	explode_bomb();
    }
  
    if (x != letter) {
	explode_bomb();
    }
#elif defined(SOLUTION)
    printf("k 654 3\n");
#else
    invalid_phase("3c");
#endif
}

/* 
 * phase4c.c - Access a linked list, return the value with the specified index
 * User inputs index and value
 */
listNode node6 = {479, 6, NULL};
listNode node5 = {533, 5, &node6};
listNode node4 = {645, 4, &node5};
listNode node3 = {104, 3, &node4};
listNode node2 = {837, 2, &node3};
listNode node1 = {196, 1, &node2};

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

    if (ptr->value != 645)
        explode_bomb();
   
#elif defined(SOLUTION)
    printf("4 645\n");
#else
    invalid_phase("4c");
#endif
}

