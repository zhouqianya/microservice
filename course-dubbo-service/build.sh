#!/usr/bin/env bash

mvn package

docker build -t course-service:latest  .

docker tag course-service:latest 192.168.113.161:8080/micro-service/course-service:latest

docker push 192.168.113.161:8080/micro-service/course-service:latest