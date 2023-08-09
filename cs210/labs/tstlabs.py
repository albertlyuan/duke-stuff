import os

end = 26 # one past last lab

has_local_test = [18,19,23,24,25]
thread_labs = [23,24,25]
fork_labs = [18,19]

for lab in range(1,end,1):
  util_cmd = " "
  if (lab == 6 or lab == 13):
    continue
  if (lab < 10):
    dir = "L0" + str(lab) 
  else:
    dir = "L" + str(lab)
  print("checking lab " + dir)
  if os.path.exists(dir):
    if (lab == 15):
      tststr = "bash runem.sh"
    elif lab in has_local_test:
      if lab in thread_labs:
        util_cmd = "gcc -O3 -fvisibility=hidden -s -c thread_util.c -o .thread_util.o; "
      if lab in fork_labs:
          util_cmd = "gcc -O3 -fvisibility=hidden -s -c fork_util.c -o .fork_util.o; "  
      tststr = "python3 ./test_kit.py ALL"
    else:
      tststr = "python3 ../src/test_kit.py ALL"
    cmd = "cd " + dir + "; make clean; " + util_cmd + " make; " + tststr
    print(cmd)
    os.system(cmd)
