
#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <stdint.h>
#include <string.h>
#include <unistd.h>

#include "CuTest.h"

// The main buffer structure
typedef struct cbuf {
    uint32_t *buffer;
    uint32_t head, tail;
    uint max_length;
} cbuf_t;

uint length = 4;

// add data to the head of the circular buffer
// input: buffer struct and data
// return 0 on success and -1 on failure (i.e., buffer is full)
int cbuf_write(cbuf_t *cbuf, uint32_t data) {
#ifdef STUDENT
    return NULL;
#else
    // note: reference to https://embedjournal.com/implementing-circular-buffer-embedded-c/
    int next;

    next = cbuf->head + 1;  // next is where head will point to after this write.
    if (next >= cbuf->max_length)
        next = 0;

    if (next == cbuf->tail)  // if the head + 1 == tail, circular buffer is full
        return -1;

    cbuf->buffer[cbuf->head] = data;  // Load data and then move
    cbuf->head = next;             // head to next data offset.
    return 0;  // return success to indicate successful write.
#endif
}

// remove an entry from the tail of the buffer
// input: buffer struct and a pointer to data storage
// return 0 on success and -1 on failure (i.e., buffer is empty)
int cbuf_read(cbuf_t *cbuf, uint32_t *data) {
#ifdef STUDENT
    return NULL;
#else
    // note: reference to https://embedjournal.com/implementing-circular-buffer-embedded-c/
    int next;

    if (cbuf->head == cbuf->tail)  // if the head == tail, we don't have any data
        return -1;

    next = cbuf->tail + 1;  // next is where tail will point to after this read.
    if(next >= cbuf->max_length)
        next = 0;

    *data = cbuf->buffer[cbuf->tail];  // Read data and then move
    cbuf->tail = next;              // tail to next offset.
    return 0;  // return success to indicate successful write.
#endif
}

// utility functions
// print the buffer
void print_cbuf(cbuf_t *cbuf) {
 
    printf("head %d tail %d\n",cbuf->head, cbuf->tail);
    int idx = cbuf->head;
    while (idx != cbuf->tail ) {
        printf("idx %d data %d\n", idx, cbuf->buffer[idx]);
        idx = (idx + 1) % cbuf->max_length;
    }
}


// single write and read
void test_1 (CuTest *tc) {
    
    uint32_t item = 210;
    uint32_t ret_item = 0;
    // initialize the circular buffer
    cbuf_t *cbuf;
    cbuf = (cbuf_t *) malloc(sizeof(cbuf_t));
    cbuf->buffer = (uint32_t *) malloc (sizeof(uint32_t) * length);
    cbuf->max_length = length;
    cbuf->head = 0;
    cbuf->tail = 0;

    cbuf_write(cbuf,item);
    CuAssertIntEquals(tc, 1, cbuf->head);
    CuAssertIntEquals(tc, 0, cbuf->tail);
    
    cbuf_read(cbuf, &ret_item);
    CuAssertIntEquals(tc, item, ret_item);
}

// read when empty
void test_2 (CuTest *tc) {
    
    uint32_t item = 210;
    uint32_t ret_item = 0;
    int res;
    // initialize the circular buffer
    cbuf_t *cbuf;
    cbuf = (cbuf_t *) malloc(sizeof(cbuf_t));
    cbuf->buffer = (uint32_t *) malloc (sizeof(uint32_t) * length);
    cbuf->max_length = length;
    cbuf->head = 0;
    cbuf->tail = 0;    

    res = cbuf_write(cbuf,item);
    CuAssertIntEquals(tc, 1, cbuf->head);
    CuAssertIntEquals(tc, 0, cbuf->tail);
    CuAssertIntEquals(tc, 0, res);
    
    res = cbuf_read(cbuf,&ret_item);
    CuAssertIntEquals(tc, item, ret_item);
    CuAssertIntEquals(tc, 0, res);
    
    res = cbuf_read(cbuf,&ret_item);
    CuAssertIntEquals(tc, -1, res);
}

// write to full
void test_3 (CuTest *tc) {
    
    uint32_t item = -1;
    uint32_t ret_item = 0;
    int res;
    // initialize the circular buffer
    cbuf_t *cbuf;
    cbuf = (cbuf_t *) malloc(sizeof(cbuf_t));
    cbuf->buffer = (uint32_t *) malloc (sizeof(uint32_t) * length);
    cbuf->max_length = length;
    cbuf->head = 0;
    cbuf->tail = 0;

    for (int i = 0; i < cbuf->max_length-1; i++) {
        res = cbuf_write(cbuf,i);
        CuAssertIntEquals(tc, 0, res);
    }
    CuAssertIntEquals(tc, 0, cbuf->tail);
    CuAssertIntEquals(tc, cbuf->max_length-1, cbuf->head);
    // write one too many
    res = cbuf_write(cbuf, item);
    CuAssertIntEquals(tc, -1, res);

}

// write to full, ensure order is correct
void test_4 (CuTest *tc) {
    
    uint32_t ret_item = 0;
    int res;
    // initialize the circular buffer
    cbuf_t *cbuf;
    cbuf = (cbuf_t *) malloc(sizeof(cbuf_t));
    cbuf->buffer = (uint32_t *) malloc (sizeof(uint32_t) * length);
    cbuf->max_length = length;
    cbuf->head = 0;
    cbuf->tail = 0;

    for (int i = 0; i < cbuf->max_length-1; i++) {
        res = cbuf_write(cbuf,i);
        CuAssertIntEquals(tc, 0, res);
    }
    // read and should be in order
    for (int i = 0; i < cbuf->max_length-1; i++) {
        res = cbuf_read(cbuf,&ret_item);
        CuAssertIntEquals(tc, i, ret_item);
    }
}


int main(int argc, char **argv){
    CuString *output = CuStringNew();
    CuSuite* suite = CuSuiteNew();
    char c;
    int test_num = 0;

    while ((c = getopt(argc, argv, "t:l:")) != -1) {
        if (c == 't')
            test_num = atoi(optarg);
        if (c == 'l')
            length = atoi(optarg);       
    }


    switch (test_num) {
        case 1:
            SUITE_ADD_TEST(suite, test_1);
            break;
        case 2:
           SUITE_ADD_TEST(suite, test_2);
            break;
        case 3:
           SUITE_ADD_TEST(suite, test_3);
            break;
        case 4:
           SUITE_ADD_TEST(suite, test_4);
            break;
        default:
            SUITE_ADD_TEST(suite, test_1);
            SUITE_ADD_TEST(suite, test_2);
            SUITE_ADD_TEST(suite, test_3);
            SUITE_ADD_TEST(suite, test_4);
    }

    CuSuiteRun(suite);
    CuSuiteSummary(suite, output);
    CuSuiteDetails(suite, output);
    printf("%s\n", output->buffer);
    CuStringDelete(output);
    CuSuiteDelete(suite);
    return 0;
}