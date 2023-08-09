#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>
#include <sys/wait.h>
#include "my_fork.h"


int main() {
    // TODO: Edit this
    int pid = my_fork();
    int status;
    char* myargv[] = {"/bin/ls", "/bin", NULL};
    if (pid == 0){
        printf("Hello from child\n");
        my_execv(myargv[0], myargv);
    }else{
        my_waitpid(pid, &status, 0);
        printf("Hello from parent, child pid = %d\n",pid);
    }
    return 0;
}