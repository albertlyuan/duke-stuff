#include "kernel/types.h"
#include "kernel/stat.h"
#include "user/user.h"

int
main(int argc, char *argv[]){
    

    int p[2]; 
    char buf[1];

    pipe(p); 
    int pid = fork();
    if(pid == 0) { //child
        while (read(p[0], buf, 1) < 1){
            continue;
        }
        printf("%d: received ping\n", pid);
        write(p[1], buf, 1); 

        close(p[0]);
        close(p[1]);
    } else { //parent
        write(p[1], "1", 1); 
        wait(0);
        while (read(p[0], buf, 1) < 1){
            continue;
        }
        printf("%d: received pong\n", pid);
        close(p[0]);
        close(p[1]);
    }
    exit(0);
}