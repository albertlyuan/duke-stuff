import os

count = 0
exe = "./node_alloc"
for arg in range(1,6):
  out = "./tests/node_alloc_expected_"+str(count)+".txt"
  cmd = exe + " -t " + str(arg) + " > " + out
  print(cmd)
  os.system(cmd)
  count = count + 1