import os

arglist = ["add_with_pointers","address_in_array","bytes_between_4s","point_to_smallest","point_to_smallest_ptr","power_replace","power_sum_dont_replace","set_string_with_q","remove_2nd_q_strings","words","breakint","doublecorrectly"]

count = 0
exe = "./pointers"
for arg in arglist:
  out = "./tests/pointers_expected_"+str(count)+".txt"
  cmd = exe + " " + arg + " > " + out
  print(cmd)
  os.system(cmd)
  count = count + 1