HW = `basename $(shell dirname $(PWD))`

AG = $(HW)_autograder.zip 
AGDIR = $(PWD)
SRCDIR = ../../src
FILES = settings.json       \
				Makefile            \
				tests               \
				ring_input_0.txt    \
				ring_input_1.txt    \
				ring_input_2.txt    \
				ring_input_3.txt    \
				strings_input_0.txt \
				strings_input_1.txt \
				strings_input_2.txt \
				ring.c              \
				recurse.c           \

$(AG):
	(cd ../; zip -r $(AGDIR)/$(AG) $(FILES); cd $(AGDIR))
	(cd $(SRCDIR); zip $(AGDIR)/$(AG) run_autograder setup.sh autograder.py common.py; cd $(AGDIR))
	
clean:
	rm $(AG)
