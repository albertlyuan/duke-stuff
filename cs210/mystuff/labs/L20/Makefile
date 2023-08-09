CC = gcc
CFLAGS = -Wall -O2 -g

PROGS = fork1 fork2 fork3

all: $(PROGS)

fork1: fork1.c my_fork.h .fork_util.o
	$(CC) $(CFLAGS) -o $@  $< .fork_util.o 

fork2: fork2.c my_fork.h .fork_util.o
	$(CC) $(CFLAGS) -o $@  $< .fork_util.o 

fork3: fork3.c my_fork.h .fork_util.o
	$(CC) $(CFLAGS) -o $@  $< .fork_util.o 

clean:
	rm -f $(PROGS)

