package com.truongjae.predictlove.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

@Configuration
@EnableWebSocketMessageBroker
public class WsConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry
				.addEndpoint("/predict-love-chat")
				.withSockJS();
		registry
				.addEndpoint("/predict-love-chat")
				.setAllowedOrigins("https://truongjae.github.io")
//				.setAllowedOrigins("http://127.0.0.1:5501")
				.withSockJS();
	}
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableSimpleBroker("/topic");
		registry.setApplicationDestinationPrefixes("/app");
	}

	@Override
	public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
		registry.setMessageSizeLimit(999999999); // default : 64 * 1024
		registry.setSendTimeLimit(50 * 10000); // default : 10 * 10000
		registry.setSendBufferSizeLimit(5* 512 * 1024); // default : 512 * 1024
	}
}
