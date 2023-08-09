**Objectives**

- Compute, by hand, the number of *tag*, *set index*, and *block offset* bits for a specified cache.

---

**Overview**

This lab will give you some practice when it comes to calculating the number of bits for the tag, set index, and block offset for a specified cache. You are free to use a calculator or write a C program to check your work.

Calculation of these values may very well be on your next exam (read: will almost certainly be on your next exam) so make sure to understand the answers of this lab and the way to get to the solution well.

As always, if you have any questions, please come to office hours, post a question on Ed, ask after class, etc.

---

**Your Task**

Your task is to calculate the number of tag, set index, and block offset bits for certain cache specifications. You will put the number of bits for each part in the provided text files `q1.txt`, `q2.txt`, `q3.txt`, and `q4.txt`. This assignment follows the notation used by the CS:APP book. We recommend you read sections 6.4.1 and 6.4.2 before starting the lab. For reference, the number of tag bits corresponds to *t* in the book, the number of sets index bits corresponds to *s* in the book, and the number of block offset bits corresponds to *b* in the book.

The following are the cache configurations you will be working with:

- `q1.txt` - 32-bit addresses, 1 KB direct-mapped cache, and 32-byte blocks.

- `q2.txt` - 64-bit addresses, 1 KB direct-mapped cache, and 32-byte blocks.

- `q3.txt` - 32-bit addresses, 512 B direct-mapped cache, and 32-byte blocks.

- `q4.txt` - 64-bit addresses, 512 B direct-mapped cache, and 32-byte blocks.

In each text file, please format your numbers with spaces in between:

```text
tag set_index block_offset
```

If the number of tag bits is 20, number of set index bits is 6, and number of block offset bits is 6, then the text file would look like this:

```text
20 6 6
```

---

**cachechecker**

This is a program written for you to check your answers. This program will also be run on Gradescope to give your your final score.

You can run the program by giving it command-line arguments of the address size (in bits), cache size (in bytes), and block size (in bytes). Below is an example for `q1.txt`:

```bash
./cachechecker 32 1024 32
```

After you run `cachechecker` in this way, the program will ask you to input the three numbers for tag bits, set index bits, and block offset bits.

After entering the three numbers, the program will tell you if your answers are right or wrong.

This program will work with all reasonable combinations of address size, cache size, and block size and will **only work for direct-mapped caches**. If you need more practice, feel free to create your own combination of cache specs and try to work out the number of bits for the tag, set index, and block offset and test your answers with this program.

---

**Local Testing and Submission**

1. Fill out `q1.txt`, `q2.txt`, `q3.txt`, and `q4.txt`.

2. Run the local python test script by running the following on the unix command line:
    ```bash
    python3 test_kit.py ALL
    ```

3. Add, commit, and push the files to your git repo:
   
   `q1.txt`, `q2.txt`, `q3.txt`, and `q4.txt`

4. Submit your completed lab to Gradescope via the GitLab submission button.
