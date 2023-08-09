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
    int foo = 20; 
    char array[6] = "ABCDE";

    dummy_func(&foo);
    printf("foo is %d\n",foo);
    if (scanf("%s",array) == 1) // read a string into array
        // We don't want the expected output to contain the answer :)
        // printf("array is %s\n",array); 
 
    return foo;
}

int main (int argc, char** argv) {
    int x,y,z,numels,in2;
    char c, in1;
    char* mystring = "Star Wars: Return of the Jedi";
    struct my_struct array1[] = {{32, 20}, {8, 4}, {20, 6}, {15, 200}, {20, 12}};

    int list1[] = {7,5,3,5};
    struct ll_node* head = make_sample_list(list1, 4);
    struct ll_node* head_copy = make_sample_list(list1, 4);


    int test = 0; // 0 means do all tests

    if (argc == 2)
    {
        test = atoi(argv[1]);
    }

    if (test == 1 || test == 0)
    {
        c = 'e';
        printf("Enter the character that will cause count_chars to return the value %d\n", count_chars(mystring,c));
        if (scanf("%c", &in1) != 1)
        {
            printf("Error in input\n");
            return 1;
        }
        if (count_chars(mystring,c) == count_chars(mystring,in1))
        {
            printf("Success!\n");
        }
        else
        {
            printf("Sorry try again...exiting\n");
            return 1;
        }
    }
    if (test == 2 || test == 0)
    {
        int foo_val = 120;
        printf("When prompted, enter a string that causes my_bug to return the value %d\n", foo_val);
        if (my_bug() == 120)
        {
            printf("Success!\n");
        }
        else
        {
            printf("Sorry try again...exiting\n");
            return 1;
        }
    }
    if (test == 3 || test == 0)
    {
        x = 20, y = 5;
        printf("Extra Credit: Enter the integer that will cause set_if_in_array to return the value %d\n",set_if_in_array(array1,x,y));
        if (scanf("%d", &in2) != 1)
        {
            printf("Error in input\n");
            return 1;
        }
        if (set_if_in_array(array1,x,y) == set_if_in_array(array1,in2,y))
        {
            printf("Success!\n");
        }
        else
        {
            printf("Sorry try again...exiting\n");
            return 1;
        }
    }

    if (test == 4 || test == 0)
    {
        z = 5;
        int target_retval = clear_if_in_list(head, z);
        printf("Extra Credit: Enter the integer that will cause clear_if_in_list to return the value %d\n", target_retval);
        if (scanf("%d", &in2) != 1)
        {
            printf("Error in input\n");
            return 1;
        }
        if (target_retval == clear_if_in_list(head_copy,in2))
        {
            printf("Success!\n");
        }
        else
        {
            printf("Sorry try again...exiting\n");
            return 1;
        }
    }
    


    return 0;
}
