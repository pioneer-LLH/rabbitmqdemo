package com.rabbitmqdemo.rabbitmqdemo.shili2.consumers;
import com.rabbitmq.client.*;

import java.io.IOException;


/*rabbit 消费者*/
public class Worker {
    private final static String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] args) throws Exception{
        //建立连接和通道
        ConnectionFactory factory =new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection =factory.newConnection();
        Channel channel =connection.createChannel();

        //声明要消费的队列
        channel.queueDeclare(TASK_QUEUE_NAME,true,false,false,null);//指定第二个参数为true保证rabbitmq不会丢失队列(即队列持久化)
        System.out.println("[*] Waiting for messages. To exit press CTRL+C");

        //回调消费消息
        channel.basicQos(1);  //接收消息的最大数量。

        final Consumer consumer =new DefaultConsumer(channel){
          @Override
          public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException   {
              String message= new String(body,"UTF-8");
                  System.out.println("[x] Received'"+message+"'");
              try {
                  doWork(message);
              }catch (Exception e){
                  e.printStackTrace();
              }
              finally {
                  System.out.println("[x] Done");
                  channel.basicAck(envelope.getDeliveryTag(),false);//启动消息确认,即手动消息确认(ack)
              }
          }
        };
        channel.basicConsume(TASK_QUEUE_NAME,false,consumer);//第二个参数,是否启动消息确认机制(ack),true关闭,false打开
    }

    private static void doWork(String task) throws InterruptedException  {
        for (char ch: task.toCharArray()){
            if(ch =='.'){
                try {
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

}
