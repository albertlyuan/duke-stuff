//
// Created by Chris Kjellqvist (cmk91@duke.edu) on 8/19/21.
//

#ifndef THREADPROJ_RWLOCK_H
#define THREADPROJ_RWLOCK_H
#ifndef STUDENT
#include <pthread.h>
#include "spin_condition_lock.h"
#endif

typedef struct {
  // Add whatever fields you need here
#ifndef STUDENT
  pthread_mutex_t scope_mutex;
  pthread_mutex_t counter_mutex;
  spin_condition_var writer_reader_wait;
  int readers;
#endif
} rwlock_t;

// Implement these in RWLock.c
void rwlock_init(rwlock_t *lock);
void rwlock_reader_lock(rwlock_t *lock);
void rwlock_reader_unlock(rwlock_t *lock);
void rwlock_writer_lock(rwlock_t *lock);
void rwlock_writer_unlock(rwlock_t *lock);
#endif //THREADPROJ_RWLOCK_H
