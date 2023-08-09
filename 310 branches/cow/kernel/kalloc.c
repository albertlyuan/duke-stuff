// Physical memory allocator, for user processes,
// kernel stacks, page-table pages,
// and pipe buffers. Allocates whole 4096-byte pages.

#include "types.h"
#include "param.h"
#include "memlayout.h"
#include "spinlock.h"
#include "riscv.h"
#include "defs.h"

void freerange(void *pa_start, void *pa_end);

extern char end[]; // first address after kernel.
                   // defined by kernel.ld.
int reference_count[PHYSTOP/PGSIZE] = { 0 };
struct spinlock refcount_lock;

struct run {
  struct run *next;
};

struct {
  struct spinlock lock;
  struct run *freelist;
  uint64 nfree;
} kmem;

void
kinit()
{ 
  initlock(&refcount_lock, "refcount");
  initlock(&kmem.lock, "kmem");
  freerange(end, (void*)PHYSTOP);
}

void
freerange(void *pa_start, void *pa_end)
{
  char *p;
  p = (char*)PGROUNDUP((uint64)pa_start);
  for(; p + PGSIZE <= (char*)pa_end; p += PGSIZE)
    kfree(p);
}

// Free the page of physical memory pointed at by v,
// which normally should have been returned by a
// call to kalloc().  (The exception is when
// initializing the allocator; see kinit above.)
void
kfree(void *pa)
{
  int rc; 
  struct run *r;

  if(((uint64)pa % PGSIZE) != 0 || (char*)pa < end || (uint64)pa >= PHYSTOP)
    panic("kfree");
  rc = dec_refcount((uint64) pa);

  if (rc > 0)
    return;

  // Fill with junk to catch dangling refs.
  memset(pa, 1, PGSIZE);

  r = (struct run*)pa;

  acquire(&kmem.lock);
  r->next = kmem.freelist;
  kmem.freelist = r;
  kmem.nfree++;
  release(&kmem.lock);
}

// Allocate one 4096-byte page of physical memory.
// Returns a pointer that the kernel can use.
// Returns 0 if the memory cannot be allocated.
void *
kalloc(void)
{
  struct run *r;

  acquire(&kmem.lock);
  r = kmem.freelist;
  if(r){
    kmem.freelist = r->next;
    kmem.nfree--;
  }
  release(&kmem.lock);

  if(r)
    memset((char*)r, 5, PGSIZE); // fill with junk
  inc_refcount((uint64)r);
  //printf("fork %p %d\n", (void*)r, inc_refcount((uint64)r));
  return (void*)r;
}

uint64
sys_nfree(void)
{
  return kmem.nfree;
}

int 
inc_refcount(uint64 pa){
  int refcount;
  uint64 idx;

  if(((uint64)pa % PGSIZE) != 0 || (char*)pa < end || (uint64)pa >= PHYSTOP)
    return -1;

  idx = PGROUNDDOWN(pa)/PGSIZE;
  acquire(&refcount_lock);
  refcount = reference_count[idx];
  reference_count[idx] += 1;
  release(&refcount_lock);

  return refcount + 1;
}

int 
dec_refcount(uint64 pa){
  int refcount;
  uint64 idx;

  if(((uint64)pa % PGSIZE) != 0 || (char*)pa < end || (uint64)pa >= PHYSTOP)
    return -1;
    
  idx = PGROUNDDOWN(pa)/PGSIZE;
  acquire(&refcount_lock);
  refcount = reference_count[idx];
  release(&refcount_lock);
  if (refcount > 0){
    acquire(&refcount_lock);
    reference_count[idx] -= 1;
    release(&refcount_lock);
    return refcount - 1;
  }
  return refcount;

}