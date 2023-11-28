package com.example.springboot;

import com.launchdarkly.eventsource.EventSource;
import com.launchdarkly.eventsource.background.BackgroundEventHandler;
import com.launchdarkly.eventsource.background.BackgroundEventSource;
import org.apache.kafka.common.protocol.types.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.concurrent.TimeUnit;

@Service
public class WikimediaChangesProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(WikimediaChangesProducer.class);

    @Value("${spring.kafka.topic.name}")
    private String topicName;
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public WikimediaChangesProducer(KafkaTemplate<String, String> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage() throws InterruptedException {

        //to read real time stream data from wikimedia, we use event source
        BackgroundEventHandler backgroundEventHandler = new WikimediaChangesHandler(kafkaTemplate, topicName);

        String url="https://stream.wikimedia.org/v2/stream/recentchange";
        EventSource.Builder eventSourceBuilder = new EventSource.Builder(URI.create(url));
        BackgroundEventSource.Builder builder = new BackgroundEventSource.Builder(backgroundEventHandler, eventSourceBuilder );
        BackgroundEventSource backgroundEventSource = builder.build();
        backgroundEventSource.start();

        TimeUnit.MINUTES.sleep(10);
    }

}
