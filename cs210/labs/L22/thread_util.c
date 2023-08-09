#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/stat.h>
#include <sys/types.h>

#include "mythreads.h"


#define NUM_LOCKS 2048

typedef struct lock_info{
  pthread_mutex_t *lock_addr;
  int l_count, u_count;
} lock_info_t;

static lock_info_t lock_trace[NUM_LOCKS];
static int lock_count = 0;
static int thread_count = 0;


void trace_lock(pthread_mutex_t *m) {
  for (int i = 0; i < lock_count; i++) {
    if (lock_trace[i].lock_addr == m) {
      __atomic_add_fetch(&lock_trace[i].l_count, 1, __ATOMIC_SEQ_CST);
      break;
    }
  }
}

void trace_unlock(pthread_mutex_t *m) {
  for (int i = 0; i < lock_count; i++) {
    if (lock_trace[i].lock_addr == m) {
      __atomic_add_fetch(&lock_trace[i].u_count, 1, __ATOMIC_SEQ_CST);
      break;
    }
  }
}

static void clear_lock_info() {
  lock_count = 0;
  memset((void *) lock_trace, 0, sizeof(lock_info_t)*NUM_LOCKS);
}

static void check_lock_stats() {
  int unique_locks = 0;
  for (int i = 0; i < NUM_LOCKS; i++) {
    if (lock_trace[i].lock_addr && lock_trace[i].l_count) {
      unique_locks++;
      if (lock_trace[i].l_count != lock_trace[i].u_count)
        fprintf(stderr,"Looks like you have a mismatch in number of locks %d & unlocks %d for one of your locks\n",lock_trace[i].l_count,lock_trace[i].u_count);
    }
  }
  if (unique_locks < 2) {
    fprintf(stderr,"You only used %d unique lock(s)\n", unique_locks);
    exit(1);
  }

}

void Pthread_mutex_init(pthread_mutex_t *mutex,
			const pthread_mutexattr_t *attr) {
    int rc = pthread_mutex_init(mutex, attr);
    assert(rc == 0);
    lock_trace[lock_count].lock_addr = mutex;
    lock_trace[lock_count].l_count = 0;
    lock_trace[lock_count].u_count = 0;
    lock_count++;
}

void Pthread_mutex_lock(pthread_mutex_t *m) {
    int rc = pthread_mutex_lock(m);
    assert(rc == 0);
    trace_lock(m);
}
                                                                                
void Pthread_mutex_unlock(pthread_mutex_t *m) {
    int rc = pthread_mutex_unlock(m);
    assert(rc == 0);
    trace_unlock(m);
}
                                                                                
void Pthread_create(pthread_t *thread, const pthread_attr_t *attr, 	 
		    void *(*start_routine)(void*), void *arg) {
    int rc = pthread_create(thread, attr, start_routine, arg);
    assert(rc == 0);
}

void Pthread_join(pthread_t thread, void **value_ptr) {
    int rc = pthread_join(thread, value_ptr);
    assert(rc == 0);
}

// use this to obtain a thread id from the system.
long Thread_gettid() {
  return(syscall(SYS_gettid));
}

// simple print_thread_info routine
void print_thread_info() {
    static char tfile[256];
    static struct stat stat_buf;
    int pid = getpid();
    long tid = Thread_gettid();
    sprintf(tfile,"/proc/%d/task/%ld",pid,tid);
    if (stat(tfile,&stat_buf)) {
        printf("%s\n",tfile);
        printf("Threads not created correctly...pid %d tid %ld\n",pid,tid);
        exit(1);
    }
    printf("pid %d tid %ld\n", pid, tid);
}


// worker routine where we run tests from
void *thread_worker(void *args) {
  int *id = (int *) args;
  print_thread_info();
  // put any work here
  printf("%ld done\n",Thread_gettid());
  return NULL;
}

void my_threads_init() {
  clear_lock_info();
}

void my_threads_end() {
  check_lock_stats();
}