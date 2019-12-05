# coding:utf-8
from message.api import MessageService
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol
from thrift.server import TServer

import smtplib
from email.mime.text import MIMEText
from email.header import Header

sender = 'imoocd@163.com'
authCode = 'aA111111'


class MessageServiceHandle:
    def sendMobileMessage(self, mobile, message):
        print("sendMobileMessage,mobile+" + mobile + "message," + message)
        return True

    def sendEmailMessage(self, email, message):
        print("sendEmailMessage,email:" + email + "message," + message)
        messageObj = MIMEText(message, "plain", "utf-8")
        messageObj['From'] = sender
        messageObj['to'] = email
        messageObj['Subject'] = Header("西瓜邮件", 'utf-8')
        try:
            smtpdObj = smtplib.SMTP("smpt.163.com")
            smtpdObj.sendmail(sender, [email], messageObj.as_string())
            print("send mail success")
            return True
        except smtplib.SMTPException as ex:
            print("send failed")
            print(ex)
            return False


if __name__ == '__main__':
    handle = MessageServiceHandle()
    # 实现的服务
    prcessor = MessageService.Processor(handle)
    # 监听端口ip
    transport = TSocket.TServerSocket("127.0.0.1", "8444")
    # 传输的方法
    tfactory = TTransport.TFramedTransportFactory()
    # 传输的协议
    pfactory = TBinaryProtocol.TBinaryProtocolFactory()

    server = TServer.TSimpleServer(prcessor, transport, tfactory, pfactory)
    print("python thrift server strat")
    server.serve()
    print("python thrift server exit")
