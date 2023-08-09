# KV Project Instructions

Build `.kv_store_util.o` offline so that is provided to the students. You can do
so with 
```bash
gcc -c -fvisibility=hidden -o .kv_store_util.o kv_store_util.c
```

Note that this occurs in `Makefile.dist` automatically.
