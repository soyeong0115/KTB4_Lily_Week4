package org.example.communityapi.config;

import org.example.communityapi.jwt.JwtHandshakeInterceptor;
import org.example.communityapi.jwt.JwtProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private final WebSocketHandler webSocketHandler;
    private final JwtProvider jwtProvider;

    public WebSocketConfig(WebSocketHandler webSocketHandler, JwtProvider jwtProvider) {
        this.webSocketHandler = webSocketHandler;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler, "/ws/alarm")
                .setAllowedOrigins("*")
                .addInterceptors(new JwtHandshakeInterceptor(jwtProvider));
    }
}
