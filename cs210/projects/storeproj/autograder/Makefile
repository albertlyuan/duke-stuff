HW = storeproj

AG = $(HW)_autograder.zip 
AGDIR = $(PWD)
FILES = Makefile aisle_test.c store_test.c aisle_manager.h store_manager.h store_util.c store_util.h test_util.h .aisle_util.o .store_util.o settings.json
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
