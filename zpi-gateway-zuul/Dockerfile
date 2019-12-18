FROM 192.168.113.161:8080/micro-service/openjdk:8u212-jdk
MAINTAINER ZHOU 469836175@qq.com

COPY target/zpi-gateway-zuul-1.0-SNAPSHOT.jar  /api-gateway-zuul.jar

ENTRYPOINT ["java","-jar","/api-gateway-zuul.jar"]