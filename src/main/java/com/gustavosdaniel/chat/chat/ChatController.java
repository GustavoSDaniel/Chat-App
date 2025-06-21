package com.gustavosdaniel.chat.chat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ChatController {
    @MessageMapping("/chat.sendMessage") // mapeia este método para receber mensagens enviadas para o destino "/app/chat.sendMessage".
    @SendTo("/topic/public") // Anotação que especifica o destino para onde o valor de retorno deste método será enviado. Neste caso, a mensagem de retorno será enviada para o tópico "/topic/public", que todos os clientes inscritos receberão.
    public ChatMessage sendMessage( @Payload ChatMessage chatMessage){
        // @Payload: Anotação que indica que o parâmetro `chatMessage` deve ser populado com o corpo (payload) da mensagem
        // ChatMessage chatMessage: O objeto ChatMessage que representa a mensagem de chat enviada pelo cliente.

        return chatMessage;
    }

    @MessageMapping("/chat.addUser")// ← Captura mensagens para "/app/chat.addUser"
    @SendTo("/topic/public") // ← Envia a resposta para todos inscritos em "/topic/publ
    public ChatMessage addUSer(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor){ // Declara o método addUSer.

        headerAccessor.getSessionAttributes().put("userName", chatMessage.getSender()); // Acessa os atributos da sessão WebSocket (armazenamento temporário associado à conexão do cliente) e adiciona um atributo chamado "userName" com o valor do remetente (sender) da mensagem de chat.

        return chatMessage;
    }

}
