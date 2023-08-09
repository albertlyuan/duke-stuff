all: buffer

buffer: buffer.c
	gcc -o $@ buffer.c CuTest.c

clean:
	rm -f buffer 
	rm -f tests/*_diff* tests/*_actual*
