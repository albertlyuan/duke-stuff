#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <stdint.h>
#include <string.h>

#include "CuTest.h"

/*

This function takes two pointers to integers, and returns the sum of
the integers being pointed to.

Look at the test cases for example of usage

 */
int add_with_pointers(int *val1, int *val2) {
#ifdef STUDENT
    return 2;
#else
    return *val1 + *val2;
#endif
}

void test_add_with_pointers(CuTest *tc) {
    int a = 7;
    int b = 11;
    CuAssertIntEquals(tc, 18, add_with_pointers(&a, &b));
    CuAssertIntEquals(tc, 22, add_with_pointers(&b, &b));
}




/*

This function takes an integer as a parameter.  It returns the address
of that integer in the global arrayForFinding or NULL if that integer
is not in the array.

Note: arrayForFinding is zero delimited - its last element is always 0
and 0 is not a valid element elsewhere in the array.  You'll want to
use this information to know when to stop searching.

Look at the test cases for example of usage
 */

int arrayForFinding[] = { 7, 8, 13, 19, 3, 0 };

int* address_in_array(int value_to_find) {
#ifdef STUDENT
    return 2;
#else    
    int* cur = arrayForFinding;
    while(*cur != 0) {
        if(*cur == value_to_find) return cur;
        cur++;
    }
    return NULL;
#endif
}

void test_address_in_array(CuTest *tc) {
    // the cast in the test cases here isn't necessary due to the weird way
    // C arrays have dual nature, but I think it makes it more obvious what
    // is going on

    // I could also get the address of a particular position in the array like this
    // &arrayForFinding[3]
    
    CuAssertPtrEquals(tc, (int*) arrayForFinding, address_in_array(7));
    CuAssertPtrEquals(tc, (int*) arrayForFinding + 1 , address_in_array(8));
    CuAssertPtrEquals(tc, (int*) arrayForFinding + 4 , address_in_array(3));
    CuAssertPtrEquals(tc, NULL , address_in_array(777));

}

/*

This function takes an integer array with 2 fours in it.  It returns
the number of bytes between the pair of 4s.  If the 4s are
consecutive, the function should return 0.  If there was 1 value in
between the 4s, the function should return the size of an integer.
You'll want to use pointer subtraction here, among other things.  You
can use sizeof(int) if you like, or maybe some clever casting.  I
encourage you to try and figure out both ways!

MIKE NOTES: I was hoping this problem would require pointer subtraction, but it doesn't

 */

int bytes_between_4s(int* array) {
#ifdef STUDENT
    return 2;
#else
    int first = -1;
    int i = 0;
    while(1) {
        if(array[i] == 4) {
            first = i;
            break;
        }
        i++;
    }
    i++;

    while(1) {
        if(array[i] == 4) {
            return (i - (first + 1))*sizeof(int);
        }
        i++;
    }
#endif
}

void test_bytes_between_4s(CuTest *tc) {

    int onedist[] = {4,1,4};
    CuAssertIntEquals(tc, sizeof(int), bytes_between_4s(onedist));
    int threedist[] = {7,4,9,9,77,4,12};
    CuAssertIntEquals(tc, 3*sizeof(int), bytes_between_4s(threedist));
    int zerodist[] = {1,4,4};
    CuAssertIntEquals(tc, 0, bytes_between_4s(zerodist));
}

/*

This function takes 2 (valid) pointers to integers and compares the
value they point to determine the smallest.  Then it sets both
integers to that value.

 */

void point_to_smallest(int* first, int* second) {
#ifdef STUDENT
    return 2;
#else
    if(*first < *second) {
        *second = *first;
    } else {
        *first = *second;
    }
#endif
}

void test_point_to_smallest(CuTest *tc) {

    int a, b;
    a = 7;
    b = 8;
    point_to_smallest(&a, &b);
    CuAssertIntEquals(tc, 7, a);
    CuAssertIntEquals(tc, 7, b);

    a = 10;
    b = 4;

    point_to_smallest(&a, &b);
    CuAssertIntEquals(tc, 4, a);
    CuAssertIntEquals(tc, 4, b);

}

/*

This function takes 2 int**.  It compares the integers being pointed
at, and then sets the pointers to the address of the smaller of the
two integers.  If the values are equal, both pointers should be set to
the address of the first pointer.

Either of the pointers being passed in might point to NULL instead of
a valid integer - in that case the other value is implicitly the
smallest and both pointers should be set to point to the non-null
value.  You can assume both pointers will not be NULL.

 */


void point_to_smallest_ptr(int** first, int** second) {
#ifdef STUDENT
    return 2;
#else
    if(*first == NULL) *first = *second;
    if(*second == NULL) *second = *first;

    if(**first <= **second) {
        *second = *first; 
    } else {
        *first = *second;
    }
#endif
}


void test_point_to_smallest_ptr(CuTest *tc) {

    int a, b, *a_ptr, *b_ptr;
    a = 7;
    b = 8;
    a_ptr = &a;
    b_ptr = &b;
    point_to_smallest_ptr(&a_ptr, &b_ptr);
    CuAssertIntEquals(tc, 7, *a_ptr);
    CuAssertIntEquals(tc, 7, *b_ptr);

    a = 11;
    b = 11;
    a_ptr = &a;
    b_ptr = &b;
    point_to_smallest_ptr(&a_ptr, &b_ptr);
    CuAssertPtrEquals(tc, &a, a_ptr);
    CuAssertPtrEquals(tc, &a, b_ptr);

    
    a = 10;
    b = 4;
    a_ptr = &a;
    b_ptr = &b;

    point_to_smallest_ptr(&a_ptr, &b_ptr);
    CuAssertIntEquals(tc, 4, *a_ptr);
    CuAssertIntEquals(tc, 4, *b_ptr);

    
    a_ptr = &a;
    b_ptr = NULL;

    point_to_smallest_ptr(&a_ptr, &b_ptr);
    CuAssertIntEquals(tc, 10, *a_ptr);
    CuAssertIntEquals(tc, 10, *b_ptr);    

    
    a_ptr = NULL;
    b_ptr = &b;

    point_to_smallest_ptr(&a_ptr, &b_ptr);
    CuAssertIntEquals(tc, 4, *a_ptr);
    CuAssertIntEquals(tc, 4, *b_ptr);    

}

/*

This function takes a pointer to a integer value and a (assumed
non-negative) power.  The function modifies the given value by taking
it to the stated power.

 */
void power_replace(int* val, int power) {
#ifdef STUDENT
    return 2;
#else
    int output = 1;
    for(int i = 0; i < power; i++) {
        output = output * *val;
    }
    *val = output;
#endif
}

void test_power_replace(CuTest *tc) {
    int val = 2;

    power_replace(&val, 3);
    CuAssertIntEquals(tc, 8, val);
    power_replace(&val, 2);
    CuAssertIntEquals(tc, 64, val);
    power_replace(&val, 1);
    CuAssertIntEquals(tc, 64, val);
    power_replace(&val, 0);
    CuAssertIntEquals(tc, 1, val);
}

/*

This function takes two integer pointers and computes a^3 + b^3, where
a and b are the two integer values pointed at.  BUT the computation
must be done with the already written power_replace function AND the
values of the initial parameters a and b must not be changed (hint:
make some copies).

 */

int power_sum_dont_replace(int* a, int* b) {
#ifdef STUDENT
    return 2;
#else
    int my_a = *a;
    int my_b = *b;
    power_replace(&my_a,3);
    power_replace(&my_b,3);
    
    return my_a + my_b;
#endif
}

void test_power_sum_dont_replace(CuTest *tc) {
    int a = 2;
    int b = 3;
    int result = power_sum_dont_replace(&a, &b);
    CuAssertIntEquals(tc, 35, result);
    CuAssertIntEquals(tc, 2, a);
    CuAssertIntEquals(tc, 3, b);

    a = 4;
    b = 1;
    result = power_sum_dont_replace(&a, &b);
    CuAssertIntEquals(tc, 65, result);
    CuAssertIntEquals(tc, 4, a);
    CuAssertIntEquals(tc, 1, b);
    
    
}

/*

This function takes two strings as parameters, only 1 of which will
contain a lowercase 'q'.  The function puts the the string with a q
into the address pointed at by the output pointer.

For example:

char *a = "foo"
char *b = "q"
char *c;

set_string_with_q(a, b, &c);
// now c == b


 */


void set_string_with_q(char* s1, char* s2, char** output) {
 #ifdef STUDENT
    return 2;
#else 
    int cur = 0;
    while(s1[cur] != 0) {
        if(s1[cur] == 'q') {
            *output = s1;
            return;
        }
        cur++;
    }
    *output = s2;
#endif
}

void test_set_string_with_q(CuTest *tc) {

    char* q_str = "abcq";
    char* no_q_str = "xy";
    char* output;

    set_string_with_q(q_str, no_q_str, &output);
    CuAssertPtrEquals(tc, q_str, output);
    set_string_with_q(no_q_str, q_str, &output);
    CuAssertPtrEquals(tc, q_str, output);
    
    char* q_str2 = "qabc";
    char* no_q_str2 = "abcdef";

    set_string_with_q(no_q_str2, q_str2, &output);
    CuAssertPtrEquals(tc, q_str2, output);
    

}

/* Takes an array of strings and a size.

Sets elements of the array to NULL if the second character of the
referenced string is a q.  You can assume no element of the array is
initially NULL.

*/

void remove_2nd_q_strings(char** array, int size) {
#ifdef STUDENT
    return 2;
#else
    for(int i = 0; i < size; i++) {
        if(array[i][1] == 'q') array[i] = NULL;
    }
#endif
}

void test_remove_2nd_q_strings(CuTest *tc) {

    char* q_1st = "qabcd";
    char* q_2nd = "aqbcd";
    char* q_strs1[] = {q_1st, q_2nd, q_1st, q_2nd , q_2nd};

    remove_2nd_q_strings(q_strs1, 4);
    CuAssertPtrEquals(tc, q_1st, q_strs1[0]);
    CuAssertPtrEquals(tc, NULL, q_strs1[1]);
    CuAssertPtrEquals(tc, q_1st, q_strs1[2]);
    CuAssertPtrEquals(tc, NULL, q_strs1[3]);
    //check that we respect size
    CuAssertPtrEquals(tc, q_2nd, q_strs1[4]);

    char* no_q = "hello";
    char* q_2nd_2 = "qq";

    char* q_strs2[] = {q_2nd, q_2nd_2, no_q, q_1st , q_2nd_2};

    remove_2nd_q_strings(q_strs2, 5);
    
    CuAssertPtrEquals(tc, NULL, q_strs2[0]);
    CuAssertPtrEquals(tc, NULL, q_strs2[1]);
    CuAssertPtrEquals(tc, no_q, q_strs2[2]);
    CuAssertPtrEquals(tc, q_1st, q_strs2[3]);
    CuAssertPtrEquals(tc, NULL, q_strs2[4]);
    
}
/*

Takes an string and an output array of strings.  The string is a space
separated string of words.  The function places in the output array
pointers to the beginning of each word in the string, followed by NULL
when there are no more words.  For example:

"foo bar baz"
 ^   ^   ^ NULL

The output pointers correspond to the places noted with ^ above.

You can assume that the string neither starts nor ends with spaces.  If
the string is "" the function should output NULL only.  You can assume
the output array always has enough memory for all the pointers it
needs to output.

MIKE SAYS: I think we might want to simplify this one...got some sort
of tricky parsing edge cases

 */
void words(char* str, char** output) {
 #ifdef STUDENT
    return 2;
#else   
    if(*str == '\0') {
        output[0] = NULL;
        return;
    } else {
        output[0] = str;
    }

    int output_pos = 1;
    char* cur = str;

    while(*cur != '\0') {
        if(*cur == ' ') {
            cur++;
            output[output_pos] = cur;
            output_pos++;
        }
        cur++;
    }
    output[output_pos] = NULL;
#endif  
}

void test_words(CuTest *tc) {
    char* output[10];

    words("foo bar baz", output);
    CuAssertStrEquals(tc, "foo bar baz", output[0]);
    CuAssertStrEquals(tc, "bar baz", output[1]);
    CuAssertStrEquals(tc, "baz", output[2]);
    CuAssertPtrEquals(tc, NULL, output[3]);

    words("hello world", output);
    CuAssertStrEquals(tc, "hello world", output[0]);
    CuAssertStrEquals(tc, "world", output[1]);
    CuAssertPtrEquals(tc, NULL, output[2]);

    words("longword q xx w", output);
    CuAssertStrEquals(tc, "longword q xx w", output[0]);
    CuAssertStrEquals(tc, "q xx w", output[1]);
    CuAssertStrEquals(tc, "xx w", output[2]);
    CuAssertStrEquals(tc, "w", output[3]);
    CuAssertPtrEquals(tc, NULL, output[4]);

    words("", output);
    CuAssertPtrEquals(tc, NULL, output[0]);
    
}

/*

This function takes 1 32-bit int as an input parameter and 4 8-bit int
pointers as output parameters.  The function sets the output
parameters to contain each of the 4 bytes in the input.  o1 gets the
first (lowest order) 8-bits of the 32-bit int, o2 gets the 2nd 8-bits
etc.

 */
void breakint(int32_t in, int8_t* o1, int8_t* o2, int8_t* o3, int8_t* o4) {
#ifdef STUDENT
    return 2;
#else
    int8_t* in_ptr = (int8_t*) &in;
    *o1 = *in_ptr;
    in_ptr++;
    *o2 = *in_ptr;
    in_ptr++;
    *o3 = *in_ptr;
    in_ptr++;
    *o4 = *in_ptr;
#endif
}


void test_breakint(CuTest *tc) {
    int8_t output1, output2, foobar, output3, output4;
    foobar = 77;
    breakint(0, &output1, &output2, &output3, &output4);
    CuAssertIntEquals(tc, 0, output1);
    CuAssertIntEquals(tc, 0, output2);
    // prevents tricky things like assigning all 4 bytes together
    CuAssertIntEquals(tc, 77, foobar);
    CuAssertIntEquals(tc, 0, output3);
    CuAssertIntEquals(tc, 0, output4);


    foobar++;
    breakint(1, &output1, &output2, &output3, &output4);
    CuAssertIntEquals(tc, 1, output1);
    CuAssertIntEquals(tc, 0, output2);
    CuAssertIntEquals(tc, 78, foobar);
    CuAssertIntEquals(tc, 0, output3);
    CuAssertIntEquals(tc, 0, output4);

    foobar++;
    breakint(256, &output1, &output2, &output3, &output4);
    CuAssertIntEquals(tc, 0, output1);
    CuAssertIntEquals(tc, 1, output2);
    CuAssertIntEquals(tc, 79, foobar);
    CuAssertIntEquals(tc, 0, output3);
    CuAssertIntEquals(tc, 0, output4);

    foobar++;
    breakint(65536, &output1, &output2, &output3, &output4);
    CuAssertIntEquals(tc, 0, output1);
    CuAssertIntEquals(tc, 0, output2);
    CuAssertIntEquals(tc, 80, foobar);
    CuAssertIntEquals(tc, 1, output3);
    CuAssertIntEquals(tc, 0, output4);

    foobar++;
    breakint(16777217, &output1, &output2, &output3, &output4);
    CuAssertIntEquals(tc, 1, output1);
    CuAssertIntEquals(tc, 0, output2);
    CuAssertIntEquals(tc, 81, foobar);
    CuAssertIntEquals(tc, 0, output3);
    CuAssertIntEquals(tc, 1, output4);

    
}




/*

doublecorrectly takes a void pointer to a array of data and num
parameter indicating the size of the array.  The array contains either
32 bit integer values (int32_t) or doubles (size could theoretically
vary - you can use pointer math or sizeof(double) to figure out what
the size is on this system).  The first parameter is set to false if
the values are integer data, true if the values are double data.

doublecorrectly should loop though the items in the array and double
them in the appropaite way for the type they are.

 */

void doublecorrectly(bool is_double, void* data, int num) {
#ifdef STUDENT
    return 2;
#else
    if(!is_double) {
        int32_t* idata = data;
        for(int i = 0; i < num; i++) {
            *idata = 2*(*idata);
            idata++;
        }
    } else {
        double* ddata = data;
        for(int i = 0; i < num; i++) {
            *ddata = 2*(*ddata);
            ddata++;
        }
    }
#endif
}

void test_doublecorrectly(CuTest *tc) {

    int32_t intdata[] =  { 1, 4, -1};
    doublecorrectly(false, intdata, 3);
    CuAssertIntEquals(tc, 2, intdata[0]);
    CuAssertIntEquals(tc, 8, intdata[1]);
    CuAssertIntEquals(tc, -2, intdata[2]);

    double doubledata[] =  {1.0, 8.0, -1.0, 999};
    doublecorrectly(true, doubledata, 4);
    CuAssertDblEquals(tc, 2.0, doubledata[0],0.00001);
    CuAssertDblEquals(tc, 16.0, doubledata[1],0.00001);
    CuAssertDblEquals(tc, -2.0, doubledata[2], 0.00001);
    CuAssertDblEquals(tc, 1998, doubledata[3], 0.00001);    
    
}


/*

The next functions utilize a simple linked list struct.  I've provided
functions that create and destroy linked lists.

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

/*

Computes the sum of all values in the list.  An empty list is a NULL head 
and its sum is 0.

Look at the test cases for example of usage

 */
int sum_linked_list(struct ll_node* head) {
#ifdef STUDENT
    return 2;
#else
    if(head == NULL) return 0;
    
    return head->val + sum_linked_list(head->next);
#endif
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

/*

Compares 2 linked lists for equality.

Returns 0 if the lists have different values.

Returns 1 if the lists are equal but fully distinct in memory
(i.e. they have no nodes in common)

Returns 2 if they share some nodes but not all nodes in memory.  Note
that this can only happen if they have different prefixes that
eventually merge into 1 list.

Returns 3 if they are the same list in memory.  

Note that 2 empty lists are considered to be equal in memory BUT the
two distinct but equal lists can still end in null and still be
considered distinct.

 */

int linked_list_equals(struct ll_node* head1, struct ll_node* head2) {
#ifdef STUDENT
    return 2;
#else
    if(head1 == head2) return 3;
    while(head1 != NULL) {
        if(head2 == NULL) return 0;
        if(head1 == head2) return 2;
        if(head1->val != head2->val) return 0;
        head1 = head1->next;
        head2 = head2->next;
    }
    if(head2 != NULL) return 0;
    return 1;
#endif
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


/*

Makes a chain of linked lists in memory, with each entry pointing to
the next.  The memory to use is provided by the memory pointer - you
can assume there is enough space to allocate the whole list.  The
other parameter is the number of list items to create.

In this case treat the size of struct ll_nodes as 16-bytes (64 bits
for the pointer, 64 bits for the data which is maybe more than it
needs).  This will also ensure things are aligned, without which we
might get some problems if we tried to use the list (more on alignment
*perhaps* when we talk about memory).  In this case don't use C array
syntax with the pointers, because that will use C's defaults on struct
size which might not be be 128-bits on every computer).  You can also
assume that the input memory pointer is already aligned, if that means
anything to you.

The created list should have values 1 2 3...num_items.  The list
should end with NULL as a normal linked list.  The function returns
the head of the list, which should always be memory unless num_items
is zero in which case the function should return NULL.

 */
struct ll_node* make_chain_in_memory(void* memory, int num_items) {
#ifdef STUDENT
    return 2;
#else
    if(num_items == 0) return NULL;

    struct ll_node *cur = memory;
    
    for(int i = 0; i < num_items - 1; i++) {
        cur->val = i + 1;
        cur->next = (void*) cur + 16;
        cur = cur->next;
    }

    cur->val = num_items;
    cur->next = NULL;
    return memory;
#endif
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

    SUITE_ADD_TEST(suite, test_add_with_pointers);
    SUITE_ADD_TEST(suite, test_address_in_array);
    SUITE_ADD_TEST(suite, test_bytes_between_4s);
    SUITE_ADD_TEST(suite, test_point_to_smallest);
    SUITE_ADD_TEST(suite, test_point_to_smallest_ptr);
    SUITE_ADD_TEST(suite, test_power_replace);
    SUITE_ADD_TEST(suite, test_power_sum_dont_replace);
    SUITE_ADD_TEST(suite, test_set_string_with_q);
    SUITE_ADD_TEST(suite, test_remove_2nd_q_strings);
    SUITE_ADD_TEST(suite, test_words);
    SUITE_ADD_TEST(suite, test_breakint);
    SUITE_ADD_TEST(suite, test_doublecorrectly);
    SUITE_ADD_TEST(suite, test_sum_linked_list);
    SUITE_ADD_TEST(suite, test_linked_list_equality);
    SUITE_ADD_TEST(suite, test_make_chain_in_memory);

    
    CuSuiteRun(suite);
    CuSuiteSummary(suite, output);
    CuSuiteDetails(suite, output);
    printf("%s\n", output->buffer);
    CuStringDelete(output);
    CuSuiteDelete(suite);
}
