all: mystery3 prog3

mystery3: mystery3.c
	gcc -Og -fno-stack-protector -o $@ mystery3.c

prog3: prog3.c
	gcc -Og -fno-stack-protector -o $@ prog3.c

clean:
	rm -f mystery3 prog3
	rm -f tests/*_actual* tests/*_diff*
	rm -rf __pycache__/
