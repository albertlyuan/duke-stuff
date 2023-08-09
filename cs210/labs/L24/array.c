#include <stdio.h>
#include <stdlib.h>
#include "mythreads.h"

#define SIZE 4

int array[SIZE];

#ifdef STUDENT
// add any global declarations here

#else
static pthread_mutex_t locks[SIZE];
#endif

// inputs: integer indices in range 0 to SIZE-1 in either argument
// outputs: none
// Must call update_entry(int indx) with the lower of the two indices before
// incrementing the larger index array value by the updated lower index array 
// value.
void update_two(int idx1, int idx2) {
#ifdef STUDENT
  // fill in this routine to call update_entry for each of the entries with the corresponding
  // indices.  Your program should not use one single lock and should not deadlock.
#else
  // grab smallest lock first to break deadlock;
  int t1 = idx1, t2 = idx2;
#define NODL
#ifdef NODL 
  if (idx1 > idx2) {
    t1 = idx2;
    t2 = idx1;
  }
#endif
  
#define NOTFOO
#ifdef NOTFOO

  Pthread_mutex_lock(&locks[t1]);
  Pthread_mutex_lock(&locks[t2]);
  update_entry(t1);
  array[t2] += array[t1];
  
  Pthread_mutex_unlock(&locks[t2]);
  Pthread_mutex_unlock(&locks[t1]);
#else
  Pthread_mutex_lock(&locks[t1]);
  update_entry(t1);
  Pthread_mutex_unlock(&locks[t1]);
  Pthread_mutex_lock(&locks[t2]);
  array[t2] += array[t1];
  Pthread_mutex_unlock(&locks[t2]);
//  Pthread_mutex_unlock(&locks[t1]);
#endif // mytest
#endif 
}

// create 4 threads, passing to each one a unique integer id in the range 0-3
// each thread should be created with thread_worker as the function to execute
// wait for all 4 threads to complete before returning.
// this routine will be called many times from main.
void create_and_wait_threads() {
#ifdef STUDENT
  // fill in this portion to initialize locks and create 4 threads 
  // that call thread_worker with a unique integer id in the range 0-3
  // use join to wait for all four threads to finish before returning

#else
  pthread_t threads[4];
  int ids[4];
  for (int i = 0; i < SIZE; i++) {
    Pthread_mutex_init(&locks[i], NULL);
  }
  for (int i=0; i < 4; i++) {
    ids[i] = i;
    Pthread_create(&threads[i], NULL, thread_worker, (void *) &ids[i] );
  }

  for (int i=0; i < 4; i++) {
    Pthread_join(threads[i],NULL);
  }
#endif
}

int main (int argc, char **argv)
{
 
  // run some number of trials defined in mythreads.h
  for (int k = 0; k < TRIALS; k++) {
    // set the array values
   for (int i = 0; i < SIZE; i++) {
      array[i] = 0;
   }

    // call an internal init routine, used by prof
    my_threads_init();

    // create the threads and wait for completion
    create_and_wait_threads();

    my_threads_end();

    // main finish up code, compute a sum of the array values
    int sum = 0;
    for (int i =0; i < SIZE; i++) {
      if (array[i]) {
        printf("%d: %d\n",i,array[i]);
        sum += array[i];
      } 
    }
    printf("main: trial %d result %d\n",k,sum);
  }

  return(0);
}
