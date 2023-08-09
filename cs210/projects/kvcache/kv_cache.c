
#include <stdio.h>
#include <stdlib.h>
#include <stddef.h>
#include <string.h>
#include "kv_cache.h"


// Lookup the provided key in the KV cache.
// If the key is not found, fetch it from storage by calling read_key
// and place the KVPAIR in the cache.
// If placing a KVPAIR from memory into the cache causes the number of elements
// in the bin to exceed MAX_BIN_ELEMENTS, remove a KVPAIR using the LRU
// replacement policy and call write_key if the removed KVPAIR has
// been modified and changes need to be written back to memory.
// If the key is found in the cache, update LRU order for the
// key's bin appropriately.
// Input: the KV cache hash table and key to lookup
// Return: pointer to KVPAIR with the desired key

KVPAIR *lookup(KVCACHE *kv_cache, long key, unsigned int bins) {
#ifdef STUDENT
    return NULL;
#else
    KVPAIR *ptr, *tptr, *tmp;
    unsigned int index = 0; 
    index = key % bins;
    ptr = kv_cache[index].list;
    tptr = NULL;

    while (ptr) {
        if (ptr->key == key) {
            hits++;
            break;
        }
        tptr = ptr;
        ptr = ptr->next;
    }
    if (!ptr) {  // did not find the key, put at head of list
        misses++;
        tmp = read_key(key);
        tmp->prev = NULL;
        tmp->next = kv_cache[index].list;
        if (tmp->next) {
            tmp->next->prev = tmp;
        }
        kv_cache[index].list = tmp;
        kv_cache[index].num_keys++;

        // if above max replace least recently used -- its at end of list
        if (kv_cache[index].num_keys > MAX_BIN_ELEMENTS) {
            // prev now points to end of list
            if (tptr->next) {
                printf("Error tptr is not end of list.\n");
                exit(1);
            }
            if (tptr->modified)  {
                if (!write_key(tptr)) {
                    printf("Error writing key back.\n");
                    exit(1);
                }
                ++writebacks;
            }
            tptr->prev->next = NULL; // end of ptr, make next null
            free(tptr->val);
            free(tptr);
            kv_cache[index].num_keys--;
        }
        ptr = tmp;
    }
    else {  // found key, update LRU order -- move to front of list
 
        // add to front of chain, if not already there
        if (ptr != kv_cache[index].list) {
            // first unlink from the chain, tptr is one behind list (found pointer)
            if (ptr->prev)
                ptr->prev->next = ptr->next;
            if (ptr->next)
                ptr->next->prev = ptr->prev;

            ptr->next = kv_cache[index].list;
            if (ptr->next)
                ptr->next->prev = ptr;
            kv_cache[index].list = ptr;
            ptr->prev = NULL;
        }      
    }
    return ptr;
#endif
}
