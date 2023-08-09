import os
arglist = ["-t 1", "-t 2", "-t 3", "-t 4", "-t 4 -l 1024"]
count = 0
exe = "./buffer"
for arg in arglist:
  out = "./tests/buffer_expected_"+str(count)+".txt"
  cmd = exe + " " + str(arg) + " > " + out
  print(cmd)
  os.system(cmd)
  count = count + 1