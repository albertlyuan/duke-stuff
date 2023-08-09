
#include <stdio.h>
#include <stdlib.h>
#include <stddef.h>
#include <string.h>
#include "kv_cache.h"


// Lookup the provided key in the KV cache 
// if not found fetch from storage by calling read_key
// if number of elements in bin is > max, replace lru KV pair, call write_key.
// if found, update lru order appropriately
// Input: the KV cache hash table and key to lookup
// Return: pointer to KVPAIR

KVPAIR *lookup(KVCACHE *kv_cache, long key, unsigned int bins) {
    unsigned int index = key%bins;
    // printf("key %ld, bins %d, index %d\n",key,bins,index);
    KVCACHE* currBin = kv_cache+index;
    unsigned int numKeys = currBin->num_keys; 
    // printf("numKeys %d\n",numKeys);

    KVPAIR* currKVP = currBin->list;

    int idx = 0;
    KVPAIR* prev;
    while (idx < numKeys){
        if (currKVP->key == key){
            if (idx >= 1){
                prev->next = currKVP->next;
                currKVP->next = currBin->list;
                currBin->list = currKVP;
            }
            hits += 1;
            return currKVP;
        }
        prev = currKVP;
        currKVP = currKVP->next;
        idx += 1;
    }
    misses += 1;
    KVPAIR* newKVP = read_key(key);
    if (currBin->num_keys >= MAX_BIN_ELEMENTS){
        //free lru
        currKVP = currBin->list;
        while(currKVP->next->next != NULL){
            currKVP = currKVP->next;
        }
        KVPAIR* lru = currKVP->next;
        if (lru->modified != 0){
            write_key(lru);
            writebacks += 1;
        }
        currKVP->next = NULL;
        free(lru->val);
        // free(lru->next);
        // free(lru->prev);
        free(lru);

    }else{
        currBin->num_keys++;
    }
    newKVP->next = currBin->list;
    currBin->list = newKVP;

    // print_cache(kv_cache, bins);
    // printlist(currBin->list);
    return newKVP;
}
