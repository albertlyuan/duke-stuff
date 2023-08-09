#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>
#include <sys/wait.h>
#include <sys/mman.h>
#include "my_fork.h"


int main()
{    
    int* ptr = mmap(NULL,sizeof(int),PROT_WRITE | PROT_WRITE,MAP_SHARED | MAP_ANON,-1,0);
    
    ptr[0] = 1234;
    int pid = my_fork();
    int status;

    if (pid == 0){
        printf("Before: Hello from child, &value = 0x%p, value = %d\n",ptr,*ptr);
        *ptr = 210;
        printf("After: Hello from child, &value = 0x%p, value = %d\n",ptr,*ptr);
        munmap(NULL,4);
        return 0;
    }
    printf("Before Wait: Hello from parent,  &value = 0x%p, value = %d, child pid = %d\n",ptr,*ptr,pid);
    my_waitpid(pid, &status, 0);
    printf("After Wait: Hello from parent,  &value = 0x%p, value = %d, child pid = %d\n",ptr,*ptr,pid);

    munmap(NULL,4);


    return 0;

}