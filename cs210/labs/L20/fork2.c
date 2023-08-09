#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>
#include <sys/wait.h>
#include "my_fork.h"

int main()
{
    int value = 1234;
  
#ifdef STUDENT

    return 0;

#else
    int pid = my_fork();
    if (pid < 0) {
        printf("Error w/ fork\n");
    }
    else if (pid == 0) {
        printf("Before: Hello from child, &value = 0x%p, value = %d\n", &value, value);
        value = 210;
        printf("After: Hello from child, &value = 0x%p, value = %d\n", &value, value);
    }
    else {
        int status = 0;
        int retpid = my_waitpid(pid,&status,0);
        if (retpid < 0)
            printf("Error in wait\n");
        printf("Hello from parent, &value = 0x%p, value = %d, child pid = %d\n", &value, value, pid);
    }

    return 0;
#endif
}