#include <stdio.h>
#include <stdlib.h>



// return the number of times character c appears in string str
// assume str is not null
int count_chars (char *str, char c) {
    int count = 0;
    int idx = 0;
    while (str[idx] != '\0') {
        if (str[idx] == c)
            count++;
        idx++;
    }
    return count;
}

// for use with structure functions
struct my_struct {
    int i1, i2;
};

// for any entry with i1 = a, set i2 = i1 + 2, return number set
int set_if_in_array (struct my_struct *arr, int a, int numels) { 
    int count = 0;
    for (int i = 0; i < numels; i++) { 
        if (arr[i].i1 == a) {
            arr[i].i2 = a + 2;
            count++;
        }
    }
    return count;
}

/*

The next functions utilize a simple linked list struct.  I've provided
functions that create and destroy linked lists.

 */

struct ll_node {
    int val;
    struct ll_node *next;
};

// helper function to create a list
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

// search the list and and set node value to 0 if found
// return the number of nodes cleared
int clear_if_in_list(struct ll_node* head, int i) {
    int count = 0;
    while (head != NULL) {
        if (head->val == i) {
            head->val = 0;
            count++;
        }
        head = head->next;
    }
    return count;
}

// example buffer overflow

// dummy_func used to make sure variable foo in my_bug is allocated on the stack
// else the compiler puts foo in a register
int dummy_func(int *x)
{
    *x = *x + 1;
    return 1;
}
int my_bug()
{
    int foo = 10; 
    char array[4] = "ABC";

    dummy_func(&foo);
    printf("foo is %d\n",foo);
    if (scanf("%s",array) == 1) // read a string into array
        printf("array is %s\n",array);
 
    return foo;
}

int main (int argc, char** argv) {
    int x,y,z,numels;
    char c;
    char* mystring = "Harry Potter and the Sorcerer's Stone";
    struct my_struct array1[] = {{19, 65}, {32, 200}, {4, 6}, {32,210}};

    int list1[] = {2,3,1};
    struct ll_node* head = make_sample_list(list1, 3);

    c = 't';
    printf("Count chars %c %d\n", c, count_chars(mystring,c));
    x = 32, y = 4;
    printf("Set if in array %d %d\n", x, set_if_in_array(array1,x,y));
    z = 3;
    printf("Clear if in list %d %d\n", z, clear_if_in_list(head,z));

    printf("What is foo? %d\n",my_bug());

    return 0;
}
