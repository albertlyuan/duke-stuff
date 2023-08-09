HW = `basename $(shell dirname $(PWD))`

AG = $(HW)_autograder.zip 
AGDIR = $(PWD)
SRCDIR = ../../src
FILES = convert.c           \
				convert_student.c   \
				convert_input_0.txt \
				convert_input_1.txt \
				convert_input_2.txt \
				convert_input_3.txt \
				convert_input_4.txt \
				convert_input_5.txt \
				convert_input_6.txt \
				convert_input_7.txt \
				convert_input_8.txt \
				convert_input_9.txt \
				settings.json       \
				Makefile            \
				tests

.PHONY: $(AG)

$(AG):
	(cd ../; zip -r $(AGDIR)/$(AG) $(FILES); cd $(AGDIR))
	(cd $(SRCDIR); zip $(AGDIR)/$(AG) run_autograder setup.sh autograder.py common.py; cd $(AGDIR))
	
clean:
	rm $(AG)
