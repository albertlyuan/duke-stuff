import os

end = 26 # one past last lab

has_local_test = [18,19,23,24,25]

for lab in range(1,end,1):
  if (lab == 6 or lab == 13):
    continue
  if (lab < 10):
    dir = "L0" + str(lab) 
  else:
    dir = "L" + str(lab)
  print("checking lab " + dir)
  hdir = dir + "_handout"
  if os.path.exists(dir):
    if (lab == 15):
      tststr = "bash runem.sh"
    else:
#      tststr = "python3 ./" + hdir + "/test_kit.py ALL"
      tststr = "python3 test_kit.py ALL"

    cmd = "cd " + dir + "; make; cd " + hdir + "; make; " + tststr
    print(cmd)
    os.system(cmd)
