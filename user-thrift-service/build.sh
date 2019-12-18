#!/usr/bin/env bash

mvn package

docker build -t user-service:latest  .
docker tag user-service:latest 192.168.113.161:8080/micro-service/user-service:latest
docker push 192.168.113.161:8080/micro-service/user-service:latest