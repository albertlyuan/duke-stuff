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
int bomb_id = 105;

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
 * phase3a.c - A long switch statement that the compiler should implement
 * with a jump table. The user has to enter both an index into the table 
 * and a value stored there.
 */
void phase_3(char *input)
{
#if defined(PROBLEM)
    int index, val, x = 0;
    int numScanned = 0;

    numScanned = sscanf(input, "%d %d", &val, &index);

    if (numScanned < 2)
	explode_bomb();

    switch(index) {
    case 0:
	x = 850;
	break;
    case 1:
	x = 422;
	break;
    case 2:
	x = 67;
	break;
    case 3:
	x = 241;
	break;
    case 4:
	x = 726;
	break;
    case 5:
	x = 548;
	break;
    case 6:
	x = 68;
	break;
    case 7:
	x = 294;
	break;
    default:
	explode_bomb();
    }

    if (x != val)
	explode_bomb();
#elif defined(SOLUTION)
    printf("68 6\n");
#else
    invalid_phase("3a");
#endif
}
/* 
 * phase4c.c - Access a linked list, return the value with the specified index
 * User inputs index and value
 */
listNode node6 = {894, 6, NULL};
listNode node5 = {970, 5, &node6};
listNode node4 = {984, 4, &node5};
listNode node3 = {393, 3, &node4};
listNode node2 = {927, 2, &node3};
listNode node1 = {700, 1, &node2};

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

    if (ptr->value != 984)
        explode_bomb();
   
#elif defined(SOLUTION)
    printf("4 984\n");
#else
    invalid_phase("4c");
#endif
}

