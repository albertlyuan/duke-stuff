all: debug1 debug2 debug2sol

debug1: debug1.c
	gcc -g -o $@ $<

debug2: debug2.c
	unifdef -DSTUDENT -o debug2_temp.c $<; test "$$?" -eq 1
	gcc -g -o $@ debug2_temp.c

debug2sol: debug2.c
	gcc -g -o $@ $<

clean:
	rm -f debug1 debug2 debug2sol
