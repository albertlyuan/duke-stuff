#include <stdio.h>
#include <stdlib.h>
#include "mythreads.h"

#define SIZE 4

int array[SIZE];

// add any global declarations here
pthread_mutex_t lockArray[SIZE];
pthread_t threads[4];

// inputs: integer indices in range 0 to SIZE-1 in either argument
// outputs: none
// Must call update_entry(int indx) with the lower of the two indices before
// incrementing the larger index array value by the updated lower index array 
// value.
void update_two(int idx1, int idx2) {
  // fill in this routine to call update_entry for each of the entries with the corresponding
  // indices.  Your program should not use one single lock and should not deadlock.
  int low; 
  int high;
  if (idx1<idx2){
    low = idx1;
    high = idx2;
  }else{
    low = idx2;
    high = idx1;
  }
  int newVal1;
  Pthread_mutex_lock(&lockArray[low]);
  update_entry(low);
  newVal1 = array[low];
  Pthread_mutex_unlock(&lockArray[low]);

  Pthread_mutex_lock(&lockArray[high]);
  array[high] = array[high] + newVal1;
  Pthread_mutex_unlock(&lockArray[high]);
}

// create 4 threads, passing to each one a unique integer id in the range 0-3
// each thread should be created with thread_worker as the function to execute
// wait for all 4 threads to complete before returning.
// this routine will be called many times from main.
void create_and_wait_threads() {
  // fill in this portion to initialize locks and create 4 threads 
  // that call thread_worker with a unique integer id in the range 0-3
  // use join to wait for all four threads to finish before returning
  for (int i = 0; i<SIZE;i++){
    Pthread_mutex_init(&lockArray[i],NULL);
    array[i] = 0;
  }
  int ids[4];
  for (int j = 0; j<4; j++){
    ids[j] = j;
    Pthread_create(&threads[j], NULL, thread_worker, &ids[j]);
    Pthread_join(threads[j],NULL);
  }
  return;
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
