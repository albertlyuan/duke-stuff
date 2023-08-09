#include <signal.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/resource.h>

void sigint_sigaction(int signal, siginfo_t *si, void *arg)
{
#ifdef STUDENT
    // TODO: Implement function to exit on the third
    // instance of user ^C input
#else
    static int count = 0;
    if (count == 0) {
        printf("Hmm trying to stop this program are you?\n");
        count++;
    } else if (count == 1) {
        printf("You are persistent!\n");
        count++;
    } else if (count >= 2) {
        printf("OK you win.\n");
        exit(1);
    }
#endif
}

int main(void)
{
    struct sigaction sa;
    struct rlimit rlim;

    rlim.rlim_cur = 20; // setting cpu limit at 20 seconds to avoid runaway processes
    setrlimit(RLIMIT_CPU,&rlim);
    
    memset(&sa, 0, sizeof(struct sigaction));
    sigemptyset(&sa.sa_mask);
    sa.sa_sigaction = sigint_sigaction;
    sa.sa_flags   = SA_SIGINFO;

    sigaction(SIGINT, &sa, NULL);

    printf("Can you stop me?\n");
    while(1);  // infinite loop

    return 0;
}