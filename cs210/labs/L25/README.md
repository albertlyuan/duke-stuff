This lab is entirely optional.

This lab gives you exposure to managing memory resources using a circular buffer. This is a common approach
for systems/ applications to communicate with external devices (e.g., a network interface or storage).
The circular buffer uses an array of entries (in our case integers). The tricky part of a circular buffer
is detecting when it is full or empty.  For this you will use two indicies indicating the head and tail of
the buffer.  The buffer is empty when head == tail and it is full when (head+1) == tail, with
head wrapping around when it exceeds the max index in the buffer. Notice that this approach leaves one space in the buffer unused.

---

**buffer.c**

You are to modify the file buffer.c and complete the two functions _cbuf_write_ and _cbuf_read_ as follows. See
buffer.c for function prototypes.

**cbuf_write**: write an entry at the head of the buffer if there is space. Return 0 on success and -1 on failure (i.e., full).
Ignore / discard the data if the buffer is full.

**cbuf_read**: read an entry from the tail of the buffer.  Return 0 on success and -1 on failure (i.e., empty).

There are four simple test functions in the file, you can run each individually by using the -t <test #> option. There is 
also another argument for the max length (entries) of teh circular buffer -l <length>.

./buffer -t 3 -l 1024 will run test 3 with a buffer size of 1024

---

**Local Testing and Submission**

Run the Python tester as in previous labs:
```bash
python3 test_kit.py ALL
```

There is no Gradescope assignment associated with this lab.
