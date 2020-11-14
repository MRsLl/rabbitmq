package com.atguigu.rabbitmq.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {

    private static String QUEUE_NAME = "simple_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        //1.创建连接工厂并配置，创建连接
        ConnectionFactory connectionFactory = new ConnectionFactory();

        connectionFactory.setHost("192.168.136.128");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("123456");
        connectionFactory.setVirtualHost("/");

        Connection connection = connectionFactory.newConnection();
        //2.创建信道
        Channel channel = connection.createChannel();
        //3.创建队列并发送信息
        /**
         * @param queue 队列名
         * @param durable true 持久化队列，服务重启后仍存在
         * @param exclusive true 设置一个独占队列，只能有一个消费者监听
         * @param autoDelete true 空闲时自动删除，没有消费者时自动删除
         * @param arguments 队列其他参数
         */
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        String message = "Hello,RabbitMQ";
        /**
         * @param exchange 发布消息的交换机
         * @param routingKey 路由key
         * @param props 消息的配置信息
         * @param body 消息体
         */
        channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
        //4.关闭资源
        channel.close();
        connection.close();
    }
}
