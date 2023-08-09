import os

handout_dir = os.getcwd() + "/../handouts"
end = 26 # one past last lab

for lab in range(1,end,1):
  if (lab == 13):
    continue
  if (lab < 10):
    dir = "L0" + str(lab) 
  else:
    dir = "L" + str(lab)
  src_dir = dir + "/" + dir + "_handout"
  dst_dir = handout_dir + "/" + dir
  if not os.path.exists(dst_dir):
      print("mkdir")
      os.makedirs(dst_dir)
  if os.path.exists(dir):
    cmd = "cp -r " + src_dir + "/* " + dst_dir
    print(cmd)
    os.system(cmd)
