#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "kv_store.h"

#define MAX_STRING_SIZE 256

// external function
void printlist(KVPAIR* list) {
    while (list) {
        printf("id: 0x%lx size: %d val: 0x", list->key, list->size);
        for (int i = 0; i < list->size; i++) {
            printf("%x", (unsigned char)list->val[i]);
        }
        printf("\n");
        list = list->next;
    }
}

// internal to test_harness (hidden from students)

// takes a pointer to a list of KV pairs and serializes them.
// inputs: pointer to use for buffer and pointer to list of KVPAIRS
// return: size of buffer as (size_t type)
char* serialize(size_t* tot_size, KVPAIR* kv) {
    // first determine the size required
    KVPAIR* p = kv;
    *tot_size = 0;
    while (p != NULL) {
        *tot_size += sizeof(p->key) + sizeof(p->size) + p->size;
        p = p->next;
    }

    // allocate the buffer
    char* buf = malloc(*tot_size);
    char* out_ptr = buf;

    // reset pointer to head of list
    p = kv;

    // write out the fields
    while (p != NULL) {
        memcpy(out_ptr, (char*)&(p->key), (size_t)sizeof(p->key));
        out_ptr += sizeof(p->key);
        memcpy(out_ptr, (char*)&(p->size), (size_t)sizeof(p->size));
        out_ptr += sizeof(p->size);
        memcpy(out_ptr, p->val, (size_t)p->size);
        out_ptr += p->size;
        p = p->next;
    }

    return buf;
}

// generate a length n string of random characters from the alphabet
char* genstring(unsigned int len) {
    char* str;

    str = malloc(len);
    if (str == NULL) {
        printf("Error allocating string!\n");
    }

    // we assume the value is some string of 8-bit values
    for (int i = 0; i < len; i++) {
        str[i] = (char)(random() & 0xFF);
    }

    return str;
}

// return 1 if the two lists are identical, 0 otherwise
int compare_lists(KVPAIR* list1, KVPAIR* list2) {

    while (list1 != NULL && list2 != NULL) {
        if (list1->key != list2->key) {
            printf("Error: IDs don't match\n");
            return 0;
        }

        if (list1->size != list2->size) {
            printf("Error: Sizes don't match\n");
            return 0;
        }

        if (memcmp(list1->val, list2->val, list1->size)) {
            printf("Error: Values don't match\n");
            return 0;
        }

        list1 = list1->next;
        list2 = list2->next;
    }
    if (list1 != NULL || list2 != NULL) {
        printf("Error: Lists not the same length\n");
        return 0;
    }
    return 1;
}

KVPAIR *copylist(KVPAIR *list) {
    KVPAIR *tnode = NULL, *tptr = NULL, *head = NULL;
    while (list) {
        tnode = (KVPAIR *) malloc(sizeof(KVPAIR));
        tnode->key = list->key;
        tnode->size = list->size;
        tnode->val = malloc(tnode->size);
        tnode->next = NULL;
        memcpy(tnode->val, list->val, (size_t) tnode->size);
        if (head == NULL) {
            head = tnode;
            tptr = head;
        } else {
            tptr->next = tnode;
            tptr = tptr->next;
        }
        list = list->next;
    }
    return head;
}

KVPAIR *mylookup(KVPAIR *list, long key) {
    while (list != NULL) {
        if (list->key == key)
            break;
        list = list->next;
    }
    return list;
}

void dealloc(KVPAIR *list) {
    KVPAIR *tptr;
    while(list) {
        tptr = list;
        list = list->next;
        tptr->next = NULL;
        free(tptr->val);
        free(tptr);
    }
}

int count_list_items(KVPAIR *list) {
    int count = 0;
    while (list != NULL) {
        count++;
        list = list->next;
    }
    return count;
}

void run_tests(int num_pairs, char *test_fname) {
    KVPAIR* kvlist = NULL, * new_kv = NULL, * tail = NULL;
    char* buffer = NULL;
    size_t size = 0;
    printf("Testing %d kv pairs for ", num_pairs);
    if (test_fname == NULL) {
        printf("all functions\n");
    } else {
        printf("%s\n", test_fname);
    }

    // make n KV pairs
    for (int i = 0; i < num_pairs; i++) {
        new_kv = (KVPAIR*)malloc(sizeof(KVPAIR));
        if (new_kv == NULL) {
            printf("Erorr allocating KV pairs...exiting\n");
            return;
        }
        new_kv->next = NULL;
        new_kv->key = random();
        new_kv->size = (unsigned int)random() % MAX_STRING_SIZE;
        new_kv->val = genstring(new_kv->size);

        // add new kv pair to tail of the list
        if (kvlist == NULL) {
            kvlist = new_kv;
            tail = kvlist;
        } else {
            tail->next = new_kv;
            tail = tail->next;
        }
    }

    buffer = serialize(&size, kvlist);


    if (!test_fname || strcmp("deserialize", test_fname) == 0) {
        printf("Testing deserialize...");
        KVPAIR* newlist;
        newlist = deserialize(buffer, size);

        if (compare_lists(kvlist, newlist)) {
            printf("Success!\n");
        } else {
            printf("Failure\n");
        }
        dealloc(newlist);
    }

    if (!test_fname || strcmp("lookup", test_fname) == 0) {
        KVPAIR *kvpair, *tpair;
        printf("Testing lookup...");
        fflush(stdout);

        // check the populated list
        tpair = kvlist;
        while(tpair != NULL) {
            kvpair = lookup(kvlist, tpair->key);
            if (!kvpair || kvpair->key != tpair->key || kvpair->size != tpair->size) {
                printf("Failure\n");
                return;
            }
            tpair = tpair->next;
        }

        kvpair = lookup(NULL,kvlist->key);
        if (kvpair != NULL) {
           printf("Failure\n");
            return;
        }
         
        // check some random values
        for (int i = 0; i < 10; i++) {
            long rkey = random();
            if (mylookup(kvlist,rkey))
                continue; // skip this key if it already exists in the list
            kvpair = lookup(kvlist,rkey);
            if (kvpair != NULL) {
                printf("Failure\n");
                return;
            }
        }
        printf("Success\n");
    }

    if (!test_fname || strcmp("delete", test_fname) == 0) {
        KVPAIR *tpair, *tlist;
 
        tlist = copylist(kvlist); // make a copy of the list
 
        if (!compare_lists(kvlist, tlist)) {
            printf("Error 1 in testing infrastructure, contact TAs/instructor\n");
            return;
        }

        printf("Testing delete...");
        fflush(stdout);

        // see if we can delete the entire populated list
        int count = count_list_items(tlist);
        while(tlist != NULL) {
            long key = tlist->key;           
            if (!delete(&tlist, tlist->key)) {
                printf("Failure 1\n");
                return;
            }
            if (mylookup(tlist, key)) {  // make sure it is deleted.
                printf("Failure 1\n");
                return;
            }
            if (count_list_items(tlist) != count-1) {  // make remainder of list is there.
                printf("Failure 1\n");
                return;
            }
            count--; // decrement count
        }

        tpair = NULL;
        if (delete(&tpair, kvlist->key)) {
           printf("Failure 2\n");
            return;
        }

        tlist = copylist(kvlist); // make a copy of the list
        if (!compare_lists(kvlist,tlist)) {
            printf("Error 2 in testing infrastructure, contact TAs/instructor\n");
            return;
        }

        // check some random values
        for (int i = 0; i < 10; i++) {
            long rkey = random();
            if (mylookup(tlist,rkey))
                continue; // skip this key if it already exists in the list
            if (delete(&tlist, rkey)) {
                printf("Failure 3\n");
                return;
            }
        }
        printf("Success\n");
        dealloc(tlist);
    }

    // clean up the list
    dealloc(kvlist);
    free(buffer);

}