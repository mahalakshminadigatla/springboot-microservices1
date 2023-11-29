package com.example.springbootrabbitmqtutorial.consumer;

import com.example.springbootrabbitmqtutorial.dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbiMQJsonConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbiMQJsonConsumer.class);

    @RabbitListener(queues = {"${rabbitmq.queue.json.name}"})
    public void consumeJsonMessage(User user){
        LOGGER.info(String.format("Json Message Received -> %s", user.toString()));

    }
}
