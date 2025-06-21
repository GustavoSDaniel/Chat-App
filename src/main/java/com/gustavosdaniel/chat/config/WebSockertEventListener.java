package com.gustavosdaniel.chat.config;

import com.gustavosdaniel.chat.chat.ChatMessage;
import com.gustavosdaniel.chat.chat.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
@Slf4j // log
public class WebSockertEventListener {

    private final SimpMessageSendingOperations messagingTemplate;

    @EventListener // Marca o método `handleWebSocketDisconnect` como um ouvinte de eventos. Ele será invocado quando um evento do tipo `SessionDisconnectEvent` for publicado.
    private void handleWebSocketDisconnect(SessionDisconnectEvent event) { // Declara o método que lida com o evento de desconexão de sessão WebSocket. Ele recebe um objeto SessionDisconnectEvent como parâmetro.
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage()); // Cria um `StompHeaderAccessor` a partir da mensagem do evento. Isso permite acessar os cabeçalhos STOMP e atributos da sessão WebSocket.
        String username = (String) accessor.getSessionAttributes().get("username"); // Tenta recuperar o nome de usuário (username) dos atributos da sessão WebSocket.
        if (username != null) { // Verifica se o nome de usuário foi encontrado nos atributos da sessão.
            log.info("Usuario disconectado:{}", username); // Se o nome de usuário não for nulo, registra uma mensagem de log informando qual usuário se desconectou.
            var chatMessage = ChatMessage.builder() // Inicia a construção de um novo objeto ChatMessage usando o padrão Builder (fornecido pelo Lombok para ChatMessage, presumindo que está configurado).
                    .type(MessageType.LEAVE) // Define o tipo da mensagem como LEAVE (saída), indicando que um usuário deixou o chat.
                    .sender(username) // Define o remetente da mensagem como o nome de usuário que se desconectou.
                    .build();
            messagingTemplate.convertAndSend("/topic/public", chatMessage); // Envia a mensagem de chat (indicando que o usuário saiu) para o tópico "/topic/public". Todos os clientes inscritos neste tópico receberão esta mensagem.
        }
    }
}
