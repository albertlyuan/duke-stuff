# Projects
For the larger projects in Compsci 210
- Storelab (bits & bytes)
- Memlab (deserialize a KV store into a linked list)
- Bomblab (reverse engineer assmembly to enter specific inputs)
- Attacklab (buffer overflow/ stack smashing: 1) change a string on the stack, 2) call a function by overwriting return address on stack)
- Cachelab (KV cache, simple hash table with 4-deep chaining)
- Allocproj (simple segregated heap allocator with four bins for sizes 8,16,32,64)
- Threadlab (TBD, simple locks + concurrency, see OSTEP)

# To test things
- clone projects into a directory at the same level as labs (this is due to a shared test_kit for some things) 
should be .../projects and .../labs 
- cd projects
- run python3 mkprojs.py
- run python3 tstprojs.py
- This works for all projects except the attack project, since that needs an unsafe container.
- The output should display green PASS for all projects, except bomblab where you should see "Congratulations! You've defused the bomb!" This builds only a couple bombs and tests only one bomb.

- there is also a cleanprojs.py that can be used to clean up things accordingly (do not ever run that after you've deployed any projects to students as it will remove critical files).

#### Autograder scripts, Gradescope files, etc.
- Each lab can be built and tested individually within its respective directory.
- The gradescope files used are in the autograder_files directory of this repository.
- The autograder for many of the labs is based on Tyler's newest version https://gitlab.oit.duke.edu/tkb13/hwtest it uses a single python script for both local testing and the autograder.
- Currently the tests are identical for local testing and the gradescope autograder. If different tests are desired for the autograder, then you need to modify the Makefile add a tests directory to the autograder subdirectory and also remove the line in the project .gitgnore about the autograder tests.

# Container information
Ubuntu 18.04 with the following installed
gcc, gdb, valgrind, zip, unifdef, wget

OIT has containers configured for this, called cs210, but you can also create one for local use on personal x86_64 machines.
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
Then to test it run the following in order:
make
python3 mkalltargets.py sample_roster.csv
python3 cktargets.py sample_roster.csv
