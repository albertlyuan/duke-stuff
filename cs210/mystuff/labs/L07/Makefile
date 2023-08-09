all: pointers2 image2

pointers2: pointers2.c
	gcc -o $@ pointers2.c CuTest.c

image2: image2.c
	gcc -o $@ $<

clean:
	rm -f pointers2 image2
	rm -f tests/*_diff* tests/*_actual*
	rm -f *.ppm
	cp tests/sample.ppm sample.ppm
