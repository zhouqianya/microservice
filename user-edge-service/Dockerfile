FROM 192.168.113.161:8080/micro-service/openjdk:8u212-jdk
MAINTAINER ZHOU 469836175@qq.com

COPY target/user-edge-service-1.0-SNAPSHOT.jar  /user-edge-service.jar

ENTRYPOINT ["java","-jar","/user-edge-service.jar"]