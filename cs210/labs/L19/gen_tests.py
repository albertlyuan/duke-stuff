import os

args = ["", "-n 5000", "-n 10000", "-n 100000", "-n 50000 -v"]

tracefiles = ["col_trace.txt", "row_trace.txt"]

count = 0
exe = "./vasim"
for arg in args:
  for trace in tracefiles:
    out = "./tests/vasim_expected_"+str(count)+".txt"
    cmd = exe + " " + arg + " -f " + trace + " > " + out
    print(cmd)
    os.system(cmd)
    count = count + 1
