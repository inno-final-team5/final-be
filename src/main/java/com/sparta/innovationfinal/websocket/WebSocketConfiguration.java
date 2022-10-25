package com.sparta.innovationfinal.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    // jwt 토큰 인증 핸들러
    private final StompHandler stompHandler;
    
    // 연결하려면 반드시 필요
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        registry.enableSimpleBroker("/sub");
        // 여기로 데이터가 들어오면 messageMapping 으로 jump
        registry.setApplicationDestinationPrefixes("/pub");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        registry.addEndpoint("/ws")
                .setAllowedOrigins("https://www.moviecritic.site","https://yjcoding.shop","http://localhost:3000");

        registry.addEndpoint("/ws")
                .setAllowedOrigins("https://www.moviecritic.site","https://yjcoding.shop","http://localhost:3000")
                .withSockJS();

//        registry.addEndpoint("/ws")
//                .setAllowedOrigins("https://www.moviecritic.site/","https://yjcoding.shop/","http://localhost:3000","http://localhost:8080")
//                .withSockJS();

//        registry.addEndpoint("/ws")
//                .setAllowedOrigins("http://localhost:3000","http://localhost:8080");

//        registry.addEndpoint("/ws")
//                .setAllowedOrigins("http://localhost:3000","http://localhost:8080").withSockJS();

//        registry.addEndpoint("/ws")
//                .setAllowedOrigins(FRONTEND_URL, DEVELOP_URL, "http://localhost:8080");
//        registry.addEndpoint("/ws")
//                .setAllowedOrigins(FRONTEND_URL, DEVELOP_URL, "http://localhost:8080").withSockJS();

//        registry.addEndpoint("/ws-stomp")   // alarm 웹 소켓 연결 주소
//                .setAllowedOrigins("http://localhost:8080")
//                .withSockJS();  // 낮은 버전 브라우저에서도 websocket 작동하도록
    }
    // StompHandler가 webSocket 앞단에서 token 및 메시지 타입 등을 체크할 수 있게 인터셉터 설정
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(stompHandler);    // 핸들러 등록
    }

}
