#!/usr/bin/env bash

mvn package

docker build -t course-edge-service:latest  .

docker tag course-edge-service:latest 192.168.113.161:8080/micro-service/course-edge-service:latest

docker push 192.168.113.161:8080/micro-service/course-edge-service:latest
