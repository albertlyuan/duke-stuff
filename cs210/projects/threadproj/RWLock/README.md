
## Reader Writer Locks

Another classic synchronization primitive is a reader-writer lock. You have seen in previous assignments that
race conditions are a common problem in parallel programming contexts. One solution to this is to wrap important
sections of code (critical sections) with mutual exclusion locks (mutexes). This ensures that all structures can
stay in a consistent state relative to one another. For instance, inserting an item into a list modifies both the
list structure itself and the size counter for the list.

A big inconvenience here is for users that don't wish to modify any data, just to read it. There isn't any harm in
letting multiple concurrent readers traverse a list if they aren't changing anything. But if a writer wishes to change
something, they must prevent new readers from entering a code section, wait for all readers to finish their reading
operation, and only then begin writing to the structure. Once the writer finishes, other threads are allowed to
access the structure again.

The API for the reader/writer lock (RW Lock) is slightly different from a normal lock. Instead of a single pair `lock()` and
`unlock()` functions, we now have two: `reader_lock()`/`reader_unlock()` and `writer_lock()`/`writer_unlock()`.

### Reader Writer Lock Test Program

We have developed two tests to verify the functionality of the RW Locks you will implement. One is a simple test to
verify that you can support multiple readers in the same critical region. 


```c
void* multiple_reader_test_thread(void * arg) {
  rwlock_t *lock = (rwlock_t*)arg;
  rwlock_reader_lock(lock);
  pthread_mutex_lock(&testing_lock);
  testing_counter++;
  pthread_mutex_unlock(&testing_lock);
  return NULL;
}
```

In the above code, multiple readers acquire, but never release the RW Lock. They increment a counter to show the main
thread how many threads have been able to enter the critical region simultaneously. For a correct RW Lock implementation,
all reader threads should be able to increment the counter and finish execution successfully.

The next test is slightly more complex and includes cooperation of reader and writer threads.
The basic structure is a list of numbers where `A[0]` is a randomly generated number and for every `i>0`,
`A[i]-A[i-1]=0`. Whenever a writer begins a write operation, it generates a new random number and walks up the list,
updating the other values based on the new `A[0]`. Whenever a reader begins a read operation, it walks down the list
verifying that the condiiton `A[i]-A[i-1]=1`
