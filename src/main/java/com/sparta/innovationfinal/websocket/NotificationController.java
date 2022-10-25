package com.sparta.innovationfinal.websocket;

import com.sparta.innovationfinal.jwt.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    // 알림 전체조회
    @GetMapping("/auth/notification")
    public List<NotificationResponseDto> getNotification(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return notificationService.getNotification(userDetails);
    }

    // 리딩 안된 알람 개수 조회
    @GetMapping("/auth/notificationCount")
    public CountNotificationDto countNotication(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return notificationService.countNotification(userDetails);
    }

    // 알림 리딩 전체 확인
    @PostMapping("/auth/notification")
    public List<NotificationDto> readNotification(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return notificationService.readNotification(userDetails);
    }

    // 알림 전체삭제
    @DeleteMapping("/auth/notification")
    public BasicResponseDto deleteAllNotification(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return notificationService.deleteAllNotification(userDetails);
    }

//    // 알림 삭제
//    @DeleteMapping("/auth/notification/{notificationId}")
//    public BasicResponseDto deleteNotification(@PathVariable Long notificationId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//
//        return notificationService.deleteNotification(notificationId, userDetails);
//    }

}
