package com.rabbitmqdemo.rabbitmqdemo.shli1.producer;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/*rabbit 生产者*/
public class Send {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception{
        //创建连接到rabbit服务器的连接
        ConnectionFactory factory =new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection =factory.newConnection();
        Channel channel = connection.createChannel();

        //指明消息发送到那个队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        String message= "Hello World!3";

        //发布消息
        channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
        System.out.println("[x] Sent '"+message+"'");

        //关闭通道和连接
        channel.close();
        connection.close();
    }
}
