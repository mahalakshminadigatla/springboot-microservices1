package com.example.springbootrabbitmqtutorial.controller;

import com.example.springbootrabbitmqtutorial.dto.User;
import com.example.springbootrabbitmqtutorial.publisher.RabbiMQJSonProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class MessageJsonController {

    private RabbiMQJSonProducer rabbiMQJSonProducer;

    public MessageJsonController(RabbiMQJSonProducer rabbiMQJSonProducer) {
        this.rabbiMQJSonProducer = rabbiMQJSonProducer;
    }

    @PostMapping("/publish")
    public ResponseEntity<String> sendJsonMessage(@RequestBody User user){
        rabbiMQJSonProducer.sendJsonMessage(user);
        return ResponseEntity.ok("Json Message has sent to RabbitMQ...");

    }
}
