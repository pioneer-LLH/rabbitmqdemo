package com.rabbitmqdemo.rabbitmqdemo.shili2.producer;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

/*rabbit 生产者*/
public class NewTask {
    private final static String TASK_QUEUE_NAME  = "task_queue";

    public static void main(String[] args) throws Exception{
        //创建连接到rabbit服务器的连接
        ConnectionFactory factory =new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection =factory.newConnection();
        Channel channel = connection.createChannel();

        //指明消息发送到那个队列
        channel.queueDeclare(TASK_QUEUE_NAME ,true,false,false,null);
        String message= "11112221";

        //channel.basicPublish发布消息,第二个参数设置为MessageProperties.PERSISTENT_TEXT_PLAIN,消息持久化
        channel.basicPublish("",TASK_QUEUE_NAME ,MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());
        System.out.println("[x] Sent '"+message+"'");

        //关闭通道和连接
        channel.close();
        connection.close();
    }
}
