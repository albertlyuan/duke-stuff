#include <stdio.h>
#include <stdlib.h>
#include "mythreads.h"


#define SIZE 1000
#define NUM_THREADS 4


static int array[SIZE];

typedef struct {
  unsigned int id;
  unsigned int start;
  unsigned int end;
} args_t;

void print_args(args_t *args) {
  printf("args: id %d start %d end %d\n", args->id, args->start, args->end);
}

void *thread_sum(void *argin) {
  args_t* arg = (args_t*) argin;
  // print_args(arg);
  print_thread_info();
  for (int i = arg->start; i < arg->end; i++){
    array[i] = array[i]*2;
  }
  long currThreadId = Thread_gettid();
  printf("%ld done\n", currThreadId);
  // fill in this function to have the thread update its range of array entries
  // each entry in the array should be multiplied by 2.
  // also call print_thread_info
  return NULL;
}

int main (int argc, char **argv)
{
  pthread_t threads[4];
  args_t args[4];

  // initialize the global array
  for (int j = 0; j < SIZE; j++) {
    array[j] = j;
  }

  int sum = 0;
  for (int i =0; i < SIZE; i++) {
    sum += array[i];
  }
  printf("main: before %d\n",sum);

  // fill in this portion to create 4 threads that operate on 1/4 of the array
  // you need to setup the arg structure appriately
  // main should use join to wait for all four threads to finish before executing the finish up code
  for (int i = 0; i < NUM_THREADS; i++){
    args[i].id = i;
    args[i].start = (SIZE/NUM_THREADS)*i;
    args[i].end = (SIZE*(i+1))/NUM_THREADS;
    // printf("%d %d %d\n",args[i].id,args[i].start,args[i].end);
    Pthread_create(&threads[i], NULL,thread_sum,&args[i]);
    Pthread_join(threads[i],NULL);
  }

  // main finish up code.
  sum = 0;
  for (int i =0; i < SIZE; i++) {
    sum += array[i];
  }
  printf("main: result %d\n",sum);

  return (0);
}
