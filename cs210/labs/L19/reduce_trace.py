import sys

if len(sys.argv) != 2:
  print ("Usage: reduce_trace.py filename")
  sys.exit()

filename = sys.argv[1]
tracefd = open(filename,'r')

for line in tracefd.readlines():
 # print (line)
  access = line.split(' ')
  address = int(access[2],base=16)
  print(hex(address & 0xFFFFFF))



