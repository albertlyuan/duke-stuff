#!/bin/bash
sudo service mongod start
mongorestore --db congress /opt/dbcourse/examples/congress/mongodb-dump/congress