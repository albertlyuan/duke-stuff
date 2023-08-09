HW = `basename $(shell dirname $(PWD))`

AG = $(HW)_autograder.zip 
AGDIR = $(PWD)
SRCDIR = ../../src


.PHONY: $(AG)

$(AG):
	(cd ../; zip -r $(AGDIR)/$(AG) array.c mythreads.h .thread_util.o autograder.py Makefile; cd $(AGDIR))
	(cd $(SRCDIR); zip $(AGDIR)/$(AG) setup.sh run_autograder; cd $(AGDIR))
	
clean:
	rm $(AG)
