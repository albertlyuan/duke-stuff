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
int bomb_id = 196;

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
	x = x + 814;
    case 1:
	x = x - 748;
    case 2:
	x = x + 951;
    case 3:
	x = x - 645;
    case 4:
	x = x + 201;
    case 5:
	x = x - 201;
    case 6:
	x = x + 201;
	break;
    default:
	explode_bomb();
    }

    if ((index > 4) || (x != sum))
	explode_bomb();
#elif defined(SOLUTION)
    printf("201 4\n");
#else
    invalid_phase("3b");
#endif
}
/* 
 * phase4c.c - Access a linked list, return the value with the specified index
 * User inputs index and value
 */
listNode node6 = {499, 6, NULL};
listNode node5 = {369, 5, &node6};
listNode node4 = {860, 4, &node5};
listNode node3 = {209, 3, &node4};
listNode node2 = {694, 2, &node3};
listNode node1 = {251, 1, &node2};

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

    if (ptr->value != 860)
        explode_bomb();
   
#elif defined(SOLUTION)
    printf("4 860\n");
#else
    invalid_phase("4c");
#endif
}
