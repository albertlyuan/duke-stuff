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
    heap_size += size;
    if (heap_size > max_heap_size){
        heap_size -= size;
        return NULL;
    }

    return malloc(size);

}

void *my_alloc(unsigned int size) {
    // printf("size: %d\n",size);
    int idx;
    if (size <= 8){
        size = 8;
        idx = 0;
    }else if (size <= 16){
        size = 16;
        idx = 1;
    }else if (size <= 32){
        size = 32;
        idx = 2;
    }else{
        size = 64;
        idx = 3;
    }
    if (heap[idx] == NULL){
        int slabSize = (size + sizeof(blk_header_t))*NUM_HEAP_BLOCKS;
        void* returnedMemory = allocate_slab(slabSize);
        if (returnedMemory == NULL){
            printf("Malloc failed, out of memory!\n");
            exit(0);
        }
        heap[idx] = returnedMemory;
        //build linked list
        void* currAddy = heap[idx];
        blk_header_t* currHeader = (blk_header_t*) currAddy;
        for (int i = 0; i < NUM_HEAP_BLOCKS-1; i++){
            currHeader->size = size;
            currAddy += sizeof(blk_header_t) + size;
            currHeader->next = currAddy;
            currHeader = (blk_header_t*) currAddy;

        }
        currHeader->size = size;
    }

    void* ret = heap[idx] + sizeof(blk_header_t); 
    blk_header_t* header = (blk_header_t*) heap[idx];
    header = header->next;
    heap[idx] = (void*) header;
    // print_heap();

    return ret;
}

void my_free(void *ptr) {
    blk_header_t* header = (blk_header_t*) (ptr-sizeof(blk_header_t));
    int size = header->size;
    int idx;
    if (size == 8){
        idx = 0;
    }else if (size == 16){
        idx = 1;
    }else if (size == 32){
        idx = 2;
    }else{
        idx = 3;
    }
    header->next = heap[idx];
    heap[idx] = (void *) header;
    // print_heap();
    return;
}