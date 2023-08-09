import sys
import shutil,os

if len(sys.argv) < 2:
	print("Error: usage mkallbombs.pl roster.csv\n")
	exit()

handout_dir = "student_bombs"

print(sys.argv[1])
if not os.path.exists("./bombs/"):
    os.makedirs("./bombs/")
if not os.path.exists(handout_dir):
    os.makedirs(handout_dir)

with open(sys.argv[1]) as f:
    for index, fullline in enumerate(f):
        line = fullline.split(',')
        lastname = line[0]
        firstname = line[1]
        name = firstname + " " + lastname
        netid = line[2].split('@')[0]
        email = line[3]
        print("Make bomb for " + name + ", netid: " + netid + ", email " + email)
        # invoke the CSAPP perl script
        cmd = './makebomb.pl -i ' + str(index) + ' -s ./src -b ./bombs -l bomblab -u ' + email + ' -v ' + line[2]
        os.system(cmd)
        bombdir = "./bombs/bomb" + str(index)
        newbombdir = "./bombs/bomb-" + netid
        os.rename(bombdir,newbombdir)
        bomb = newbombdir + "/bomb-quiet"
        student_bomb = newbombdir + "/bomb-" + netid
        student_bomb_dir = handout_dir + "/" + netid
        if not os.path.exists(student_bomb_dir):
            os.makedirs(student_bomb_dir)
        print(bomb)
        print(student_bomb)
        shutil.copy(bomb,student_bomb)
        print("copied local")
        shutil.copy(student_bomb,student_bomb_dir)
        print("copied to hand out")
        htaccess_str = "AuthType shibboleth\nShibRequireSession On\nRequire user " + netid + "@duke.edu\n"
        outname = student_bomb_dir + "/.htaccess"
        outfd = open(outname,"w+")
        outfd.write(htaccess_str)
        outfd.close()


        


