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
int bomb_id = 128;

/* 
 * phase1a.c - The user's input must match the specified string 
 */
void phase_1(char *input)
{
#if defined(PROBLEM)
    if (strings_not_equal(input, "Houston we have a problem.") != 0)
	explode_bomb();
#elif defined(SOLUTION)
    printf("Houston we have a problem.\n"); 
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
	x = x + 901;
    case 1:
	x = x - 438;
    case 2:
	x = x + 353;
    case 3:
	x = x - 368;
    case 4:
	x = x + 176;
    case 5:
	x = x - 176;
    case 6:
	x = x + 176;
	break;
    default:
	explode_bomb();
    }

    if ((index > 4) || (x != sum))
	explode_bomb();
#elif defined(SOLUTION)
    printf("176 4\n");
#else
    invalid_phase("3b");
#endif
}
/* 
 * phase4c.c - Access a linked list, return the value with the specified index
 * User inputs index and value
 */
listNode node6 = {179, 6, NULL};
listNode node5 = {492, 5, &node6};
listNode node4 = {546, 4, &node5};
listNode node3 = {428, 3, &node4};
listNode node2 = {898, 2, &node3};
listNode node1 = {678, 1, &node2};

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

    if (ptr->value != 546)
        explode_bomb();
   
#elif defined(SOLUTION)
    printf("4 546\n");
#else
    invalid_phase("4c");
#endif
}

