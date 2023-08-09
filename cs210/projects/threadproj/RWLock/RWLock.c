//
// Created by Chris Kjellqvist (cmk91@duke.edu) on 8/19/21.
//

#include "RWLock.h"
#include <unistd.h>

void rwlock_init(rwlock_t *lock) {
#ifndef STUDENT
  pthread_mutex_init(&lock->scope_mutex, NULL);
  pthread_mutex_init(&lock->counter_mutex, NULL);
  SCL_init(&lock->writer_reader_wait, &lock->readers, 0);
  lock->readers = 0;
#endif
}

void rwlock_reader_lock(rwlock_t *lock) {
#ifndef STUDENT
  pthread_mutex_lock(&lock->scope_mutex);
  pthread_mutex_lock(&lock->counter_mutex);
  lock->readers++;
  pthread_mutex_unlock(&lock->counter_mutex);
  pthread_mutex_unlock(&lock->scope_mutex);
#endif
}
void rwlock_reader_unlock(rwlock_t *lock) {
#ifndef STUDENT
  pthread_mutex_lock(&lock->counter_mutex);
  lock->readers--;
  pthread_mutex_unlock(&lock->counter_mutex);
#endif
}


void rwlock_writer_lock(rwlock_t *lock) {
#ifndef STUDENT
  pthread_mutex_lock(&lock->scope_mutex);
  SCL_wait(&lock->writer_reader_wait);
#endif
}
void rwlock_writer_unlock(rwlock_t *lock) {
#ifndef STUDENT
  pthread_mutex_unlock(&lock->scope_mutex);
#endif
}
