#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include "kv_cache.h"

#define MAX_STRING_SIZE 64

// external function
void printlist(KVPAIR* list) {
    while (list) {
        printf("key: 0x%lx size: %d val: 0x", list->key, list->size);
        for (int i = 0; i < list->size; i++) {
            printf("%x", (unsigned char)list->val[i]);
        }
        printf("\n");
        list = list->next;
    }
}

// print cache
void print_cache(KVCACHE *cache, unsigned int bins) {
    for (int i = 0; i < bins; i++) {
        printf("bin %d: num_keys %d\n",i, cache[i].num_keys);
        printlist(cache[i].list);
        printf("\n");
    }
}

// internal to test_harness (hidden from students)

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


// Read a key value pair (with the specified key) from storage
// Returns a pointer to the KV pair, NULL if failure
KVPAIR *read_key(long key) {
    KVPAIR *new_kv;
    new_kv = (KVPAIR*)malloc(sizeof(KVPAIR));
    if (new_kv == NULL) {
        printf("Error allocating KV pairs...exiting\n");
        return NULL;
    }
    new_kv->next = NULL;
    new_kv->prev = NULL;
    new_kv->modified = 0;
    new_kv->key = key;
    new_kv->size = (unsigned int)random() % MAX_STRING_SIZE;
    new_kv->val = genstring(new_kv->size);
    usleep(5);

    return new_kv;
}

// Write a key value pair back to storage
// Returns a 1 on success 0 on failure
int write_key(KVPAIR *kv_pair){
    if (!kv_pair->modified) {
        printf("Error writeback of unmodified key!\n");
    }
    usleep(20);
    return(1);
}

// used for final clean up
void dealloc(KVPAIR *list) {
    KVPAIR *tptr;
    while(list) {
        tptr = list;
        list = list->next;
        tptr->next = NULL;
        tptr->prev = NULL;
        free(tptr->val);
        free(tptr);
    }
}

void clear_cache(KVCACHE *kv_cache, unsigned int bins) {
    for (int i = 0; i < bins; i++) {
        dealloc(kv_cache[i].list);
        kv_cache[i].list = 0;
        kv_cache[i].num_keys = 0;
    }

}

void run_tests(unsigned int num_pairs, unsigned int bins, unsigned int test) {
    KVPAIR* kvlist = NULL, * new_kv = NULL, * tail = NULL;
    KVCACHE* kv_cache = NULL;
    char* buffer = NULL;
    size_t size = 0;

    printf("Testing %d kv pairs and %d bins for ", num_pairs, bins);
    if (test) {
        printf("test %d\n",test);
    } else {
        printf("all tests\n");
    }
    
    // initialize the cache hash table
    kv_cache = (KVCACHE *) malloc(bins*sizeof(KVCACHE));
    for (int i = 0; i < bins; i++) {
            kv_cache[i].list = NULL;
            kv_cache[i].num_keys = 0;
    }


    // simple lookup of a three kv pairs
    if (test == 0 || test == 1) {
        KVPAIR *t1;
        hits =  misses = writebacks = 0;
        clear_cache(kv_cache, bins);
        t1 = lookup(kv_cache, 0x0, bins);
        if (!t1) {printf("Error\n"); exit(1);}
        t1 = lookup(kv_cache, bins/2, bins);
        if (!t1) {printf("Error\n"); exit(1);}
        t1 = lookup(kv_cache, bins-1, bins);
        if (!t1) {printf("Error\n"); exit(1);}
//        print_cache(kv_cache,bins);
        printf("Test 1: Hits: %d, Misses: %d, Miss Ratio: %f, Writebacks: %d\n",
            hits, misses, (float) misses/(hits+misses), writebacks);
    }

    // simple lookup of a three kv pairs w/ reuse
    if (test == 0 || test == 2) {
        KVPAIR *t1;
        hits =  misses = writebacks = 0;
        clear_cache(kv_cache, bins);
        t1 = lookup(kv_cache, 0x0, bins);
        if (!t1) {printf("Error\n"); exit(1);}
        t1 = lookup(kv_cache, bins/2, bins);
        if (!t1) {printf("Error\n"); exit(1);}
        t1 = lookup(kv_cache, bins-1, bins);
        if (!t1) {printf("Error\n"); exit(1);}
        t1 = lookup(kv_cache, 0x0, bins);
        if (!t1) {printf("Error\n"); exit(1);}
        t1 = lookup(kv_cache, bins/2, bins);
        if (!t1) {printf("Error\n"); exit(1);}
        t1 = lookup(kv_cache, bins-1, bins);
        if (!t1) {printf("Error\n"); exit(1);}
//        print_cache(kv_cache,bins);
        printf("Test 2: Hits: %d, Misses: %d, Miss Ratio: %f, Writebacks: %d\n",
            hits, misses, (float) misses/(hits+misses), writebacks);
    }

    // push the lru feature by stressing one set
    if (test == 0 || test == 3) {
        KVPAIR *t1;
        hits =  misses = writebacks =  0;
        clear_cache(kv_cache, bins);
        for (int i = 0; i < 8; i++) {
            t1 = lookup(kv_cache,i*bins,bins);
            if (!t1) {printf("Error\n"); exit(1);}
        }
//        print_cache(kv_cache,bins);
        printf("Test 3: Hits: %d, Misses: %d, Miss Ratio: %f, Writebacks: %d\n",
            hits, misses, (float) misses/(hits+misses), writebacks);
    }

    // push the lru feature by stressing one set
    if (test == 0 || test == 4) {
        KVPAIR *t1;
        hits =  misses = writebacks = 0;
        clear_cache(kv_cache, bins);
        for (int i = 0; i < 8; i++) {
            t1 = lookup(kv_cache,i*bins,bins);
            if (!t1) {printf("Error\n"); exit(1);}
            if (rand() % 3 == 0) {
                t1->modified = 1;
            }
        }
        for (int i = 7; i >= 0; i--) {
            t1 = lookup(kv_cache,i*bins,bins);
            if (!t1) {printf("Error\n"); exit(1);}
        }
//        print_cache(kv_cache,bins);
        printf("Test 4: Hits: %d, Misses: %d, Miss Ratio: %f, Writebacks: %d\n",
            hits, misses, (float) misses/(hits+misses), writebacks);
    }

       // push the lru feature by stressing one set
    if (test == 0 || test == 5) {
        KVPAIR *t1;
        hits =  misses = writebacks = 0;
        clear_cache(kv_cache, bins);
        for (int i = 0; i < 8; i++) {
            t1 = lookup(kv_cache,i*bins,bins);
            if (!t1) {printf("Error\n"); exit(1);}
            if (rand() % 3 == 0) {
                t1->modified = 1;
            }
        }
        for (int j = 0; j < 6; j++) {
            for (int i = 0; i < 4; i++) {
                t1 = lookup(kv_cache,i*bins,bins);
                if (!t1) {printf("Error\n"); exit(1);}
                if (rand() % 3 == 0) {
                    t1->modified = 1;
            }
            }
        }
//        print_cache(kv_cache,bins);
        printf("Test 5: Hits: %d, Misses: %d, Miss Ratio: %f, Writebacks: %d\n",
            hits, misses, (float) misses/(hits+misses), writebacks);
    }


    long *keys = (long *) malloc(num_pairs*sizeof(long));

    // generate some keys for larger experiments
    for (int i = 0; i < num_pairs; i++) {
        keys[i] = random();
    }

    // just iterate of the key array and do lookups
    if (test == 0 || test == 6) {
        KVPAIR *t1;
        int idx;
        hits =  misses = writebacks = 0;
        clear_cache(kv_cache, bins);

        for (int i = 0; i < 10*num_pairs; i++) {
            t1 = lookup(kv_cache, keys[i%num_pairs], bins);
            if (!t1) {printf("Error\n"); exit(1);}
            if (rand() % 3 == 0) {
                t1->modified = 1;
            }
        }
//      print_cache(kv_cache,bins);
        printf("Test 6: Hits: %d, Misses: %d, Miss Ratio: %f, Writebacks: %d\n",
            hits, misses, (float) misses/(hits+misses), writebacks);
    }

    srand(1001);

    if (test == 0 || test == 7) {
        KVPAIR *t1;
        int idx;
        hits =  misses = writebacks = 0;
        clear_cache(kv_cache, bins);

        for (int k = 0; k < 10000; k++) {
            t1 = lookup(kv_cache, keys[rand() % num_pairs], bins);
            if (!t1) {printf("Error\n"); exit(1);}
            if (rand() % 3 == 0) {
                t1->modified = 1;
            }
        }
//        print_cache(kv_cache,bins);
        printf("Test 7: Hits: %d, Misses: %d, Miss Ratio: %f, Writebacks: %d\n",
            hits, misses, (float) misses/(hits+misses), writebacks);
    }

    // test some hot keys now interposed with some random accesses
    if (test == 0 || test == 8) {
        KVPAIR *t1;
        int idx;
        hits =  misses = writebacks = 0;
        clear_cache(kv_cache, bins);
        for (int k = 0; k < 10000; k++) {
            if (k % 10 == 0) {
                idx = rand() % num_pairs; // 1/10 random
            }
            else {
                idx = rand() % (num_pairs/10); // 9/10 hot keys
            }
            t1 = lookup(kv_cache, keys[idx], bins);
            if (!t1) {printf("Error\n"); exit(1);}
            if (rand() % 3 == 0) {
                t1->modified = 1;
            }
           
        }
//        print_cache(kv_cache,bins);
        printf("Test 8: Hits: %d, Misses: %d, Miss Ratio: %f, Writebacks: %d\n",
            hits, misses, (float) misses/(hits+misses), writebacks);
    }
    


    // clean up the list
    clear_cache(kv_cache, bins);
    free(kv_cache);
    free(keys);


}