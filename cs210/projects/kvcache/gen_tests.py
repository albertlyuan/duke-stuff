import os

arglist = ["-b 4 -t 1", "-b 13 -t 1",
            "-b 4 -t 2", "-b 13 -t 2",
            "-b 4 -t 3", "-b 13 -t 3",
            "-b 4 -t 4", "-b 13 -t 4",
            "-b 4 -t 5", "-b 13 -t 5",
            "-n 500 -b 64 -t 6", "-n 1000 -b 113 -t 6",
            "-n 500 -b 64 -t 7", "-n 1000 -b 113 -t 7",
            "-n 1000 -b 64 -t 8", "-n 5000 -b 251 -t 8"
]

count = 0
exe = "./kv_cache_test"
for arg in arglist:
  out = "./tests/kv_cache_test_expected_"+str(count)+".txt"
  cmd = exe + " " + arg + " > " + out
  print(cmd)
  os.system(cmd)
  count = count + 1
