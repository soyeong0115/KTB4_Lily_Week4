package org.example.communityapi.notification;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class NotificationWebSocketHandler extends TextWebSocketHandler {
    private List<WebSocketSession> sessions = new ArrayList<>();
    private Map<Integer, WebSocketSession> sessionMap = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        sessionMap.put((Integer) session.getAttributes().get("userId"), session);

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
        sessionMap.remove((Integer) session.getAttributes().get("userId"));
    }

    public void sendToUser(Integer userId, String message) throws IOException {
        WebSocketSession session = sessionMap.get(userId);

        if (session != null && session.isOpen()) {
            session.sendMessage(new TextMessage(message));
        }
    }
}
