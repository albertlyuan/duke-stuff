/* 
 * phase4c.c - Access a linked list, return the value with the specified index
 * User inputs index and value
 */
listNode node6 = {POSITIVE, 6, NULL};
listNode node5 = {POSITIVE, 5, &node6};
listNode node4 = {POSITIVE_SET, 4, &node5};
listNode node3 = {POSITIVE, 3, &node4};
listNode node2 = {POSITIVE, 2, &node3};
listNode node1 = {POSITIVE, 1, &node2};

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

    if (ptr->value != POSITIVE_GET)
        explode_bomb();
   
#elif defined(SOLUTION)
    printf("4 POSITIVE_GET\n");
#else
    invalid_phase("4c");
#endif
}

