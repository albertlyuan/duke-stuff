
#include <stdio.h>
#include <stdlib.h>
#include <stddef.h>
#include <string.h>
#include "kv_store.h"

/**
 * Purpose: deserialize a key-value pair buffer (format in README)
 * 
 * Inputs:
 * - char* buf: pointer to the key-value pair buffer
 * - size_t buffer_size: total size of the key-value pair buffer (in bytes)
 * 
 * Return:
 * - KVPAIR* start_of_list: pointer to start of linked list of KVPAIRs if successful
 * - (OR)
 * - NULL if an error occurs
 * 
 * Assumptions: buf is not empty or NULL and the buffer_size is the correct size of buf
 */

KVPAIR *deserialize(char *buf, size_t buffer_size) {
    long key;
    long *kp = &key;
    unsigned int size;
    unsigned int *sp = &size;
    char *value;

    memcpy(kp, buf, 8);
    memcpy(sp, buf+8, 4);
    value = (char *)malloc(size);
    memcpy(value, buf+12,size);

    KVPAIR *head, *ptr, *curr;
    head = (KVPAIR*)malloc(sizeof(KVPAIR));
    head->key = key;
    head->size = size;
    head->val = value;
    head->next = NULL;
    curr = head;
    buffer_size -= (12+size);
    buf += (12+size);
    while(buffer_size>0){
        ptr = (KVPAIR*)malloc(sizeof(KVPAIR));
        memcpy(kp, buf, 8);
        memcpy(sp, buf+8, 4);
        value = (char *)malloc(size);
        memcpy(value, buf+12,size);
        ptr->key = key;
        ptr->size = size;
        ptr->val = value;
        ptr->next = NULL;

        curr->next = ptr;
        curr = ptr;

        buffer_size -= (12+size);
        buf += (12+size);

    }
    return head;
}

/**
 * Purpose: Lookup the provide key in a KV list
 * 
 * Inputs: 
 * - KVPAIR* list: pointer to the start of the linked list of KVPAIRs
 * - long key: the key to lookup in the linked list
 * 
 * Return:
 * - KVPAIR* found_entry: pointer to the entry that contains the key
 * - (OR)
 * - NULL if the key is not found
 */

KVPAIR *lookup(KVPAIR *list, long key) {
    while (list != NULL){
        if (list->key == key){
            return list;
        }
        list = list->next;
    }
    return NULL;
}


/**
 * Purpose: Delete the KVPAIR with the specified key from the KVPAIR list
 * 
 * Inputs: 
 * KVPAIR** list: pointer to the pointer of first KVPAIR
 * long key: the key to the KVPAIR entry that should be deleted
 * 
 * Return:
 * 1: if key found in list and KVPAIR entry successfully deleted
 * 0: if key not found in list and/or some error occurs in deletion
 * 
 * Hints:
 * (hint: You will need to change KVPAIR's next pointers in the list and free memory)
 * (hint: What needs to be updated if the first entry in the list is deleted? KVPAIR** list)
 */

int delete(KVPAIR **list, long key) {
    KVPAIR *prev = NULL;
    KVPAIR *curr = *list;
    if (curr == NULL){
        free(curr);
        return 0;
    }
    KVPAIR *next = curr->next;

    if (curr->key == key){
        if(next != NULL){
            *list = next;
        }else{
            *list = NULL;
        }
        free(curr->val);
        free(curr);
        return 1;
    }
    prev = curr;
    curr = next;
    if (next != NULL){
        next = next->next;
    }
    while (curr != NULL){
        if (curr->key == key){
            prev->next = next;
            free(curr->val);
            free(curr);
            return 1;
        }
        prev = curr;
        curr = curr->next;
        if (curr != NULL){
            next = curr->next;
        }

    }
    return 0;
}