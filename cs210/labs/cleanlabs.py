import os
import sys

args_count = len(sys.argv) - 1
if (args_count == 0):
  end = 25 # one past last lab
  for lab in range(1,end,1):
    if (lab < 10):
      dir = "L0" + str(lab) 
    else:
      dir = "L" + str(lab)
    print("checking lab " + dir)
    if os.path.exists(dir):
        cmd = "cd " + dir + "; make clean; make -f Makefile.dist clean"
        print(cmd)
        os.system(cmd)

print("--------------------")
print("")
print("End of cleaning labs")
print("")
print("--------------------")