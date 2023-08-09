//
// Created by Chris Kjellqvist (cmk91@duke.edu) on 8/19/21.
//

#include "barrier.h"

// initialize your barrier
void barrier_init(barrier_t *barr, int num_threads) {
#ifdef STUDENT
#else
  barr->counter = 0;
  barr->max = num_threads;
  barr->mutexes = (pthread_mutex_t *) malloc(sizeof(pthread_mutex_t) * num_threads);
  pthread_mutex_init(&barr->counter_mutex, NULL);
  for (int i = 0; i < num_threads; ++i) {
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
  pthread_mutex_lock(&barr->counter_mutex);
  int my_lock = barr->counter++;
  if (barr->counter == barr->max) {
    barr->counter = 0;
    for (int i = 0; i < barr->max; ++i) {
      pthread_mutex_unlock(&barr->mutexes[i]);
    }
  }
  pthread_mutex_unlock(&barr->counter_mutex);
  pthread_mutex_lock(&barr->mutexes[my_lock]);
#endif
}
