#include <stdio.h>
#include <stdlib.h>


struct my_struct {
    int i1, i2;
};

// struct by value
int struct_by_val(struct my_struct a){
    int val =0;
    val = a.i1 + a.i2;
    return val;
}

// struct by ref
int struct_by_ref(struct my_struct *a){
    int val =0;
    val = a->i1 + a->i2;
    return val;
}


// array index, assume idx is valid;
void set_struct_vals (struct my_struct *arr, int idx, int a, int b) {
    arr[idx].i1 = a;
    arr[idx].i2 = b;
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

// access a linked list to retrun a value.  Assumes not NULL next!!
int access_list(struct ll_node* head) {
    int val = 0;
    val = head->next->next->val;
    return val;
}

// helper function to create a list

int main (int argc, char** argv) {
    int idx, a, b;
    struct my_struct struct1 = {7, 3};
    struct my_struct struct2 = {10, 32};
    struct my_struct array1[3] = {{6, 4}, {12, 20}, {5, 9}};

    int list1[] = {10,40,9,15,32};
    struct ll_node* head = make_sample_list(list1, 4);

    int in1, in2, in3, in4, in5;

    int test = 0; // 0 means do all tests

    if (argc == 2)
    {
        test = atoi(argv[1]);
    }

    if (test == 1 || test == 0)
    {
        printf("Enter the two integer values i1 and i2 of the structure passed to struct_by_ref, it returns %d\n",struct_by_ref(&struct2));
        if (scanf("%d %d", &in1, &in2) != 2)
        {
            printf("Error in input\n");
            return 1;
        }
        if (in1 == struct2.i1 && in2 == struct2.i2)
        {
            printf("Success!\n");
        }
        else
        {
            printf("Sorry try again...exiting\n");
            return 1;
        }
    }

    idx = 2;
    a = 2;
    b = 6;
    if (test == 2 || test == 0)
    {
        printf("Enter the arguments idx, a and b to set_struct_vals, then the initial values of i1 and i2 in the structure array1[idx]\n calling set_struct_vals now...\n");
        set_struct_vals(array1, idx, a, b);
        if (scanf("%d %d %d %d %d", &in1, &in2, &in3, &in4, &in5) != 5)
        {
            printf("Error in input\n");
            return 1;
        }
        if (in1 == idx && in2 == a && in3 == b && in4 == 5 && in5 == 9)
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
        printf("Enter three integers: 1) the return value of access_list, 2) which node in the list (numbered starting from 1 2 3 ..) contains the value, and 3) the total number of nodes in the list.\n");
        int a = access_list(head);
        if (scanf("%d %d %d", &in1, &in2, &in3) != 3)
        {
            printf("Error in input\n");
            return 1;
        }
        if (in1 == a && in2 == 3 && in3 == 4)
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
        printf("OPTIONAL PROBLEM: Enter the two integer values i1 and i2 of the structure passed to struct_by_val, it returns %d\n",struct_by_val(struct1));
        if (scanf("%d %d", &in1, &in2) != 2)
        {
            printf("Error in input\n");
            return 1;
        }
        if (in1 == struct1.i1 && in2 == struct1.i2)
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
