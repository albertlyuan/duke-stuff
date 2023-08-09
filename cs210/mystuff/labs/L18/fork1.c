#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>
#include "my_fork.h"

int main() {
    // TODO: Edit this
    int pid = my_fork();
    if (pid == 0){
        printf("Hello from child\n");
    }else{
        printf("Hello from parent, child pid = %d\n",pid);
    }
    return 0;
}