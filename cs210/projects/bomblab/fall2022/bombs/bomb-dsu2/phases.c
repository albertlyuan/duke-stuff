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
int bomb_id = 145;

/* 
 * phase1c.c - The user's input must match the specified string 
 */
void phase_1(char *input)
{
#if defined(PROBLEM)
    if (strings_not_equal(input, "Coach K is retiring.") != 0)
	explode_bomb();
#elif defined(SOLUTION)
    printf("Coach K is retiring.\n");
#else
    invalid_phase("1c");
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
	x = 'm';
	if(num != 885) {
	    explode_bomb();
	}
	break;
    case 1:
	x = 't';
	if(num != 189) {
	    explode_bomb();
	}
	break;
    case 2:
	x = 'x';
	if(num != 322) {
	    explode_bomb();
	}
	break;
    case 3:
	x = 'o';
	if(num != 867) {
	    explode_bomb();
	}
	break;
    case 4:
	x = 's';
	if(num != 501) {
	    explode_bomb();
	}
	break;
    case 5:
	x = 'a';
	if(num != 488) {
	    explode_bomb();
	}
	break;
    case 6:
	x = 'j';
	if(num != 345) {
	    explode_bomb();
	}
	break;
    case 7:
	x = 'a';
	if(num != 290) {
	    explode_bomb();
	}
	break;
    default:
	x = 'h'; /* keep gcc happy */
	explode_bomb();
    }
  
    if (x != letter) {
	explode_bomb();
    }
#elif defined(SOLUTION)
    printf("o 867 3\n");
#else
    invalid_phase("3c");
#endif
}

/* 
 * phase4c.c - Access a linked list, return the value with the specified index
 * User inputs index and value
 */
listNode node6 = {196, 6, NULL};
listNode node5 = {183, 5, &node6};
listNode node4 = {163, 4, &node5};
listNode node3 = {81, 3, &node4};
listNode node2 = {715, 2, &node3};
listNode node1 = {865, 1, &node2};

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

    if (ptr->value != 163)
        explode_bomb();
   
#elif defined(SOLUTION)
    printf("4 163\n");
#else
    invalid_phase("4c");
#endif
}

