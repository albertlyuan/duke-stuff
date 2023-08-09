import os
import sys

args_count = len(sys.argv) - 1
if args_count == 0:
  end = 25 # one past last lab
  for lab in range(1,end,1):
    if (lab < 10):
      dir = "L0" + str(lab) 
    else:
      dir = "L" + str(lab)
    if os.path.exists(dir):
      cmd = "cd " + dir + "; make -f Makefile.dist clean; make -f Makefile.dist"
      print(cmd)
      os.system(cmd)
  print("--------------------")
  print("")
  print("End of making labs")
  print("")
  print("--------------------")
elif args_count == 1:
  lab = int(sys.argv[1])
  if (lab < 10):
      dir = "L0" + str(lab) 
  else:
    dir = "L" + str(lab)
  if os.path.exists(dir):
    cmd = "cd " + dir + "; make -f Makefile.dist clean; make -f Makefile.dist"
    print(cmd)
    os.system(cmd)
  print("--------------------")
  print("")
  print("End of making lab " + str(lab))
  print("")
  print("--------------------")
else:
  print("-----------------------------------------------------------")
  print("")
  print("Run \"python3 mklabs.py\" to make all labs")
  print("")
  print("Or \"python3 mklabs.py <lab number>\" to select lab to make")
  print("")
  print("-----------------------------------------------------------")

