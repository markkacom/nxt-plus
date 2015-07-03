#!/bin/sh
CP="lib/*:classes"
SP=src/java/

/bin/rm -f nxtservice.jar
/bin/mkdir -p classes/

javac -sourcepath "${SP}" -classpath "${CP}" -d classes/ src/java/nxt/*.java src/java/nxt/*/*.java || exit 1

/bin/rm -f nxt.jar 
jar cf nxt.jar -C classes . || exit 1
/bin/rm -rf classes

echo "nxt.jar generated successfully"

/bin/rm -f nxt.zip
zip -qr -9 nxt.zip conf/nxt-default.properties conf/logging-default.properties html/ lib/ logs/ nxt.jar MIT-license.txt README.txt run.bat run.sh

echo "nxt.zip generated successfully"
