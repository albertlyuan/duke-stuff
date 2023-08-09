# gradescope autograder for bomblab, simple find the defuse strings 
# for each phase and generate json

import json

p1max = 20; # string entry
p2max = 20; # 4 numbers  
p3max = 30; # switch statement
p4max = 30; # memory problems

p1score = 0
p2score = 0
p3score = 0
p4score = 0

outfile = open('out.txt')
for line in outfile:
    if 'Phase 1 defused. How about the next one?' in line:
        p1score = p1max
    if 'That\'s number 2.  Keep going!' in line:
        p2score = p2max
    if 'Almost there, one more to go!' in line:
        p3score = p3max
    if 'Congratulations! You\'ve defused the bomb!' in line:
        p4score = p4max


total_score = p1score + p2score + p3score + p4score
output = {"score": total_score, "stdout_visibility": "visible", "tests": [
{"name": "Phase1", "score": p1score, "max_score": p1max },
{"name": "Phase2", "score": p2score, "max_score": p2max },
{"name": "Phase3", "score": p3score, "max_score": p3max },
{"name": "Phase4", "score": p4score, "max_score": p4max }
]
}
with open("results.json", "w+") as result_file:
    json.dump(output, result_file)
