#ifndef __MY_FORK_
#define __MY_FORK_


int my_fork(); // returns a PID for a fork call
int my_waitpid(int pid, int * status, int options); // 
int my_execv(const char *path, char *const argv[]);

#endif