//
// Created by Chris Kjellqvist (cmk91@duke.edu) on 8/19/21.
//

#include "barrier.h"
#include <pthread.h>
#include <stdlib.h>

// initialize your barrier 
void barrier_init(barrier_t *barr, int num_threads) {
    barr->idx = 0;
    barr->end = num_threads;
    pthread_mutex_init(&(barr->idxLock),NULL);
}

// free anything that is dynamically allocated inside the barrier, do not
// free the barrier itself - it can be statically allocated or located on the
// stack, in which case freeing the barrier struct will result in a segfault.
void barrier_free(barrier_t *barr) {
    return;
}

// wait on the barrier. The barrier should be initialized to expect a certain
// number of threads. If there are (n-1) threads waiting and another thread
// calls barrier_wait(), that thread is responsible for releasing the threads
// to keep working and is also responsible for resetting the barrier.
void barrier_wait(barrier_t *barr) {
    int currIdx, end, idx;
    pthread_mutex_lock(&(barr->idxLock));
    currIdx = barr->idx;
    barr->idx += 1;
    end = barr->end;
    pthread_mutex_unlock(&(barr->idxLock));

    while(1){
        pthread_mutex_lock(&(barr->idxLock));
        idx = barr->idx;
        pthread_mutex_unlock(&(barr->idxLock));

        if (idx >= end){
            break;
        }
        // printf("curridx %d\n",currIdx);
    }
    printf("done %d\n",currIdx);
    pthread_mutex_lock(&(barr->idxLock));
    barr->end += 1;
    pthread_mutex_unlock(&(barr->idxLock));
    return;
}
