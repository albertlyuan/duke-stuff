import os

arglist = ["-f t1.txt", "-f t2.txt", "-f t2.txt -s 2048", "-f t3.txt -s 2048", "-f t3.txt -s 1024", "-f t3.txt -s 1200", 
            "-f t4.txt -s 2048", "-f t4.txt -s 1200","-f t5.txt -s 2048", "-f t6.txt -s 2048"
]

count = 0
exe = "./alloc_test"
for arg in arglist:
  out = "./tests/alloc_test_expected_"+str(count)+".txt"
  cmd = exe + " " + arg + " > " + out
  print(cmd)
  os.system(cmd)
  count = count + 1
