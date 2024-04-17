package com.ashish.sfgjms.sender;

import com.ashish.sfgjms.config.JmsConfig;
import com.ashish.sfgjms.model.HelloWorldMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class HelloWorldSender {
    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedRate = 5000)
    public void sendHelloWorld(){
        log.info("Entering hello world");
        HelloWorldMessage helloWorldMessage = HelloWorldMessage.builder()
                .uuid(UUID.randomUUID())
                .message("Hello World")
                .build();

        jmsTemplate.convertAndSend(JmsConfig.HELLO_WORLD_QUEUE, helloWorldMessage);

        log.info("Exiting hello world");
    }

    @Scheduled(fixedRate = 2000)
    public void sendAndReceiveMessage() throws JMSException {
        HelloWorldMessage helloWorldMessage = HelloWorldMessage.builder()
                .uuid(UUID.randomUUID())
                .message("Hello")
                .build();

        MessageCreator messageCreator = (session) -> {
          Message message = null;
          try{
              message = session.createTextMessage(objectMapper.writeValueAsString(helloWorldMessage));
              message.setStringProperty("_type", "com.ashish.sfgjms.model.HelloWorldMessage");
              return message;
          } catch (JsonProcessingException e) {
              throw new JMSException(e.getMessage());
          }
        };
        log.info("Sending hello to queue");

        Message receivedMessage = jmsTemplate.sendAndReceive(JmsConfig.SEND_AND_RCV_QUEUE, messageCreator);

        log.info( receivedMessage.getBody(String.class).toString());
    }
}
