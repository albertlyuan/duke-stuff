HW = `basename $(shell dirname $(PWD))`

AG = $(HW)_autograder.zip 
AGDIR = $(PWD)
SRCDIR = ../../src

$(AG):
	(cd ../; zip -r $(AGDIR)/$(AG) dlc btest.c settings.json Makefile tests; cd $(AGDIR))
	(cd $(SRCDIR); zip $(AGDIR)/$(AG) run_autograder setup.sh autograder.py common.py; cd $(AGDIR))
	
clean:
	rm $(AG)
