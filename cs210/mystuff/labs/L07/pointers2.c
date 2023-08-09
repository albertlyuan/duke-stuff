#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <stdint.h>
#include <string.h>

#include "CuTest.h"

/**
 * Returns a new, dynamically allocated string whose value
 * is str1 and str2 appended together (str1 comes first).
 * See test code below for an example usage
 */

char* stringcat(char* str1, char* str2) {
    // TODO: Implement the function
    long newLen = strlen(str1) + strlen(str2) + 1;
    char* str1and2 = (char *)malloc(newLen*sizeof(char));
    strcat(str1and2,str1);
    strcat(str1and2,str2);
    return str1and2;
}

/**
 * TODO: Modify the test_stringcat function to remove memory leaks
 */

void test_stringcat(CuTest *tc) {
    char* output = NULL;

    output = stringcat("compsci", "210");
    CuAssertStrEquals(tc, "compsci210", output);
    free(output);


    output = stringcat("The weather in Durham is...", "hot and humid in the summer!");
    CuAssertStrEquals(tc, "The weather in Durham is...hot and humid in the summer!", output);
    free(output);

}

/**
 * The next functions utilize a simple linked list struct shown below.  
 * Functions that make and destroy the list is also provided below
 */

struct ll_node {
    int val;
    struct ll_node *next;
};

struct ll_node* make_sample_list(int *values, int size) {

    if(size == 0)
        return NULL;
    struct ll_node *new_node = malloc(sizeof(struct ll_node));
    (*new_node).val = *values;
    //using recursion isn't most efficient, but it is simple
    struct ll_node* rest_of_list = make_sample_list(values+1, size - 1);
    (*new_node).next = rest_of_list;
    return new_node;
}

void free_list(struct ll_node* head) {
    if(head == NULL)
        return;
    free_list((*head).next);
    free(head);
}

/** 
 * Computes the sum of all values in the list.  An empty list is a NULL head 
 * and its sum is 0.
 * 
 * Look at the test cases for example of usage
 */

int sum_linked_list(struct ll_node* head) {
    // TODO: Implement the function
    int total = 0;
    while (head != NULL){
        total += head->val;
        head = head->next;
    }
    return total;
}

void test_sum_linked_list(CuTest *tc) {

    int list1[] = {1,1,2};
    struct ll_node* head = make_sample_list(list1, 3);
    CuAssertIntEquals(tc, 4, sum_linked_list(head));
    free_list(head);

    int list2[] = {1,1,1,1,100,20};
    head = make_sample_list(list2, 6);
    CuAssertIntEquals(tc, 124, sum_linked_list(head));
    free_list(head);

    head = NULL;
    CuAssertIntEquals(tc, 0, sum_linked_list(head));    
}

/**
 * Compares 2 linked lists for equality.
 * 
 * Returns 0 if the lists have different values.
 * 
 * Returns 1 if the lists are equal but fully distinct in memory (i.e. they have no nodes in common)
 * 
 * Returns 2 if they share some nodes but not all nodes in memory.
 * (Note that this can only happen if they have different prefixes that eventually merge into 1 list.)
 * 
 * Returns 3 if they are the same list in memory.  
 * (Note that 2 empty lists are considered to be equal in memory BUT 
 * the two distinct but equal lists can still end in null and 
 * still be considered distinct.)
 */

int linked_list_equals(struct ll_node* head1, struct ll_node* head2) {
    // TODO: Implement the function
    struct ll_node* curr1 = head1;
    struct ll_node* curr2 = head2;
    while (curr1 != NULL){
        if (curr2 == NULL){
            //printf("%d\n",0);
            return 0;
        }
        //printf("curr1 %d curr2 %d\n",curr1->val,curr2->val);

        if (curr1->val != curr2->val){
            //printf("%d\n",0);
            return 0;
        }
        curr1 = curr1->next;
        curr2 = curr2->next;

    }
    if (curr2 != NULL){
        // printf("%d\n",0);
        return 0;
    }
    int sameMem = 0;
    int totalNode = 0;
    while (head1 != NULL){
        totalNode += 1;
        if (head1 == head2){
            sameMem += 1;
        }
        head1 = head1->next;
        head2 = head2->next;
    }
    if (sameMem == 0){
        // printf("%d\n",1);
        return 1;
    }
    if (sameMem < totalNode){
        // printf("%d\n",2);
        return 2;
    }
    // printf("%d\n",3);
    return 3;
}

void test_linked_list_equality(CuTest *tc) {

    int list1[] = {1,1,2};
    int list2[] = {1,1,3};
    struct ll_node* head1 = make_sample_list(list1, 3);
    struct ll_node* head2 = make_sample_list(list2, 3);
        
    CuAssertIntEquals(tc, 0, linked_list_equals(head1, head2));
    free_list(head1);
    free_list(head2);

    int list3[] = {1,1,2,4};
    int list4[] = {1,1,2};
    head1 = make_sample_list(list3, 4);
    head2 = make_sample_list(list4, 3);
        
    CuAssertIntEquals(tc, 0, linked_list_equals(head1, head2));
    free_list(head1);
    free_list(head2);

    int list5[] = {1,1,2,4};
    head1 = make_sample_list(list5, 4);
    // this will make a memory distinct list
    head2 = make_sample_list(list5, 4);
        
    CuAssertIntEquals(tc, 1, linked_list_equals(head1, head2));
    free_list(head1);
    free_list(head2);

    int list6[] = {1,1,2,4};
    struct ll_node* shared_list = make_sample_list(list6, 4);
    struct ll_node extra1, extra2;
    extra1.val = 0;
    extra1.next = shared_list;
    extra2.val = 0;
    extra2.next = shared_list;
    CuAssertIntEquals(tc, 2, linked_list_equals(&extra1, &extra2));

    CuAssertIntEquals(tc, 3, linked_list_equals(shared_list, shared_list));
    free_list(shared_list);

}


/**
 * Makes a chain of linked list nodes in memory, with each entry pointing to
 * the next.  The memory to use is provided by the memory pointer - you
 * can assume there is enough space to allocate the whole list.  The
 * other parameter is the number of list items to create.
 * 
 * Note that on the course container, the size of struct ll_nodes is 16 bytes.
 * The created list should have values 1 2 3...num_items.  The list
 * should end with NULL as a normal linked list.  The function returns
 * the head of the list, which should always be memory unless num_items
 * is zero in which case the function should return NULL.
 */

struct ll_node* make_chain_in_memory(void* memory, int num_items) {
    // TODO: Implement the function
    if(num_items == 0){
        return NULL;
    }
    struct ll_node *head = memory;

    head->val = 1;
    head->next = NULL;
    struct ll_node *currNode = head;

    for (int i=2; i<=num_items; i++){
        struct ll_node *nextNode = memory + 16*(i-1);
        nextNode->val = i;
        nextNode->next = NULL;
        currNode->next = nextNode;
        currNode = currNode->next;
    }
    return head;
}

void test_make_chain_in_memory(CuTest *tc) {
    uint64_t listdata[100]; // enough space for 50 list items
    
    struct ll_node* head = make_chain_in_memory(listdata, 3);
        
    CuAssertIntEquals(tc, 1, head->val);
    CuAssertIntEquals(tc, 2, head->next->val);
    CuAssertIntEquals(tc, 3, head->next->next->val);
    CuAssertPtrEquals(tc, NULL, head->next->next->next);

    //now lets check locations
    CuAssertPtrEquals(tc, listdata, head);
    CuAssertPtrEquals(tc, listdata + 2, head->next);
    CuAssertPtrEquals(tc, listdata + 4, head->next->next);

    head = make_chain_in_memory(listdata, 0);
    CuAssertPtrEquals(tc, NULL, head);

    head = make_chain_in_memory(listdata, 1);
    CuAssertPtrEquals(tc, NULL, head->next);
    CuAssertIntEquals(tc, 1, head->val);

    head = make_chain_in_memory(listdata, 20);
    struct ll_node* cur = head;
        
    for(int i = 0; i < 18; i++) {
        cur = cur->next;
    }
    CuAssertIntEquals(tc, 19, cur->val);
    CuAssertPtrEquals(tc, listdata + 38, cur->next);
    
}

int main(int argc, char *argv[]) {
    
    CuString *output = CuStringNew();
    CuSuite* suite = CuSuiteNew();

    if (argc < 2) {
            printf("Usage: pointers2 <testname> \ntest names:\n");
            printf("all\nstringcat\nsum_linked_list\nlinked_list_equality\nmake_chain_in_memory\n");
                        return(-1);
    }
    if (!strcmp(argv[1], "all")) {
        SUITE_ADD_TEST(suite, test_stringcat);
        SUITE_ADD_TEST(suite, test_sum_linked_list);
        SUITE_ADD_TEST(suite, test_linked_list_equality);
        SUITE_ADD_TEST(suite, test_make_chain_in_memory);
    } else if (!strcmp(argv[1], "stringcat")) {
        SUITE_ADD_TEST(suite, test_stringcat);} 
    else if (!strcmp(argv[1], "sum_linked_list")) {
        SUITE_ADD_TEST(suite, test_sum_linked_list);
    } else if (!strcmp(argv[1], "linked_list_equality")) {
        SUITE_ADD_TEST(suite, test_linked_list_equality);
    } else if (!strcmp(argv[1], "make_chain_in_memory")) {
        SUITE_ADD_TEST(suite, test_make_chain_in_memory);
    } 

    CuSuiteRun(suite);
    CuSuiteSummary(suite, output);
    CuSuiteDetails(suite, output);
    printf("%s\n", output->buffer);
    CuStringDelete(output);
    CuSuiteDelete(suite);
    return 0;
}
