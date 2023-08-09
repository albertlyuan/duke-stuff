all: args1 args2 recurse file1 file2 strings1

args1: args1.c
	gcc -o $@ $<

args2: args2.c
	gcc -o $@ $<

recurse: recurse.c
	gcc -o $@ $<

file1: file1.c
	gcc -o $@ $<

file2: file2.c
	gcc -o $@ $<

strings1: strings1.c
	gcc -o $@ $<

clean:
	rm -f args1 args2 recurse file1 file2 strings1 output1.txt tests/*_actual* tests/*_diff*
