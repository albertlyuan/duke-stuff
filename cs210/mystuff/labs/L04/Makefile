all: image stringbug

image: image.c
	gcc -o $@ $<

stringbug: stringbug.c
	gcc -g -fno-stack-protector -o $@ $<

clean:
	rm -f image newimg.ppm stringbug
	rm -f tests/*_diff* tests/*_actual*
