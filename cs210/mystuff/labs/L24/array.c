#include <stdio.h>
#include <stdlib.h>
#include "mythreads.h"

#define SIZE 1250

static int array[SIZE] = {0};

// add any global declarations here
pthread_mutex_t lockArray[SIZE];

// return a random index
unsigned int get_idx() {
  return(random()%(SIZE));
}

void *array_update(void *args) {
  print_thread_info();
  // fill in your the solution here to update the array 10000 times.
  // do not remove or modify any other lines of code in this function
  int idx;
  for (int i = 0; i < 10000; i++){
    idx = get_idx();
    Pthread_mutex_lock(&lockArray[idx]);
    array[idx] = array[idx] + 1;
    Pthread_mutex_unlock(&lockArray[idx]);
  }
  printf("%ld done\n",Thread_gettid());
  return NULL;
}

int main (int argc, char **argv)
{
  pthread_t threads[4];
  
  srandom(2001); // seed the random number generator
  my_threads_init();

  // fill in this portion to initialize locks and create 4 threads 
  // main should use join to wait for all four threads to finish before executing the finish up code
  for (int i = 0; i < SIZE; i++){
    Pthread_mutex_init(&lockArray[i],NULL);
  }
  for (int j = 0; j < 4; j++){
    Pthread_create(&threads[j],NULL,array_update,NULL);
    Pthread_join(threads[j],NULL);
  }

  my_threads_end();
  // main finish up code.
  int sum = 0;
  for (int i =0; i < SIZE; i++) {
    if (array[i]) {
      printf("%d: %d\n",i,array[i]);
      sum += array[i];
    }
  }
  printf("main: result %d\n",sum);

  return(0);
}
