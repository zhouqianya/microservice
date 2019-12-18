#!/usr/bin/env bash

mvn package

docker build -t api-gateway-zuul:latest  .

docker tag api-gateway-zuul:latest 192.168.113.161:8080/micro-service/api-gateway-zuul:latest

docker push 192.168.113.161:8080/micro-service/api-gateway-zuul:latest