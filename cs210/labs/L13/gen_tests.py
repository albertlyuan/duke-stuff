import os

#not off by one counting of function names and expected_x file name
interpret_arglist = ["empty", "push", "neg", "add", "sub", "mul", "xor", "pop", "prog"]

count = 0
exe = "./interpret"
for arg in interpret_arglist:
  out = "./tests/interpret_expected_"+str(count)+".txt"
  cmd = exe + " " + arg +  ".bin > " + out
  print(cmd)
  os.system(cmd)
  count = count + 1
