#include <stdio.h>  // needed for size_t etc.
#include <unistd.h> // needed for sbrk etc.
#include <sys/mman.h> // needed for mmap
#include <assert.h> // needed for asserts
#include "dmm.h"

/* 
 * The lab handout and code guide you to a solution with a single free list containing all free
 * blocks (and only the blocks that are free) sorted by starting address.  Every block (allocated
 * or free) has a header (type metadata_t) with list pointers, but no footers are defined.
 * That solution is "simple" but inefficient.  You can improve it using the concepts from the
 * reading.
 */

/* 
 *size_t is the return type of the sizeof operator.   size_t type is large enough to represent
 * the size of the largest possible object (equivalently, the maximum virtual address).
 */

typedef struct metadata {
  size_t size;
  int allocated;
  struct metadata* next;
  struct metadata* prev;
} metadata_t;

/*
 * Head of the freelist: pointer to the header of the first free block.
 */

static metadata_t* freelist = NULL;

void* dmalloc(size_t numbytes) {

  if(freelist == NULL) {
    if(!dmalloc_init()) {
      return NULL;
    }
  }

  assert(numbytes > 0);

  /* your code here */
  if (numbytes + METADATA_T_ALIGNED > freelist->size){
    return NULL;
  }

  if (freelist->size - (numbytes + METADATA_T_ALIGNED) < METADATA_T_ALIGNED){
    return NULL;
  }


  freelist->size -= (numbytes + METADATA_T_ALIGNED);
  //printf("freelist size %d num bytes %d\n",freelist->size, numbytes);

  metadata_t* usedBlock = (void*)freelist + (freelist->size);
  usedBlock->size = numbytes;
  
  usedBlock->next = freelist->next;
  usedBlock->prev = freelist;
  if (usedBlock->next != NULL){
    usedBlock->next->prev = usedBlock;
  }
  usedBlock->allocated = 1;

  freelist->next = usedBlock;
  //print_freelist();
  
  return (void*)usedBlock + METADATA_T_ALIGNED;
}

void dfree(void* ptr) {
  /* your code here */
  metadata_t* header = (void*)ptr - METADATA_T_ALIGNED;
  header->allocated = 0;
  
  if (header->prev != NULL && header->prev->allocated==0){
    header->prev->size += (header->size + METADATA_T_ALIGNED);
    header->prev->next = header->next;
    
    header = header->prev;
    if (header->next != NULL){
      header->next->prev = header;
    }
  }

  if (header->next != NULL && header->next->allocated==0){
    header->size += (header->next->size + METADATA_T_ALIGNED);
    header->next = header->next->next;

    if (header->next != NULL){
      header->next->prev = header;
    }
    
  }
  
  

}

/*
 * Allocate heap_region slab with a suitable syscall.
 */
bool dmalloc_init() {

  size_t max_bytes = ALIGN(MAX_HEAP_SIZE);

  /*
   * Get a slab with mmap, and put it on the freelist as one large block, starting
   * with an empty header.
   */
  freelist = (metadata_t*)
     mmap(NULL, max_bytes, PROT_READ | PROT_WRITE, MAP_PRIVATE | MAP_ANONYMOUS, -1, 0);

  if (freelist == (void *)-1) {
    perror("dmalloc_init: mmap failed");
    return false;
  }
  freelist->next = NULL;
  freelist->prev = NULL;
  freelist->size = max_bytes-METADATA_T_ALIGNED;
  freelist->allocated = 0;
  return true;
}


/* for debugging; can be turned off through -NDEBUG flag*/


//This code is here for reference.  It may be useful.
//Warning: the NDEBUG flag also turns off assert protection.


void print_freelist(); 
void a();
#ifdef NDEBUG
	#define DEBUG(M, ...)
	#define PRINT_FREELIST print_freelist
#else
	#define DEBUG(M, ...) fprintf(stderr, "[DEBUG] %s:%d: " M "\n", __FILE__, __LINE__, ##__VA_ARGS__)
	#define PRINT_FREELIST
#endif


void print_freelist() {
  metadata_t *freelist_head = freelist;
  while(freelist_head != NULL) {
    DEBUG("\tFreelist Size:%zd, Head:%p, Prev:%p, Next:%p\t",
	  freelist_head->size,
	  freelist_head,
	  freelist_head->prev,
	  freelist_head->next);
    freelist_head = freelist_head->next;
  }
  DEBUG("\n");
}

void a(metadata_t *header){
  DEBUG("\theader Size:%zd, Head:%p, Prev:%p, Next:%p\t",
	  header->size,
	  header,
	  header->prev,
	  header->next);
  DEBUG("\n");
}

