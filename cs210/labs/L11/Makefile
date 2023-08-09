all: mystery4 prog4

mystery4: mystery4.c
	gcc -Og -fno-stack-protector -o $@ mystery4.c

prog4: prog4.c
	gcc -Og -fno-stack-protector -o $@ prog4.c

clean:
	rm -f mystery4 prog4
	rm -f tests/*_actual* tests/*_diff*
