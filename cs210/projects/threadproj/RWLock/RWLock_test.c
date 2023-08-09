//
// Created by Chris Kjellqvist (cmk91@duke.edu) on 8/19/21.
//
#include "RWLock.h"
#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <pthread.h>

static pthread_mutex_t testing_lock = PTHREAD_MUTEX_INITIALIZER;
static int testing_counter = 0;

void* multiple_reader_test_thread(void * arg) {
  rwlock_t *lock = (rwlock_t*)arg;
  rwlock_reader_lock(lock);
  pthread_mutex_lock(&testing_lock);
  testing_counter++;
  pthread_mutex_unlock(&testing_lock);
  return NULL;
}

int multiple_reader_test() {
  rwlock_t lock;
  rwlock_init(&lock);
  pthread_t threads[2];
  for (int i = 0; i < 2; ++i)
    pthread_create(&threads[i], NULL, multiple_reader_test_thread, &lock);

  for (int i = 0; i < 2; ++i)
    pthread_join(threads[i], NULL);

  return testing_counter == 2;
}

rwlock_t list_lock;
static const int list_sz = 1000;
int *list;
static int done_with_writing = 0;
#define NUM_WRITERS 3
#define NUM_READERS 8
#define NUM_CHANGES 15

void* list_test_reader(void * arg) {
  int* n_errors = (int*)arg;
  while (done_with_writing != NUM_WRITERS) {
    rwlock_reader_lock(&list_lock);
    // go backwards so that we're sure to see errors if we're in here with a writer
    for (int i = list_sz - 1; i > 0; --i) {
      if (list[i] - list[i-1] != 1) (*n_errors)++;
    }
    rwlock_reader_unlock(&list_lock);
  }
  return NULL;
}

void* list_test_writer(void * args) {
  for (int i = 0; i < NUM_CHANGES; ++i) {
    usleep((unsigned int)((double)rand() / RAND_MAX * 100000));
    rwlock_writer_lock(&list_lock);
    list[0] = rand();
    for (int j = 1; j < list_sz; ++j) {
      list[j] = list[j-1]+1;
    }
    rwlock_writer_unlock(&list_lock);
  }
  pthread_mutex_lock(&testing_lock);
  done_with_writing++;
  pthread_mutex_unlock(&testing_lock);
  return NULL;
}

int list_test() {
  list = (int*)malloc(sizeof(int)* list_sz);
  list[0] = rand();
  rwlock_init(&list_lock);
  for (int i = 1; i < list_sz; ++i) {
    list[i] = list[i-1] + 1;
  }

  int error_accums[NUM_READERS] = {0};
  pthread_t readers[NUM_READERS];
  for (int i = 0; i < NUM_READERS; ++i) {
    pthread_create(&readers[i], NULL, list_test_reader, &error_accums[i]);
  }

  pthread_t writers[NUM_WRITERS];
  for (int i = 0; i < NUM_WRITERS; ++i) {
    pthread_create(&writers[i], NULL, list_test_writer, NULL);
  }

  int sum = 0;
  for (int i = 0; i < NUM_READERS; ++i) {
    pthread_join(readers[i], NULL);
    sum += error_accums[i];
  }
  for (int i = 0; i < NUM_WRITERS; ++i) {
    pthread_join(writers[i], NULL);
  }
  return sum == 0;
}

int main() {
  srand(time(0));
  if (multiple_reader_test() > 0) {
    printf("pass multiple reader\n");
  } else {
    printf("fail multiple reader\n");
  }
   if (list_test() > 0) {
     printf("pass list test\n");
   } else {
     printf("fail list test\n");
   }

}