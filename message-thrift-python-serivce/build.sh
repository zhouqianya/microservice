#!/usr/bin/env bash

docker build -t  message-service:latest .

docker tag message-service:latest 192.168.113.161:8080/micro-service/message-service:latest

docker push 192.168.113.161:8080/micro-service/message-service:latest