package com.ashish.sfgjms.listener;

import com.ashish.sfgjms.config.JmsConfig;
import com.ashish.sfgjms.model.HelloWorldMessage;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class HelloWorldListener {

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.HELLO_WORLD_QUEUE)
    public void listenMessage(@Payload HelloWorldMessage helloWorldMessage,
                              @Headers MessageHeaders messageHeaders,
                              Message message){
//        log.info("Listening message");
        log.info(helloWorldMessage.toString());
//        log.info("Exiting listener method");
    }

    @JmsListener(destination = JmsConfig.SEND_AND_RCV_QUEUE)
    public void listenAndSendMessage(@Payload HelloWorldMessage helloWorldMessage,
                                     @Headers MessageHeaders messageHeaders,
                                     Message message) throws JMSException {
        HelloWorldMessage payloadMsg = HelloWorldMessage.builder()
                .uuid(UUID.randomUUID()).message("world").build();
        log.info("Received and now replying back");
        jmsTemplate.convertAndSend(message.getJMSReplyTo(), payloadMsg);
    }
}
