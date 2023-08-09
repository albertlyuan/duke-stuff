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
int bomb_id = 83;

/* 
 * phase1c.c - The user's input must match the specified string 
 */
void phase_1(char *input)
{
#if defined(PROBLEM)
    if (strings_not_equal(input, "John Scheyer is the new mens basketball coach.") != 0)
	explode_bomb();
#elif defined(SOLUTION)
    printf("John Scheyer is the new mens basketball coach.\n");
#else
    invalid_phase("1c");
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
	x = 'b';
	if(num != 648) {
	    explode_bomb();
	}
	break;
    case 1:
	x = 'k';
	if(num != 426) {
	    explode_bomb();
	}
	break;
    case 2:
	x = 'd';
	if(num != 780) {
	    explode_bomb();
	}
	break;
    case 3:
	x = 'd';
	if(num != 721) {
	    explode_bomb();
	}
	break;
    case 4:
	x = 'b';
	if(num != 453) {
	    explode_bomb();
	}
	break;
    case 5:
	x = 'u';
	if(num != 965) {
	    explode_bomb();
	}
	break;
    case 6:
	x = 'x';
	if(num != 475) {
	    explode_bomb();
	}
	break;
    case 7:
	x = 'n';
	if(num != 216) {
	    explode_bomb();
	}
	break;
    default:
	x = 'j'; /* keep gcc happy */
	explode_bomb();
    }
  
    if (x != letter) {
	explode_bomb();
    }
#elif defined(SOLUTION)
    printf("d 721 3\n");
#else
    invalid_phase("3c");
#endif
}

/* 
 * phase4c.c - Access a linked list, return the value with the specified index
 * User inputs index and value
 */
listNode node6 = {406, 6, NULL};
listNode node5 = {515, 5, &node6};
listNode node4 = {152, 4, &node5};
listNode node3 = {188, 3, &node4};
listNode node2 = {865, 2, &node3};
listNode node1 = {511, 1, &node2};

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

    if (ptr->value != 152)
        explode_bomb();
   
#elif defined(SOLUTION)
    printf("4 152\n");
#else
    invalid_phase("4c");
#endif
}
