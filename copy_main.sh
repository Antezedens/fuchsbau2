#!/bin/sh
./gradlew build
#cd static
#cp default.css main.bundle.js /nfs/bfu/pi/temp-switch/html/
tar -C build/distributions/ -cz fuchsbau2.js | ssh bernhard@fuchsbau "ssh root@10.5.5.1 'tar -C /tmp -xz && mv /tmp/fuchsbau2.js /root/temp-switch/html/main.bundle.js' "

