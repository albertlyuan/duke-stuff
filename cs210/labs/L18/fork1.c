#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>
#include "my_fork.h"

int main()
{
  
#ifdef STUDENT

    return 0;

#else
    int pid = my_fork();
    if (pid < 0) {
        printf("Error w/ fork\n");
    }
    else if (pid == 0) {
        printf("Hello from child\n");
    }
    else {
        printf("Hello from parent, child pid = %d\n",pid);
    }

    return 0;
#endif
}