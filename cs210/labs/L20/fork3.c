#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>
#include <sys/wait.h>
#include <sys/mman.h>
#include "my_fork.h"


int main()
{

#ifdef STUDENT

    return 0;

#else
   int *shared_value = (int *) mmap(NULL, sizeof *shared_value, PROT_READ | PROT_WRITE, 
                    MAP_SHARED | MAP_ANONYMOUS, -1, 0);

    *shared_value = 1234;

    int pid = my_fork();
    if (pid < 0) {
        printf("Error w/ fork\n");
    }
    else if (pid == 0) {
        printf("Before: Hello from child, &value = 0x%p, value = %d\n", shared_value, *shared_value);
        *shared_value = 210;
        printf("After: Hello from child, &value = 0x%p, value = %d\n",  shared_value, *shared_value);
    }
    else {
        printf("Before Wait: Hello from parent, &value = 0x%p, value = %d, child pid = %d\n", shared_value, *shared_value, pid);
        int status = 0;
        int retpid = my_waitpid(pid,&status,0);
        if (retpid < 0)
            printf("Error in wait\n");
        printf("After Wait: Hello from parent, &value = 0x%p, value = %d, child pid = %d\n", shared_value, *shared_value, pid);
    }
    munmap(shared_value, sizeof *shared_value);
    return 0;
#endif
}