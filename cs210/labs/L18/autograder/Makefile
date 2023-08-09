HW = `basename $(shell dirname $(PWD))`

AG = $(HW)_autograder.zip 
AGDIR = $(PWD)
SRCDIR = ../../src


.PHONY: $(AG)

$(AG):
	(cd ../; zip -r $(AGDIR)/$(AG) .fork_util.o my_fork.h autograder.py Makefile; cd $(AGDIR))
	(cd $(SRCDIR); zip $(AGDIR)/$(AG) setup.sh run_autograder; cd $(AGDIR))
	
clean:
	rm $(AG)
