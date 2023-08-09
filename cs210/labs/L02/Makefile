all: recurse strings ring

recurse: recurse.c recurse_student.c
	gcc -g -o $@ $?

strings: strings.c
	gcc -g -o $@ $<

ring: ring.c ring_student.c
	gcc -g -o $@ $?

clean:
	rm -f recurse strings ring tests/*_actual* tests/*_diff*
	rm -rf __pycache__/
