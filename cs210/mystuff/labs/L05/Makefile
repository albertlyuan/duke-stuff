all: pointers

pointers: pointers.c
	gcc -o $@ pointers.c CuTest.c

clean:
	rm -f pointers 
	rm -f tests/*_diff* tests/*_actual*
