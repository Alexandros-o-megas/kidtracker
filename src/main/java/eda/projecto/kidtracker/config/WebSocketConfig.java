package eda.projecto.kidtracker.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
  
  @Override
  public void configureMessageBroker(MessageBrokerRegistry config) {
    // Os clientes (frontend/app) vão subscrever em destinos que começam com "/topic"
    config.enableSimpleBroker("/topic");
    // As mensagens enviadas pelos clientes para o servidor devem ser para destinos que começam com "/app"
    config.setApplicationDestinationPrefixes("/app");
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    // Este é o endpoint HTTP que os clientes usam para se ligar ao WebSocket
    registry.addEndpoint("/ws").setAllowedOrigins("*");
    // .withSockJS() é uma alternativa para browsers que não suportam WebSocket nativamente. Removi por simplicidade inicial.
  }
}
