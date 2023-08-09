HW = allocproj

AG = $(HW)_autograder.zip 
AGDIR = $(PWD)
INFILES = t1.txt t2.txt t3.txt t4.txt t5.txt t6.txt
FILES = Makefile alloc_test.c alloc.h .alloc_util.o settings.json $(INFILES)
AGFILE_DIR = ../../autograder_files
AGFILES = setup.sh run_autograder hwtest.py

.PHONY: $(AG)

$(AG):
	(cd ../; zip -r $(AGDIR)/$(AG) $(FILES); cd $(AGDIR))
	cp -r ../tests tests
	cp settings.json tests
	zip -r $(AGDIR)/$(AG) tests
	(cd $(AGFILE_DIR); zip $(AGDIR)/$(AG) $(AGFILES))	
	
clean:
	rm -f $(AG)
	rm -rf tests
