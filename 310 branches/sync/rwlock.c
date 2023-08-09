#include <stdio.h>
#include <time.h>
#include <errno.h>
#include <pthread.h>
#include <assert.h>
#include "rwlock.h"

/* rwl implements a reader-writer lock.
 * A reader-write lock can be acquired in two different modes, 
 * the "read" (also referred to as "shared") mode,
 * and the "write" (also referred to as "exclusive") mode.
 * Many threads can grab the lock in the "read" mode.  
 * By contrast, if one thread has acquired the lock in "write" mode, no other 
 * threads can acquire the lock in either "read" or "write" mode.
 */



//rwl_init initializes the reader-writer lock 
void
rwl_init(rwl *l)
{
	// initialization of read/write lock
	pthread_mutex_init(&l->mutex, NULL);

	pthread_cond_init(&l->r_cond, NULL);

	l->r_active = 0;
	l->r_wait = 0;

	for (int i = 0; i<3; i++){
		pthread_cond_init(&l->w_cond[i], NULL);
		l->w_active[i] = 0;
		l->w_wait[i] = 0;
	}
	
}

int writers_active(rwl *l){
	//true(1) if any writers are waiting, false if otherwise
	for (int i = 0; i<3; i++){
		if (l->w_active[i] > 0){
			return i;
		}
	}
	return -1;
}

int writers_waiting(rwl *l){
	//true(1) if any writers are waiting, false if otherwise
	for (int i = 0; i<3; i++){
		if (l->w_wait[i] > 0){
			return i;
		}
	}
	return -1;
}

//rwl_rlock attempts to grab the lock in "read" mode
void
rwl_rlock(rwl *l)
{
	// your code here
	pthread_mutex_lock(&l->mutex);
	l->r_wait += 1;
	while (writers_waiting(l) >= 0 || writers_active(l) >= 0){
		pthread_cond_wait(&l->r_cond, &l->mutex);
	}
	l->r_wait -= 1;
	l->r_active += 1;
	
	pthread_mutex_unlock(&l->mutex);
}




//rwl_runlock unlocks the lock held in the "read" mode
void
rwl_runlock(rwl *l)
{
	int waiter;
	// your code here
	pthread_mutex_lock(&l->mutex);
	l->r_active -= 1;
	if (l->r_active == 0){
		waiter = writers_waiting(l);
		if (waiter >= 0){
			pthread_cond_signal(&l->w_cond[waiter]);
		}
	}
	
	pthread_mutex_unlock(&l->mutex);
}


//rwl_wlock attempts to grab the lock in "write" mode
void
rwl_wlock(rwl *l, int priority)
{			
	// your code here
	int waiter = writers_active(l);
	pthread_mutex_lock(&l->mutex);
	l->w_wait[priority] += 1;

	if (waiter >= 0 || l->r_active > 0){
		pthread_cond_wait(&l->w_cond[priority], &l->mutex);
	}
	l->w_wait[priority] -= 1;
	l->w_active[priority] += 1;

	pthread_mutex_unlock(&l->mutex);
}

//rwl_wunlock unlocks the lock held in the "write" mode
void
rwl_wunlock(rwl *l, int priority)
{
	// your code here
	int waiter = writers_waiting(l);
	pthread_mutex_lock(&l->mutex);
	l->w_active[priority] -= 1;
	if (waiter >= 0){
		pthread_cond_signal(&l->w_cond[waiter]);
	}else{
		pthread_cond_broadcast(&l->r_cond);
	}
	
	pthread_mutex_unlock(&l->mutex);
}