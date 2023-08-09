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

struct spinlock zerolock;

int
zerowrite(struct file *f, int user_src, uint64 src, int n)
{   
    acquire(&zerolock);
    release(&zerolock);
    return n;
    
}

int
zeroread(struct file *f, int user_dst, uint64 dst, int n)
{
    acquire(&zerolock);
    int null_byte = 0;
    int num_reads = 8;
    for(int i = 0; i<num_reads;i++){
        either_copyout(user_dst, dst, &null_byte, 1);
        dst += 1;
    }
    release(&zerolock);
    return num_reads;
}

void
zeroinit(void)
{
    initlock(&zerolock, "zero_lock");
    devsw[ZERO_DEVICE].read = zeroread;
    devsw[ZERO_DEVICE].write = zerowrite;
}
