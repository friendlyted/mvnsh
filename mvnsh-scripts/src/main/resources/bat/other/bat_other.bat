@echo off
echo "starting bat..."
ping 127.0.0.1 -n 3 > nul
echo "TEST BAT EXECUTED SUCCESSFULLY!"

echo SOME ERROR 1>&2

call @mvnsh pro.friendlyted/mvnsh-scripts/1.0-SNAPSHOT/bat/bat.bat_root

echo 'hjhjhjhjhh' > jhgjh

