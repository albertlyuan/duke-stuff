#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>
#include <sys/wait.h>
#include "my_fork.h"


int main()
{
  
#ifdef STUDENT

    return 0;

#else
    int pid = my_fork();
    if (pid == 0) {
        printf("Hello from child\n");
    }
    else {
        int status = 0;
        int retpid = my_waitpid(pid,&status,0);
        if (retpid < 0)
            printf("Error in wait\n");
        printf("Hello from parent, child pid = %d\n",pid);
    }

    return 0;
#endif
}