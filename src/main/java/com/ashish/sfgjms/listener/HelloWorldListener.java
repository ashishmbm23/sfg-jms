package com.ashish.sfgjms.listener;

import com.ashish.sfgjms.config.JmsConfig;
import com.ashish.sfgjms.model.HelloWorldMessage;
import jakarta.jms.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class HelloWorldListener {

    @JmsListener(destination = JmsConfig.HELLO_WORLD_QUEUE)
    public void listenMessage(@Payload HelloWorldMessage helloWorldMessage,
                              @Headers MessageHeaders messageHeaders,
                              Message message){
        log.info("Listening message");
        log.info(helloWorldMessage.toString());
        log.info("Exiting listener method");
    }
}
