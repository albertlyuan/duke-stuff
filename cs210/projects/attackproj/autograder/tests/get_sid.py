from json.tool import main
from unicodedata import name
import sys
import json

def get_sid():
    name2netid = {}

    with open("/autograder/source/roster.csv", 'r') as fp:
        lines  = fp.readlines()
        for line in lines:
            splitted = line.split(",")
            name_email = splitted[-3]
            netid_email = splitted[-4]
            name2netid[name_email] = netid_email[0:netid_email.find("@")]
    
    with open("/autograder/submission_metadata.json", 'r') as fp:
        emails = [q['email'] for q in json.loads(fp.read())['users']]
    
    sids = [name2netid[email] for email in emails]

    print(sids)
    return sids

get_sid()
