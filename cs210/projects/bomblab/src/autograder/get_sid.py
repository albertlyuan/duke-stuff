
from json.tool import main
from unicodedata import name
import sys
import json

def get_sid():
    name2netid = {}

    with open("roaster.csv", 'r') as fp:
        lines  = fp.readlines()
        for line in lines:
            splitted = line.split(",")
            name_email = splitted[3]
            netid_email = splitted[2]
            name2netid[name_email] = netid_email[0:netid_email.find("@")]
            name2netid[netid_email] = netid_email[0:netid_email.find("@")]
    
    with open("/autograder/submission_metadata.json", 'r') as fp:
        email = json.loads(fp.read())['users'][0]['email']
    
    sid = name2netid[email]

    print (sid)
    return sid

get_sid()
