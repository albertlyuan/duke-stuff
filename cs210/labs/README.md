# Labs

**Per Lecture Labs**

These small coding problems are designed for students to develop skills by applying knowledge learned in readings and lectures.

Each lab is numbered according to the corresponding lecture.  
Autograding/testing is performed both locally and on gradescope, building on the test framework that Tyler Bletsch created for 250.  It may be overkill for these labs, but it gets the job done. 

Within each lab directory there is a Makefile.dist that can be used to create the student handout and the autograder for the lab.

L*_handout is the directory for the student handout. It contains a Makefile, a settings.json a tests directory. See below for test_kit use.

autograder is the directory with a Makefile to create the gradescope zip file for the autograder. This pulls files from each lab directory and the src directory.

**test_kit / autograding:**
Most labs contain a settings.json that has the information regarding the tests to be run (i.e., program name, args, type of output check). The structure allows for a test suite with one or more programs and one or more tests per program. This is good if you want to have different command line arguments to test programs and give points per test.  Expected outputs are contained in tests/<TEST>_expected_<#>.txt  where TEST is a typically a program name and # is the test number.

Assuming the following structure
../labs/src
../labs/L01
../labs/L01/tests/*expected_*.txt
../labs/L02
../labs/L02/tests/*expected_*.txt
...

In lab L01 you would make hello and then at the command prompt run: python3 ../src/test_kit.py ALL to run all tests.  In this first lab there is only hello to run, so you can also use hello in place of ALL. This will generate output on stdout that indicates if the test passed or not. It also generates files in tests called .../L01/tests/hello_actual_0.txt and .../L01/tests/hello_diff_0.txt which contain the output of hello and the diff with the expected output.

Some labs have a gen_test.py to generate expected output files.

Labs related to fork/exec/sig and threads have their own special test_kit.py files in the L* directory since 
simple diff type checking used doesn't work. Those labs rely on a library (object file) used to interpose system calls 
that performs some limited checks and logs information to a trace file in the output directory.  The main thing is to 
detect obvious short cuts, like never actually making the required system calls and just recreating the output or 
using only one lock.

All required testing files are copied into each handout directory for students. (Note: Tyler has a new version of the autograding, but I have not updated since I did a lot of this before his updates were ready. We should probably update).

**autograder**
Like test_kit.py the file .../src/autograder.py will run test suites and generate ouput but also create a json file that is compatible with gradescope.  test_kit.py and autograder.py both import the file .../src/common.py (again these files are copied to the handout directories for students so all is within their repo). Control of testing is specified in the settings.json file within each lab.  Any new testing types need to be added to settings.json and have appropriate changes in common.py.

The subdirectory ../autograder contains a Makefile and in some cases a run_autograder bash file if the base version in ../src can't be used (i.e., for any reverse engineering labs).


**build the lab**
make -f Makefile.dist 
this will build the handout directory (which is what should be distributed to students) and build the autograder zip file.
For some labs I use a single source file for both solution and for student handouts but with solutions stripped by placing them
within #ifdefs.  In these cases, Makefile.dist uses the unifdef utility and -DSTUDENT defined to generate the source files for students.

Each lab contains a README.md with directions and information for the student. (They are a bit rough yet)

After running Makefile.dist, the handout and and the autograder are ready.  If you modify the lab or are creating a new one, be sure to test things appropriately before releasing to students.

**helpful scripts**
cleanlabs.py (make clean in all labs)

mklabs.py (make -f Makefile.dist in each lab to prep handouts and autograder)

tstlabs.py (runs a make and then the testkit in each lab skips labs without gradescop autograders)

copy_handouts.py copies the student handout files to a directory ../handouts relative to the directory where you have this repository.  After a copy you can create or push any updates to the appropriate project in gitlab.


**container information**
Ubuntu 18.04 with the following installed
gcc, gdb, valgrind, zip, unifdef

OIT has containers configured for this, but you can also create one for local use on personal x86_64 machines.
The Dockerfile in the lab repo has the items needed (based off Chase's 310 container)

first get docker on your local machine, then run 

docker build -t dev210 .

which will make a container image called dev210

then use

docker run --name cs210 --mount type=bind,source=/Users/alvy/Containers/210,destination=/cs210 -it dev210 bash

to start the container and fire up an interactive bash shell (Dockerfile should be in teh directory specified by the source argument)

for the attack lab you need to start the container in a less secure mode.

docker run --name cs210-unsafe --security-opt seccomp=unconfined --mount type=bind,source=/Users/alvy/Containers/210,destination=/cs210 -it dev210 bash

Then within the docker bash shell run setarch \`uname -m\` -R /bin/bash to start a new bash shell, this will disable ASLR for the shell and all its child processes.

