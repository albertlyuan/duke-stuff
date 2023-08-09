#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>
#include <sys/wait.h>
#include "my_fork.h"

int main()
{
    int value = 1234;
    int pid = my_fork();
    int status;
    if (pid == 0){
        printf("Before: Hello from child, &value = 0x%p, value = %d\n",&value,value);
        value = 120;
        printf("After: Hello from child, &value = 0x%p, value = %d\n",&value,value);
        return 0;
    }
    my_waitpid(pid, &status, 0);
    printf("Hello from parent, &value = 0x%p, value = %d, child pid = %d\n",&value,value,pid);
    

    return 0;

}