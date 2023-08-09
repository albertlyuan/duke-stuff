HW = bombproj

AG = $(HW)_autograder.zip 
AGDIR = $(PWD)
AG_FILE_DIR = ../../../autograder_files
FILES = autograder.py run_autograder

.PHONY: $(AG)

$(AG):
	zip -r $(AGDIR)/$(AG) $(FILES);
	(cd $(AG_FILE_DIR); zip $(AGDIR)/$(AG) setup.sh; cd $(AGDIR))

	tar cfvz student_bombs.tgz ../../student_bombs
	zip $(AGDIR)/$(AG) student_bombs.tgz	
	
clean:
	rm $(AG)
