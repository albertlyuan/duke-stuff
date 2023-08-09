// A segregated storage memory allocator.  Complete the code required in the functions below
// to implement a segregated memory allocator.
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include "alloc.h"

int max_heap_size = 1024;  // default is a 1K heap (pretty small)

void *heap[4] = {NULL,NULL,NULL,NULL}; // the heap bins for each segregated size 8,16,32,64

unsigned int heap_size = 0; // A variable that keeps track of the how much memory we allocated

// allocate_slab uses the default malloc library to allocate memory
// input: size in bytes of slab to allocate
// return: pointer to slab allocated, NULL if allocation would exceed max_heap_size
void *allocate_slab(unsigned int size) {
#ifdef STUDENT
    return NULL;
#else
    void *slab = NULL;   
    if ((heap_size + size) <= max_heap_size) {
        slab = malloc(size);
        heap_size += size;
    }
    return slab;
#endif   
}

// Returns a pointer to a heap block of the appropriate
// size.
// If no such block exists in the free list, this function calls
// allocate_slab to get space NUM_HEAP_BLOCKS blocks of the
// appropriate size and adds them to the free list, then returns
// one of them.
void *my_alloc(unsigned int size) {
#ifdef STUDENT
    return NULL;
#else
    void *ret_p;
    int bin;
    unsigned int slab_size, blk_size, full_blk_size;
    void *free_list = NULL;

    if (size > 64) {
        printf("Error %d too large of an allocation request\n",size);
        exit(0);
    }
    if (size <= 8) 
        bin = 0;
    else if (size > 8 && size <= 16)
        bin = 1;
    else if (size > 16 && size <= 32)
        bin = 2;
    else if (size > 32 && size <= 64)
        bin = 3;

    free_list = heap[bin];
    blk_size = (8 << bin);
    full_blk_size = blk_size + sizeof(blk_header_t);
    slab_size = full_blk_size * NUM_HEAP_BLOCKS;

    if(!free_list) {  // empty free list, need to get more from slab allocator
        free_list = allocate_slab(slab_size);
        if (!free_list) {
            printf("Malloc failed, out of memory!\n");
            exit(0);
        }
        // initialize the free list and header size information
        blk_header_t *ptr = (blk_header_t *) free_list;
        for (int i = 0; i < NUM_HEAP_BLOCKS-1; i++) {
            ptr->size = blk_size;
            ptr->next = (blk_header_t *) ((char *) ptr + full_blk_size);
            ptr = ptr->next;
        }  
        ptr->size = blk_size;         
        ptr->next = NULL; // set the last one to NULL
        
    }
    
    ret_p = (void *) ((void *) free_list + sizeof(blk_header_t));
    blk_header_t *p = (blk_header_t *) free_list;
    free_list = (void *) p->next;
    p->next = NULL;
    heap[bin] = free_list;
    return(ret_p);
#endif
}

void my_free(void *ptr) {
#ifdef STUDENT
    return;
#else
    int bin;
    blk_header_t *h_ptr;

    if (!ptr) {
        return; // NULL input
    }
    
    h_ptr = (blk_header_t *) ((void *) ptr - sizeof(blk_header_t));
    if (h_ptr->size == 8)
        bin = 0;
    else if (h_ptr->size == 16)
        bin = 1;
    else if (h_ptr->size == 32)
        bin = 2;
    else if (h_ptr->size == 64)
        bin = 3;

    h_ptr->next = (blk_header_t *) heap[bin];
    heap[bin] = (void *) h_ptr;
    return;
#endif
}