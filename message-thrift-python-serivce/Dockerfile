FROM 192.168.113.161:8080/micro-service/python-base:latest
MAINTAINER ZHOU 469836175@qq.com

ENV PYTHONPATH /
COPY message /message

ENTRYPOINT ["python","/message/message_service.py"]