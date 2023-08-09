import sys, queue

if len(sys.argv) != 2:
  print ("Usage: mk_pagetable.py filename")
  sys.exit()

filename = sys.argv[1]
tracefd = open(filename,'r')

page_table = [-1] * 4096

phys_pages = list(range(64))
vpn_FIFO = queue.Queue(64)

hits = 0
faults = 0
phys_idx = 0

for line in tracefd.readlines():
 # print (line)
  address = int(line,base=16) & 0xFFFFFF
  vpn  = address >> 12
  print(hex(address) + " vpn: " + hex(vpn))
  if (page_table[vpn] != -1):
    print("Hit:" + hex(vpn) + " -> " + hex(page_table[vpn]))
    hits = hits + 1
  else:
    faults = faults + 1
    if (vpn_FIFO.full()):
      replace_vpn = vpn_FIFO.get()
      print("Replacing vpn, " + hex(replace_vpn) + " -> ppn " + hex(page_table[replace_vpn]) 
        + " new vpn " + hex(vpn) + " -> ppn " + hex(phys_idx))
      if (page_table[replace_vpn] != phys_idx):
          print ("Error phys page not correct")
      page_table[replace_vpn] = -1
      
    page_table[vpn] = phys_idx
    phys_idx = (phys_idx + 1)  % 64
    vpn_FIFO.put(vpn)
    print("Fault:" + hex(vpn) + " -> " + hex(page_table[vpn]))

#    if(len(phys_pages)):
#      page_table[vpn] = phys_pages.pop(0)
#      print("Fault:" + hex(vpn) + " -> " + hex(page_table[vpn]))
    

accesses = hits + faults
print("Accesses: " + str(accesses) + " Hits: " + str(hits) + " Faults: " + str(faults))


