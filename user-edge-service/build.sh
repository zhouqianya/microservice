#!/usr/bin/env bash

mvn package

docker build -t user-edge-service:latest  .
docker tag user-edge-service:latest  192.168.113.161:8080/micro-service/user-edge-service:latest
docker push 192.168.113.161:8080/micro-service/user-edge-service:latest