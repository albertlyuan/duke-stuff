#include "types.h"
#include "param.h"
#include "spinlock.h"
#include "sleeplock.h"
#include "fs.h"
#include "file.h"
#include "memlayout.h"
#include "riscv.h"
#include "defs.h"
#include "proc.h"

struct spinlock randomlock;
uint64 randomnum = 10;

/* The state must be initialized to non-zero */
uint64 xorshift()
{
	/* Algorithm "xor" from p. 4 of Marsaglia, "Xorshift RNGs" */
    uint64 x = randomnum;
	x ^= x << 13;
	x ^= x >> 7;
	x ^= x << 17;
    randomnum = x;
	return x;
}

int
randomwrite(struct file *f, int user_src, uint64 src, int n)
{   
    acquire(&randomlock);
    release(&randomlock);
    return n;
    
}

int
randomread(struct file *f, int user_dst, uint64 dst, int n)
{
    uint64 randombyte;

    acquire(&randomlock);
    int num_reads = 8;
    for(int i = 0; i<num_reads;i++){
        randombyte = xorshift();
        either_copyout(user_dst, dst, &randombyte, 1);
        dst += 1;
    }
    release(&randomlock);
    return num_reads;
}


void
randominit(void)
{
    initlock(&randomlock, "random_lock");
    devsw[RANDOM_DEVICE].read = randomread;
    devsw[RANDOM_DEVICE].write = randomwrite;
}
