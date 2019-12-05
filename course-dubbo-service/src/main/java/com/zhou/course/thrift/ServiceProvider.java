package com.zhou.course.thrift;

import com.zhou.thrift.user.UserService;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @program microservice
 * @description:
 * @author: 周茜
 * @create: 2019/12/03 13:38
 */
@Component
public class ServiceProvider {

    @Value("${thrift.user.ip}")
    private String serverIp;

    @Value("${thrift.user.port}")
    private int serverPort;


    public UserService.Client getUserService() {
        return getService(serverIp, serverPort);
    }


    public <T> T getService(String ip, int port) {

        //建立socket连接
        TSocket socket = new TSocket(ip, port, 3000);
        //传输方式
        TTransport tTransport = new TFramedTransport(socket);

        try {
            tTransport.open();
        } catch (TTransportException e) {
            e.printStackTrace();
        }
        //传输协议
        TProtocol protocol = new TBinaryProtocol(tTransport);

        //thrift客户端
        TServiceClient result = new UserService.Client(protocol);


        return (T) result;
    }
}
