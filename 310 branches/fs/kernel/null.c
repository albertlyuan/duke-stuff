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

struct spinlock nulllock;

int
nullwrite(struct file *f, int user_src, uint64 src, int n)
{   
    acquire(&nulllock);
    release(&nulllock);
    return n;
    
}

int
nullread(struct file *f, int user_dst, uint64 dst, int n)
{
    acquire(&nulllock);
    release(&nulllock);
    return 0;
}

void
nullinit(void)
{
    initlock(&nulllock, "null_lock");
    devsw[NULL_DEVICE].read = nullread;
    devsw[NULL_DEVICE].write = nullwrite;
}
