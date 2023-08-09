import os

blocks = ["16", "32", "64"]
sets = ["1024", "2048"]
tracefiles = ["col_trace.txt", "row_trace.txt"]

count = 0
exe = "./casim"
for set in sets:
  for block in blocks:
    for trace in tracefiles:
      out = "./tests/casim_expected_"+str(count)+".txt"
      cmd = exe + " " + block + " " + set + " " + trace + " > " + out
      print(cmd)
      os.system(cmd)
      count = count + 1
