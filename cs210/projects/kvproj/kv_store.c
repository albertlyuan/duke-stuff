
#include <stdio.h>
#include <stdlib.h>
#include <stddef.h>
#include <string.h>
#include "kv_store.h"

/**
 * Purpose: deserialize a key-value pair buffer (the format is in the README
 * file).
 * 
 * Inputs:
 * - `char *buf`: pointer to the key-value pair buffer.
 * - `size_t buffer_size`: total size of the key-value pair buffer (in bytes).
 * 
 * Return: `KVPAIR *start_of_list`, which is a pointer to start of linked list
 * of `KVPAIR`s if successful. Otherwise, return NULL.
 * 
 * Assumptions: `buf` is not empty or NULL and the `buffer_size` is the correct
 * size of the buffer.
 *
 * Hints: 
 * - Although you could probably make this function recursive, our
 *   implementation just uses a `while` loop.
 * - You will probably have a pointer which will go through the buffer. You
 *   first should probably think about how long you should be iterating for.
 * - Once you have figured out the correct bounds for iteration, for each new
 *   key-value pair you see, you will probably want to allocate a new node in
 *   your list, and then populate all the fields in that node. This will
 *   probably involve calling `memcpy()` a couple of times, and also `malloc()`
 *   for the value.
 * - Once you're done populating an individual node of the linked list, you are
 *   to actually add it to the list. This can be done by appending the node to
 *   the tail of the list. Think about how to do this...
 */

KVPAIR *deserialize(char *buf, size_t buffer_size) {
#ifdef STUDENT
    return NULL;
#else
    KVPAIR *kvlist = NULL, *new_kv = NULL, *tail = NULL;
    char* cur = buf;

    while (cur < (buf+buffer_size)) {
        new_kv = (KVPAIR *) malloc(sizeof(KVPAIR));
        if (new_kv == NULL) {
            return NULL;
        }
        new_kv->next = NULL;
        memcpy((char *) &new_kv->key, cur, sizeof(new_kv->key));
        cur += sizeof(new_kv->key);
        memcpy((char *) &new_kv->size, cur, sizeof(new_kv->size));
        cur += sizeof(new_kv->size);
        new_kv->val = malloc(new_kv->size);
        memcpy(new_kv->val,cur, (size_t) new_kv->size);
        cur += new_kv->size;

        // Add new kv pair to tail of the list.
        if(kvlist == NULL) {
            kvlist = new_kv;
            tail = kvlist;
        } else {
            tail->next = new_kv;
            tail = tail->next;
        }
    }
    return kvlist;
#endif
}


/**
 * Purpose: Look up the provided key in a `KVPAIR` list.
 * 
 * Inputs: 
 * - `KVPAIR *list`: pointer to the start of the linked list of `KVPAIR`s.
 * - `long key`: the key to look up in the linked list.
 * 
 * Return: `KVPAIR *found_entry`, which is pointer to the entry that contains
 * the key or NULL, if the key is not found.
 */

KVPAIR *lookup(KVPAIR *list, long key) {
#ifdef STUDENT
    return NULL;
#else
    while (list != NULL) {
        if (list->key == key)
            break;
        list = list->next;
    }
    return list;
#endif
}


/**
 * Purpose: Delete the `KVPAIR` with the specified key from the linked list.
 * 
 * Inputs: 
 * - `KVPAIR **list`: pointer to the pointer (handle) of first key-value pair.
 * - `long key`: the key to the `KVPAIR` entry that should be deleted.
 * 
 * Return: 1 if the key was found in the list and deleted successfully. 0 if the
 * key was not found in the list and/or some error occured during the deletion.
 * 
 * Hints:
 * - You will need to change `KVPAIR`'s next pointers in the list and free
 *   memory.
 * - Think about what needs to be updated if the first entry in the list is
 *   deleted...
 */

int delete(KVPAIR **list, long key) {
#ifdef STUDENT
    return 0;
#else
    KVPAIR *prev, *ptr;
    int found = 0;
    ptr = *list;
    prev = NULL;

    while (ptr) {
        if (ptr->key == key) {
            found = 1;
            // found the key
            if (!prev) {  // its the first node in the list!
                *list = ptr->next; // move the list pointer to next
                ptr->next = NULL;
            } else {
                prev->next = ptr->next; // unlink from chain
                ptr->next = NULL;
            }
            free(ptr->val);
            free(ptr);
            break; // exit the while loop
        }
        prev = ptr;
        ptr = ptr->next;
    }
    return found;
#endif
}
