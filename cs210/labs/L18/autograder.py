import os, signal, shlex, subprocess, time, json

class TextColors:
    """
    Colors for use in print.
    """
    GREEN = "\033[0;32m"
    RED = "\033[0;31m"
    END = "\033[0m"

prog_arglist = ["fork1", "fork2", "fork3"]
prog_max_points = {"fork1": 20, "fork2": 30, "fork3": 50, "sig2":10}
total_score = 0
points = {}
expected = {}
prog_result = {}
fork_good = {}
wait_good = {}
exec_good = {}
result_good = {}

expected["fork3"] = ["/bin/ls","/bin"]

if not os.path.exists("output"):
  os.makedirs("output")
## check the fork programs
for prog in prog_arglist:
  output = "Testing {}...".format(prog)
  out = "./output/" + prog +".stdout"
  outfd = open(out,'w+')
  outerr = "./output/" + prog +".stderr"
  errfd = open(outerr,'w+')
  cmd = "timeout 20s ./" + prog
  args = shlex.split(cmd)
  proc = subprocess.Popen(args, stdout=outfd, stderr=errfd, shell=False) 
  proc.wait()
  outfd.seek(0)
  out_content = outfd.read().splitlines()
  tr_out = "./output/" + prog +".tr"
  tr_outfd = open(tr_out,'r')
  tr_content = tr_outfd.read().splitlines()
  result = []
  results = {}

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
    for line in tr_content:
      if line.find("my_exec") != -1:
        exec_good[prog] = True
        results[0] = line.split(" ")[7]
        results[1] = line.split(" ")[8]
    if (expected[prog][0] == results[0] and expected[prog][1] == results[1]):
      result_good[prog] = True
    else:
      result_good[prog] = False


  points[prog] = 0
  if fork_good[prog]:
    print("fork OK")
    points[prog] += 20
  if prog != "fork1" and wait_good[prog]:
    print("wait OK")
    points[prog] += 10
  if prog == "fork3" and exec_good[prog]:
    print("exec OK")
    points[prog] += 10
  if prog == "fork3" and result_good[prog]:
    print("result OK")
    points[prog] += 10
  
  if points[prog] == prog_max_points[prog]:
    result = TextColors.GREEN + "Pass"+ TextColors.END
  else:
    result = TextColors.RED + "Fail" + TextColors.END
 
  total_score += points[prog]
  print("%-20s %45s (%.1f/%.1f)\n" %( output, result, points[prog], prog_max_points[prog]))

  outfd.close()
  errfd.close()

if os.path.isfile('sig2'):
  sig2_out = ["Can you stop me?\n",
  "Hmm trying to stop this program are you?\n",
  "You are persistent!\n",
  "OK you win.\n"
  ]
  prog = "sig2"
  output = "Testing {}...".format(prog)
  out = "./output/" + prog +".stdout"
  outfd = open(out,'w+')
  #outerr = "./output/" + prog +".stderr"
  #errfd = open(outerr,'w+')
  cmd = "./" + prog
  args = shlex.split(cmd)
  proc = subprocess.Popen(args, stdout=outfd, shell=False)
  pid = proc.pid
  time.sleep(2) 
  proc.send_signal(signal.SIGINT)
  time.sleep(1)
  proc.send_signal(signal.SIGINT)
  time.sleep(1)
  proc.send_signal(signal.SIGINT)
  time.sleep(2)
  ## check if alive
  poll = proc.poll()
  if poll == None:
    result = TextColors.RED + "Fail"+ TextColors.END
    points[prog] = 0
  #  print(TextColors.RED + "Failed...issuing kill" + TextColors.END)
    os.kill(pid,signal.SIGKILL)
  else:
    outfd.seek(0)
    prog_out = outfd.readlines()
    if prog_out != sig2_out:
      result = TextColors.RED + "Fail"+ TextColors.END
      points[prog] = 0
    else:
      result = TextColors.GREEN + "Pass"+ TextColors.END
      points[prog] = 10
  outfd.close()

  total_score += points[prog]
  print("%-20s %45s (%.1f/%.1f)\n" %( output, result, points[prog], 10))

  # create json structure
  prog_arglist.append("sig2")


test_output = []
for prog in prog_arglist:
  test_output.append({"score": points[prog], "max_score": prog_max_points[prog], "name": prog})

output = {
  "score": total_score,
  "stdout_visibility": "hidden",
  "tests": test_output
}

resfd =  open("results.json","w+")
resfd.write(json.dumps(output, indent=4))
#json.dump(output,resfd)
resfd.close()