import sys
import os
import random

if len(sys.argv) < 2:
	print("Error: usage cktargets.pl roster.csv\n")
	exit()

text_seg_base = "555555"

target_dir = "targets"
handout_dir = "student_targets"

#echo roster file
print(sys.argv[1])


#handout contains the student files
if not os.path.exists(handout_dir):
    print("No student handout directory")
    exit

#targets has one directory per student w/ targets and solutions automatically generated
if not os.path.exists(target_dir):
    print("No target directory")
    exit

with open(sys.argv[1]) as f:
    for index, fullline in enumerate(f):
        line = fullline.split(',')
        lastname = line[0]
        firstname = line[1]
        name = firstname + " " + lastname
        netid = line[2].split('@')[0]
        email = line[3]
        print("Check targets for " + name + ", netid: " + netid + ", email " + email)
        sys.stdout.flush()

        # get directories
        tdir = target_dir + '/' + netid    
        hdir = handout_dir + '/' + netid
       
        #check target1 in target dir
#        ckcmd =  tdir + '/target1.' + netid + ' < ' + tdir + '/exploit1.txt'
#        os.system(ckcmd)

        #check target1 in student handout dir
        ckcmd =  hdir + '/target1-' + netid + ' < ' + tdir + '/exploit1.txt'
        os.system(ckcmd)

        #check target2 in target dir
#        ckcmd =  tdir + '/target2-' + netid + ' < ' + tdir + '/exploit2.raw'
#        os.system(ckcmd)

        #check target2 in student handout dir
        ckcmd =  hdir + '/target2-' + netid + ' < ' + tdir + '/exploit2.raw'
        os.system(ckcmd)





        


