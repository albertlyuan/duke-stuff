all: mystery2 prog2

mystery2: mystery2.c
	gcc -Og -fno-stack-protector -o $@ mystery2.c

prog2: prog2.c
	gcc -Og -fno-stack-protector -o $@ prog2.c

clean:
	rm -f mystery2 prog2
	rm -f tests/*_actual* tests/*_diff*
