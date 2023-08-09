import os

arglist = ["1", "2", "3"]

count = 0
exe = "./mystery1"
for arg in arglist:
  out = "./tests/mystery1_expected_"+str(count)+".txt"
  cmd = exe + " " + arg + " < solution_f" + arg + ".txt > " + out
  print(cmd)
  os.system(cmd)
  count = count + 1
