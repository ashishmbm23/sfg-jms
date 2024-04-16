package com.ashish.sfgjms.sender;

import com.ashish.sfgjms.config.JmsConfig;
import com.ashish.sfgjms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class HelloWorldSender {
    private final JmsTemplate jmsTemplate;

    @Scheduled(fixedRate = 2000)
    public void sendHelloWorld(){
        log.info("Entering hello world");
        HelloWorldMessage helloWorldMessage = HelloWorldMessage.builder()
                .uuid(UUID.randomUUID())
                .message("Hello World")
                .build();

        jmsTemplate.convertAndSend(JmsConfig.HELLO_WORLD_QUEUE, helloWorldMessage);

        log.info("Exiting hello world");
    }
}
