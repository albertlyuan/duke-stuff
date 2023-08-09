import os

#note off by one counting of function names and expected_x file name
aisle_list = ["get_section", "get_id", "get_spaces", "set_section", "set_id", "set_spaces", "toggle_space", "num_items", "add_items", "remove_items", "rotate_items_left", "rotate_items_right"]

count = 0
exe = "./aisle_test"
for arg in aisle_list:
  out = "./tests/aisle_test_expected_"+str(count)+".txt"
  cmd = exe + " -f " + arg  + " > " + out
  print(cmd)
  os.system(cmd)
  count = count + 1

store_list = ["refill_from_stockroom", "fulfill_order", "empty_section_with_id", "section_with_most_items"]

count = 0
exe = "./store_test"
for arg in store_list:
  out = "./tests/store_test_expected_"+str(count)+".txt"
  cmd = exe + " -f " + arg  + " > " + out
  print(cmd)
  os.system(cmd)
  count = count + 1
