#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <unistd.h>
#include <sys/wait.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <string.h>
#include <time.h>
#include <assert.h>
#include "my_fork.h"

static FILE *trfd = NULL;
static char *tr_name = NULL;

char *get_prog_name() {
    char *exe_name = NULL;
    static char pfile[256];
    static struct stat stat_buf;
    sprintf(pfile,"/proc/%d/status",getpid());
    if (stat(pfile,&stat_buf)) {
        printf("Error opening tracefile, contact course staff\n");
        exit(1);
    } else {
        exe_name = (char *) malloc(256); // hope that is big enough!
        FILE *pfd = fopen(pfile,"r");
        if (fscanf(pfd,"Name: %s\n",exe_name) != 1) {
            printf("Error getting tracefile information, contact course staff\n");
            exit(1);
        }
    }
    return exe_name;
}

void check_process_state(int pid) {
    static char pfile[256];
    static struct stat stat_buf;
    sprintf(pfile,"/proc/%d",pid);    
    
    if (stat(pfile,&stat_buf)) {
        fprintf(trfd,"%s\n",pfile);
        fprintf(trfd,"Process not created correctly...pid %d\n",pid);
        exit(1);
    }
    
}

int my_fork() {
    if (tr_name == NULL) {
        char *prog_name  = get_prog_name();
        if ((prog_name[0] == '.') && prog_name[1] == '/') {
            prog_name += 2;
        }
        tr_name = (char *) malloc(strlen(prog_name) + 16);  // some padding
        sprintf(tr_name,"output/%s.tr",prog_name);
        // check for output directory
        static struct stat stat_buf;
        if (stat("output",&stat_buf)) {
            assert(mkdir("output",0700) == 0);
        }
        trfd = fopen(tr_name,"w");
        assert(trfd != NULL);
    }
    
    int pid = fork();
    assert(pid >= 0);
    check_process_state(getpid());
    if (pid > 0) {
        fprintf(trfd,"my_fork: pid %d cid = %d\n", getpid(), pid);
        fflush(trfd);
    }
    return (pid);
}

int my_waitpid(int pid, int *status, int options) {
    fprintf(trfd,"my_waitpid: pid %d wait for cid %d\n",getpid(),pid);
    fflush(trfd);
    waitpid(pid, status, options);
}

int my_execv(const char *path, char *const myargv[]) {
    nanosleep((const struct timespec[]){{0, 2000L}}, NULL);
    fprintf(trfd,"my_execv: pid %d calling execv with args ", getpid());
    fflush(trfd);
    int count = 0;
    char *arg = myargv[count];
    while (myargv[count] != NULL) {
        fprintf(trfd,"%s ",myargv[count]);
        count++;
    }
    fprintf(trfd,"\n");
    fflush(trfd);
    execv(path, myargv);
}