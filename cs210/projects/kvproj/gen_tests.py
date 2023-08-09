import os

arglist = ["-n 1 -f lookup", "-n 2 -f lookup",  "-n 7 -f lookup", "-f lookup", "-n 1234 -f lookup",
          "-n 1 -f delete", "-n 2 -f delete",  "-n 7 -f delete", "-f delete", "-n 1234 -f delete",
          "-n 1 -f deserialize", "-n 2 -f deserialize",  "-n 7 -f deserialize", "-f deserialize", "-n 1023 -f deserialize",
          "-n 1 ", "-n 2 ",  "-n 7", "", "-n 2049 ",
]

count = 0
exe = "./kv_test"
for arg in arglist:
  out = "./tests/kv_test_expected_"+str(count)+".txt"
  cmd = exe + " " + arg + " > " + out
  print(cmd)
  os.system(cmd)
  count = count + 1
