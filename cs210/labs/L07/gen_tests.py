import os

arglist = [ "stringcat", "sum_linked_list", "linked_list_equality" ]

count = 0
exe = "./pointers2"
for arg in arglist:
  out = "./tests/pointers2_expected_"+str(count)+".txt"
  cmd = exe + " " + arg + " > " + out
  print(cmd)
  os.system(cmd)
  count = count + 1
