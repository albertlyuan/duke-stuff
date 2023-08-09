import os, signal, shlex, subprocess, time, json

class TextColors:
    """
    Colors for use in print.
    """
    GREEN = "\033[0;32m"
    RED = "\033[0;31m"
    END = "\033[0m"

prog_arglist = ["fork1", "fork2", "fork3"]
prog_max_points = {"fork1": 1, "fork2": 3, "fork3": 6}
total_score = 0
points = {}
expected = {}
prog_result = {}
fork_good = {}
wait_good = {}
exec_good = {}
result_good = {}

expected["fork3"] = ["210"]

if not os.path.exists("output"):
  os.makedirs("output")
## check the fork programs
for prog in prog_arglist:
 # output = "Testing {}...".format(prog)
  result = []
  results = {}
  
  out = "./output/" + prog +".stdout"
  outfd = open(out,'w+')
  outerr = "./output/" + prog +".stderr"
  errfd = open(outerr,'w+')
  cmd = "timeout 20s ./" + prog
  args = shlex.split(cmd)
  proc = subprocess.Popen(args, stdout=outfd, stderr=errfd, shell=False) 
  proc.wait()
  #check return code
  if proc.returncode != 0:
    result_good[prog] = False
    wait_good[prog] = False
    fork_good[prog] = False
    continue
  outfd.seek(0)
  out_content = outfd.read().splitlines()
  tr_out = "./output/" + prog +".tr"
  tr_outfd = open(tr_out,'r')
  tr_content = tr_outfd.read().splitlines()


  for line in out_content:
    if line.find("pid") != -1:
      u_cid = line.split(" ")[-1] # get user printed child pid

  # now get information from the trace output file
  for line in tr_content:
    if line.find("my_fork") != -1:
      tr_pid = line.split(" ")[2]
      tr_cid = line.split(" ")[-1]
      fork_good[prog] = (tr_cid  == u_cid)

  if (prog == "fork2" or prog == "fork3"):
    for line in tr_content:
      if line.find("my_waitpid:") != -1:
        w_pid = line.split(" ")[2]
        w_cid = line.split(" ")[-1]
        wait_good[prog] = (w_pid == w_pid)

  if (prog == "fork3"):
    for line in out_content:
      if line.find("After Wait: Hello from parent") != -1:
        results[0] = line.split(" ")[11].split(",")[0]
    if (expected[prog][0] == results[0]):
      result_good[prog] = True
    else:
      result_good[prog] = False

for prog in prog_arglist:
  output = "Testing {}...".format(prog)
  points[prog] = 0
  if fork_good[prog]:
    print("fork OK")
    points[prog] += 1
  if prog != "fork1" and wait_good[prog]:
    print("wait OK")
    points[prog] += 2
  if prog == "fork3" and result_good[prog]:
    print("result OK")
    points[prog] += 3
  
  if points[prog] == prog_max_points[prog]:
    result = TextColors.GREEN + "Pass"+ TextColors.END
  else:
    result = TextColors.RED + "Fail" + TextColors.END
 
  total_score += points[prog]
  print("%-20s %45s (%d/%d)\n" %( output, result, points[prog], prog_max_points[prog]))

  outfd.close()
  errfd.close()

