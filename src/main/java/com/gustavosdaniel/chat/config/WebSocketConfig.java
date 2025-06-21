package com.gustavosdaniel.chat.config;

import org.springframework.context.annotation.Configuration;

import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker  // Habilita o servidor WebSocket com broker de mensagens
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Define o prefixo para mensagens que serão processadas por métodos @MessageMapping
        // Mensagens enviadas para destinos começando com "/app" serão roteadas para estes métodos
        registry.setApplicationDestinationPrefixes("/app");

        // Habilita um broker de mensagens simples na memória
        // Permite enviar mensagens para destinos com prefixo "/topic"
        registry.enableSimpleBroker("/topic");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Define o endpoint "/ws" para conexões WebSocket
        // withSockJS() habilita fallback para navegadores sem suporte nativo a WebSocket
        registry.addEndpoint("/ws").withSockJS();
    }
}