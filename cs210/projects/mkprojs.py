import os

# projects = ["storeproj","kvproj","kvcache", "bomblab"]
projects = ["allocproj", "kvproj"]

for proj in projects:
  print("checking project " + proj)
  if os.path.exists(proj):
    if proj == "bomblab":
      cmd = "cd " + proj + "; python3 mkallbombs.py sample_roster.csv"
    else:
      cmd = "cd " + proj + "; make -k -f Makefile.dist clean; make -f Makefile.dist"
    print(cmd)
    os.system(cmd)