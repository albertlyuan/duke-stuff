HW = `basename $(shell dirname $(PWD))`

AG = $(HW)_autograder.zip 
AGDIR = $(PWD)
SRCDIR = ../../src

.PHONY: $(AG)

$(AG):
	(cd ../; zip -r $(AGDIR)/$(AG) cachechecker cachechecker.c sumarraycols.c sumarrayrows.c casim.c row_trace.txt col_trace.txt settings.json  Makefile tests; cd $(AGDIR))
	(cd $(SRCDIR); zip $(AGDIR)/$(AG) setup.sh run_autograder autograder.py common.py; cd $(AGDIR))
	
clean:
	rm $(AG)
