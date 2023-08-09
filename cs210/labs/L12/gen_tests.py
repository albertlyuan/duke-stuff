import os

#not off by one counting of function names and expected_x file name
arglist = ["1", "2", "3", "4"]

count = 0
exe = "./mystery5"
for arg in arglist:
  out = "./tests/mystery5_expected_"+str(count)+".txt"
  cmd = exe + " " + arg + " < solution_f" + arg + ".txt > " + out
  print(cmd)
  os.system(cmd)
  count = count + 1
