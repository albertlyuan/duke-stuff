HW = kvcache

AG = $(HW)_autograder.zip 
AGDIR = $(PWD)
FILES = Makefile kv_cache_test.c kv_cache.h .kv_cache_util.o settings.json tests
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
