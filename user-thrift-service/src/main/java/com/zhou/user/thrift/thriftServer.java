package com.zhou.user.thrift;

import com.zhou.thrift.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @program microservice
 * @description:
 * @author: 周茜
 * @create: 2019/12/03 12:47
 */
@Configuration
@Slf4j
public class thriftServer {

    @Value("${service.port}")
    private  int serverPort;

    @Autowired
    private UserService.Iface userService;


    @PostConstruct
    public  void startThriftServer(){

        log.info("【thriftServer】start");
        //实现的service加入注册到执行器  处理器
        TProcessor processor= new UserService.Processor<>(userService);
        //网络 socket   单线程  线程池  nio  这里用nio
        TNonblockingServerSocket socket=null;
        try {
             socket =new TNonblockingServerSocket(serverPort);
        } catch (TTransportException e) {
            e.printStackTrace();
        }
        //nio线程 参数
        TNonblockingServer.Args args= new TNonblockingServer.Args(socket);
        //传输处理器
        args.processor(processor);
        //传输方式
        args.transportFactory(new TFramedTransport.Factory());
        //传输协议
        args.protocolFactory(new TBinaryProtocol.Factory());

        TServer server=new TNonblockingServer(args);
        log.info("start");

        server.serve();

    }

}
