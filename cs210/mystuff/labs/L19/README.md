You task is to complete the virtual memory simulation in vasim.c

The system has a 24-bit virtual address space with 4KiB pages, the physical memory has a total
of only 64 pages.  The pagetable is a flat page table that uses a first-in-first-out FIFO replacement
policy.

You must complete two functions _get_vpn_ and _allocate_phys_page_.

**get_vpn**: returns the virtual page number of the provided address

**allocate_phys_page**: This function is called only when a mapping is invalid (a page fault). It is 
passed a pointer to the page table and the virtual page number of the access. It updates the 
page table with a new physical page for the virtual page provided.  For simplicity we assume
that no physical pages are used initially, and when all pages are used the FIFO policy is to be used for replacement.
Initial mappings from virtual to physical pages should start with physical page 0, then 1, etc. until
all 64 pages are allocated. Thereafter, the FIFO algorithm should be used.

The program has three command line arguments -f _filename_ -n _access_limit_ and -v for verbose output.
the -f argument is required, the others are optional. _access_limit_ stops simulation after that many accesses are processed
from the tracefile. We provide two trace files row_trace.txt and col_trace.txt

Run the simulator on the command line
```./vasim -f [tracefile] -n 1024```

Example output:
```
./vasim -f row_trace.txt -n 1024
Running for 1024 accesses

Accesses 1024: Hits: 1015 Faults: 9
```

python3 test_kit.py ALL

submit your completed vasim.c to gradescope
