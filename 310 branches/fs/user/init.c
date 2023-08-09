// init: The initial user-level program

#include "kernel/types.h"
#include "kernel/stat.h"
#include "user/user.h"
#include "kernel/fcntl.h"

char *argv[] = { "sh", 0 };

int
main(void)
{
  int pid, wpid, fd;

  if(open("console", O_RDWR) < 0){
    mknod("console", 1, 1);
    open("console", O_RDWR);
  }
  mkdir("dev");


  if((fd = open("/dev/null", O_RDWR)) < 0){
    mknod("/dev/null", 2, 1);
  }else{
    close(fd);
  }
  if((fd = open("/dev/zero", O_RDWR)) < 0){
    mknod("/dev/zero", 3, 1);
  }else{
    close(fd);
  }
  if((fd = open("/dev/random", O_RDWR)) < 0){
    mknod("/dev/random", 4, 1);
  }else{
    close(fd);
  }
  if((fd = open("/dev/uptime", O_RDWR)) < 0){
    mknod("/dev/uptime", 5, 1);
  }else{
    close(fd);
  }
  
  dup(0);  // stdout
  dup(0);  // stderr

  for(;;){
    printf("init: starting sh\n");
    pid = fork();
    if(pid < 0){
      printf("init: fork failed\n");
      exit(1);
    }
    if(pid == 0){
      exec("sh", argv);
      printf("init: exec sh failed\n");
      exit(1);
    }

    for(;;){
      // this call to wait() returns if the shell exits,
      // or if a parentless process exits.
      wpid = wait((int *) 0);
      if(wpid == pid){
        // the shell exited; restart it.
        break;
      } else if(wpid < 0){
        printf("init: wait returned an error\n");
        exit(1);
      } else {
        // it was a parentless process; do nothing.
      }
    }
  }
}
