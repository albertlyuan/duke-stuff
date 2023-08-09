all: target1 target2 hex2raw

target1: target1.c
	gcc -DBUFFER_SIZE=4 -fno-stack-protector -Og -o $@ target1.c -static

target2: target2.c
	gcc -DBUFFER_SIZE=16 -fno-stack-protector -Og -o $@ target2.c -static

hex2raw: hex2raw.c
	gcc -o $@ hex2raw.c

clean:
	rm -f target1 target2 hex2raw

cleantargets:
	rm -rf targets student_targets
