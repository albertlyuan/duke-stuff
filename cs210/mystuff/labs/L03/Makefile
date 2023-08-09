all: btest ishow

btest: btest.c bits.c
	./dlc bits.c
	gcc -o $@ btest.c bits.c

ishow: ishow.c
	gcc -o $@ $<
clean:
	rm -f btest ishow
	rm -f tests/*_diff* tests/*_actual*
