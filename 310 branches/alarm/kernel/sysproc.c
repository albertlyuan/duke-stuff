#include "types.h"
#include "riscv.h"
#include "defs.h"
#include "date.h"
#include "param.h"
#include "memlayout.h"
#include "spinlock.h"
#include "proc.h"

uint64
sys_exit(void)
{
  int n;
  if(argint(0, &n) < 0)
    return -1;
  exit(n);
  return 0;  // not reached
}

uint64
sys_getpid(void)
{
  return myproc()->pid;
}

uint64
sys_fork(void)
{
  return fork();
}

uint64
sys_wait(void)
{
  uint64 p;
  if(argaddr(0, &p) < 0)
    return -1;
  return wait(p);
}

uint64
sys_sbrk(void)
{
  int addr;
  int n;

  if(argint(0, &n) < 0)
    return -1;
  addr = myproc()->sz;
  if(growproc(n) < 0)
    return -1;
  return addr;
}

uint64
sys_sleep(void)
{
  int n;
  uint ticks0;

  if(argint(0, &n) < 0)
    return -1;
  acquire(&tickslock);
  ticks0 = ticks;
  while(ticks - ticks0 < n){
    if(myproc()->killed){
      release(&tickslock);
      return -1;
    }
    sleep(&ticks, &tickslock);
  }
  release(&tickslock);
  return 0;
}

uint64
sys_kill(void)
{
  int pid;

  if(argint(0, &pid) < 0)
    return -1;
  return kill(pid);
}

// return how many clock tick interrupts have occurred
// since start.
uint64
sys_uptime(void)
{
  uint xticks;

  acquire(&tickslock);
  xticks = ticks;
  release(&tickslock);
  return xticks;
}

uint64 sys_sigalarm(void){
  int ticks;
  uint64 handler;
  if(argint(0, &ticks) < 0)
    return -1;
  if(argaddr(1, &handler) < 0)
    return -1;

  struct proc *p = myproc();
  p->handler = handler;
  p->alarmInterval = ticks;

  return 0;
}

uint64 sys_sigreturn(void){
  struct proc *p = myproc();
  p->trapframe->kernel_satp = p->savedState.kernel_satp;
  p->trapframe->kernel_sp = p->savedState.kernel_sp;
  p->trapframe->kernel_trap = p->savedState.kernel_trap;
  p->trapframe->epc = p->savedState.epc;
  p->trapframe->kernel_hartid = p->savedState.kernel_hartid;
  p->trapframe->ra = p->savedState.ra;
  p->trapframe->sp = p->savedState.sp;
  p->trapframe->gp = p->savedState.gp;
  p->trapframe->tp = p->savedState.tp;
  p->trapframe->t0 = p->savedState.t0;
  p->trapframe->t1 = p->savedState.t1;
  p->trapframe->t2 = p->savedState.t2;
  p->trapframe->s0 = p->savedState.s0;
  p->trapframe->s1 = p->savedState.s1;
  p->trapframe->a0 = p->savedState.a0;
  p->trapframe->a1 = p->savedState.a1;
  p->trapframe->a2 = p->savedState.a2;
  p->trapframe->a3 = p->savedState.a3;
  p->trapframe->a4 = p->savedState.a4;
  p->trapframe->a5 = p->savedState.a5;
  p->trapframe->a6 = p->savedState.a6;
  p->trapframe->a7 = p->savedState.a7;
  p->trapframe->s2 = p->savedState.s2;
  p->trapframe->s3 = p->savedState.s3;
  p->trapframe->s4 = p->savedState.s4;
  p->trapframe->s5 = p->savedState.s5;
  p->trapframe->s6 = p->savedState.s6;
  p->trapframe->s7 = p->savedState.s7;
  p->trapframe->s8 = p->savedState.s8;
  p->trapframe->s9 = p->savedState.s9;
  p->trapframe->s10 = p->savedState.s10;
  p->trapframe->s11 = p->savedState.s11;
  p->trapframe->t3 = p->savedState.t3;
  p->trapframe->t4 = p->savedState.t4;
  p->trapframe->t5 = p->savedState.t5;
  p->trapframe->t6 = p->savedState.t6;

  p->handlerComplete = 1;
  p->handlerRunning = 0;
  return 0;
}
