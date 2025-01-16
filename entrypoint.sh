#!/bin/bash
mvn -f /home/app/pom.xml appengine:devserver_start
tail -f /dev/null