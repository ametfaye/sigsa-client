#!/bin/bash
nohup java -jar cagesodm.jar > /home/int2/ofcc/cagesodm.pid 2>&1 &
echo $! > /home/int2/ofcc/cagesodm.pid