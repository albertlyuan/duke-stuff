all: mystery5 prog5

mystery5: mystery5.c
	gcc -fno-stack-protector -Og -o $@ mystery5.c

prog5: prog5.c
	gcc -fno-stack-protector -Og -o $@ prog5.c

clean:
	rm -f mystery5 prog5
	rm -f tests/*_actual* tests/*_diff*
