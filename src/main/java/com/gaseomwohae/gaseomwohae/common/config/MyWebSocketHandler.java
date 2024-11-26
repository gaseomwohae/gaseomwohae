package com.gaseomwohae.gaseomwohae.common.config;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class MyWebSocketHandler extends TextWebSocketHandler {

    // 클라이언트로부터 메시지 수신 시 호출
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        try {
            // 메시지를 클라이언트에게 다시 전송 (에코)
            session.sendMessage(new TextMessage("Received: " + message.getPayload()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 연결이 열릴 때 호출
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("WebSocket 연결 성공: " + session.getId());
    }

    // 연결 종료 시 호출
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("WebSocket 연결 종료: " + session.getId());
    }
}