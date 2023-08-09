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

void *thread_double(void *argin) {

#ifdef STUDENT

  // fill in this function to have the thread update its range of array entries
  // each entry in the array should be multiplied by 2.
  // also call print_thread_info
  return NULL;

#else
  args_t *args = (args_t *) argin;
  print_thread_info();
  print_args(args);
  
  for (int i = args->start; i < args->end; i++) {
    array[i] = array[i] * 2;
  }
  printf("%ld done\n",Thread_gettid());
  return NULL;
#endif
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

#ifdef STUDENT
  // fill in this portion to create 4 threads that operate on 1/4 of the array
  // you need to setup the arg structure appriately
  // main should use join to wait for all four threads to finish before executing the finish up code

#else
  int start = 0;
  for (int i=0; i < 4; i++) {
    args_t *argp = &args[i];
    argp->id = i;
    argp->start = start;
    argp->end = argp->start + SIZE/4;
    start += SIZE/4;
    Pthread_create(&threads[i], NULL, thread_double, (void *) argp);
  }

  for (int i=0; i < 4; i++) {
    Pthread_join(threads[i],NULL);
  }
#endif

  // main finish up code.
  sum = 0;
  for (int i =0; i < SIZE; i++) {
    sum += array[i];
  }
  printf("main: result %d\n",sum);

  return (0);
}
