#include <stdio.h>
#include <stdlib.h>
#include "mythreads.h"

static volatile int counter = 0;

// add any global declarations here
pthread_mutex_t lock;

void *thread_count(void *args) {
  printf("%s: begin\n", (char *) args);
  print_thread_info();
  for (int i = 0; i < 10000000; i++) {
      Pthread_mutex_lock(&lock);
      counter = counter + 1;
      Pthread_mutex_unlock(&lock);
  }
  printf("%ld done\n",Thread_gettid());

  return NULL;
}

int main (int argc, char **argv)
{
  pthread_t p1, p2;
  Pthread_mutex_init(&lock, NULL);
  Pthread_create(&p1,NULL,thread_count,"A");
  Pthread_create(&p2,NULL,thread_count,"B");
  Pthread_join(p1,NULL);
  Pthread_join(p2,NULL);
  printf("main: result %d\n", counter);
  return (0);
}
