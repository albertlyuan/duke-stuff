
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include "alloc.h"

void print_bin(int bin) {
    blk_header_t *p = (blk_header_t *) heap[bin];
    while (p != NULL) {
        printf("%p size %d next %p\n",p, p->size, p->next);
        p = p->next;
    }
}

void print_heap() {
    for (int i = 0; i < 4; i++)
        print_bin(i);
}

int count_bin(int bin) {
    int count = 0;
    blk_header_t *p = (blk_header_t *) heap[bin];
    while (p != NULL) {
        count++;
        p = p->next;
    }
    return count;
}
void count_heap_blks() {
    for (int i = 0; i < 4; i++) {
        printf("%d:%d, ", i, count_bin(i));
    }
    printf("\n");
}