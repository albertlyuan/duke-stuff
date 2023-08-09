#!/usr/bin/env bash
mv /bin/uname /bin/uname.orig
printf '#!/bin/bash\n\nif [[ "$1" == "-r" ]] ;then\n echo '4.9.250'\n exit\nelse\n uname.orig "$@"\nfi' > /bin/uname
chmod 755 /bin/uname
apt-get install -y python3 python3-pip
apt-get install -y valgrind
apt-get install -y curl
apt-get install -y strace

