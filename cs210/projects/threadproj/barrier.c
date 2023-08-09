//
// Created by Chris Kjellqvist (cmk91@duke.edu) on 8/19/21.
//

#include "barrier.h"

// initialize your barrier by setting the initial values for all the fields of
// the given barrier_t struct.
// remember that you get to define the barrier_t struct's fields in barrier.h!
void barrier_init(barrier_t *barr, int num_threads) {
#ifdef STUDENT
#else
  // how many threads have been here
  barr->counter = 0;
  // we have 2 barriers inside each other
  barr->round = 0;
  // how many threads should be waiting when a thread that enters the barrier needs to release them
  barr->max = num_threads - 1;
  // we need 2n - 2 mutexes since we're actually two barriers
  int mtxs_needed = 2 * (num_threads - 1);
  // initialize everything
  barr->mutexes = (pthread_mutex_t *) malloc(sizeof(pthread_mutex_t) * mtxs_needed);
  pthread_mutex_init(&barr->counter_mutex, NULL);
  for (int i = 0; i < mtxs_needed; ++i) {
    pthread_mutex_init(&barr->mutexes[i], NULL);
    pthread_mutex_lock(&barr->mutexes[i]);
  }
#endif
}

// free anything that is dynamically allocated inside the barrier, do not
// free the barrier itself - it can be statically allocated or located on the
// stack, in which case freeing the barrier struct will result in a segfault.
void barrier_free(barrier_t *barr) {
#ifdef STUDENT
#else
  free(barr->mutexes);
  barr->mutexes = NULL;
#endif
}

// wait on the barrier. The barrier should be initialized to expect a certain
// number of threads. If there are (n-1) threads waiting and another thread
// calls barrier_wait(), that thread is responsible for releasing the threads
// to keep working and is also responsible for resetting the barrier.
void barrier_wait(barrier_t *barr) {
#ifdef STUDENT
#else
  // possible solution
  // shared mutex to increment and read thread counter
  pthread_mutex_lock(&barr->counter_mutex);
  // if n-1 threads have been here before, then this thread is in charge of releasing everyone
  if (barr->counter == barr->max) {
    // since we're the last thread, we're need to make it so that threads entering the barrier in the future do not
    // reach the barrier while I'm still releasing threads. This could mess everything up potentially...
    int old_round = barr->round;
    // reset the counter
    barr->counter = 0;
    // increment the round
    barr->round = (barr->round + 1) % 2;
    // release the counter mutex since we're done with it
    pthread_mutex_unlock(&barr->counter_mutex);

    // release each thread
    for (int i = 0; i < barr->max; ++i) {
      pthread_mutex_unlock(&barr->mutexes[old_round * barr->max + i]);
    }
  } else {
    // compute which lock we're assigned to
    int my_lock = barr->max * barr->round + (barr->counter++);
    // release counter lock since we're done reading/modifying it
    pthread_mutex_unlock(&barr->counter_mutex);
    // wait on the lock I am assigned to
    pthread_mutex_lock(&barr->mutexes[my_lock]);
  }
#endif
}
