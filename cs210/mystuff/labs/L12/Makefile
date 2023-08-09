all:  prog5

prog5: prog5.c
	gcc -fno-stack-protector -Og -o $@ prog5.c

clean:
	rm -f prog5
	rm -f tests/*_actual* tests/*_diff*
