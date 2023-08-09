#ifndef RWLOCK_H
#define RWLOCK_H

typedef struct {
	pthread_mutex_t     mutex;
	pthread_cond_t      r_cond;
	int                 r_active;
	int                 r_wait;
	pthread_cond_t      w_cond[3];
	int                 w_active[3];
	int                 w_wait[3];
	/*
	int                 w_active;
	pthread_cond_t      w_cond;
	int                 w_wait;
	
	*/
	
	
	
}rwl;

void rwl_init(rwl *l);
void rwl_rlock(rwl *l);
void rwl_runlock(rwl *l);
void rwl_wlock(rwl *l, int priority);
void rwl_wunlock(rwl *l, int priority);

#endif