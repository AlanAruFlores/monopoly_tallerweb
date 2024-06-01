package com.tallerwebi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic"); //Perfijo para recibir informacion
        registry.setApplicationDestinationPrefixes("/app"); //Prefijo para enviar informacion

    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/wspartida");
        registry.addEndpoint("/wsespera");
        registry.addEndpoint("/wsmonopolychat");
    }

}