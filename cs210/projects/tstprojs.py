import os

# projects = ["storeproj", "kvproj", "bomblab", "kvcache", "allocproj"]
projects = ["allocproj"]

for proj in projects:
  print("checking project " + proj)
  if os.path.exists(proj):
    if proj == "bomblab":
      tststr = "./bomb < solution.txt"
      cmd = "cd " + proj + "/bombs/bomb0; " + tststr
    else:
      tststr = "python3 ../autograder_files/hwtest.py ALL"
      cmd = "cd " + proj + "; make -k clean; make; " + tststr
    print(cmd)
    os.system(cmd)
