CC = gcc
CFLAGS = -Wall -O2

PROGS = sumarrayrows \
        sumarraycols \

all: casim $(PROGS) cachechecker

casim: casim.c
	$(CC) $(CFLAGS) -o casim casim.c -lm

cachechecker: cachechecker.c
	gcc -o cachechecker cachechecker.c -lm

sumarrayrows: sumarrayrows.c
	gcc -o sumarrayrows sumarrayrows.c -lm

sumarraycols: sumarraycols.c
	gcc -o sumarraycols sumarraycols.c -lm

clean:
	rm -f casim sumarrayrows sumarraycols
	rm -f tests/*_actual* tests/*_diff*
	rm -rf __pycache__/
