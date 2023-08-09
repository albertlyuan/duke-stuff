all: mystery1 prog1 mystery2

mystery1: mystery1.c
	gcc -Og -fno-stack-protector -o $@ mystery1.c

mystery2: mystery2.c
	gcc -Og -fno-stack-protector -o $@ mystery2.c

prog1: prog1.c
	gcc -Og -fno-stack-protector -o $@ prog1.c

clean:
	rm -f mystery1 prog1 mystery2
