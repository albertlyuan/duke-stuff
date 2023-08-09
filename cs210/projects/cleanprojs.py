import os

# projects = ["storeproj","kvproj","kvcache","bomblab"]
projects = ["allocproj", "kvproj"]

for proj in projects:
  print("checking project " + proj)
  if os.path.exists(proj):
    if proj == "bomblab":
      cmd = "cd " + proj + "; make -k cleanallfiles"
    else:
      cmd = "cd " + proj + "; make -k clean; make -k -f Makefile.dist clean"
    print(cmd)
    os.system(cmd)