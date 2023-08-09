all: prog1

prog1: prog1.c
	gcc -Og -o $@ prog1.c 

clean:
	rm -f prog1 tests/*_actual* tests/*_diff*
	rm -rf __pycache__/
