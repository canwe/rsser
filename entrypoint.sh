#!/bin/bash
mvn -f /home/app/pom.xml -s /usr/share/maven/ref/settings-docker.xml appengine:devserver_start
tail -f /dev/null