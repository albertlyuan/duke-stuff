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
int bomb_id = 98;

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
	x = x + 77;
    case 1:
	x = x - 486;
    case 2:
	x = x + 59;
    case 3:
	x = x - 778;
    case 4:
	x = x + 650;
    case 5:
	x = x - 650;
    case 6:
	x = x + 650;
	break;
    default:
	explode_bomb();
    }

    if ((index > 4) || (x != sum))
	explode_bomb();
#elif defined(SOLUTION)
    printf("650 4\n");
#else
    invalid_phase("3b");
#endif
}
/* 
 * phase4c.c - Access a linked list, return the value with the specified index
 * User inputs index and value
 */
listNode node6 = {520, 6, NULL};
listNode node5 = {750, 5, &node6};
listNode node4 = {985, 4, &node5};
listNode node3 = {750, 3, &node4};
listNode node2 = {158, 2, &node3};
listNode node1 = {269, 1, &node2};

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

    if (ptr->value != 985)
        explode_bomb();
   
#elif defined(SOLUTION)
    printf("4 985\n");
#else
    invalid_phase("4c");
#endif
}

