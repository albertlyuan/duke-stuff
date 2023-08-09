test_files = empty.bin push.bin neg.bin add.bin sub.bin xor.bin mul.bin pop.bin prog.bin

all: indirect interpret asm $(test_files)

asm: asm.c isa.h
	gcc -g -o $@ $<

indirect: indirect.c
	gcc -g -o $@ $<

interpret: interpret.c isa.h
	gcc -g -o $@ $<

$(test_files): %.bin : %.s
	./asm $< $@

clean:
	rm -f indirect interpret asm $(test_files)
	rm -f tests/*_diff* tests/*_actual*
