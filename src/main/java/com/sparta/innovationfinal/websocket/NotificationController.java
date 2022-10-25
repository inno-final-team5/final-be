package com.sparta.innovationfinal.websocket;

import com.sparta.innovationfinal.jwt.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    private final SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/{nickname}")
    public void message(@DestinationVariable("nickname") String nickname) {
        messagingTemplate.convertAndSend("/sub/" + nickname, "alarm socket connection completed.");
    }

    // 알림 전체조회
    @GetMapping("/auth/notification")
    public ResponseEntity<?> getNotification(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(notificationService.getNotification(userDetails));
    }

    // 리딩 안된 알람 개수 조회
    @GetMapping("/auth/notificationCount")
    public ResponseEntity countNotication(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(notificationService.countNotification(userDetails));
    }

    // 알림 리딩 전체 확인
    @PostMapping("/auth/notification")
    public ResponseEntity<?> readNotification(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(notificationService.readNotification(userDetails));
    }

    // 알림 전체삭제
    @DeleteMapping("/auth/notification")
    public ResponseEntity<?> deleteAllNotification(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(notificationService.deleteAllNotification(userDetails));
    }

//    // 알림 전체조회
//    @GetMapping("/auth/notification")
//    public List<NotificationResponseDto> getNotification(@AuthenticationPrincipal UserDetailsImpl userDetails) {
//        return notificationService.getNotification(userDetails);
//    }
//
//    // 리딩 안된 알람 개수 조회
//    @GetMapping("/auth/notificationCount")
//    public CountNotificationDto countNotication(@AuthenticationPrincipal UserDetailsImpl userDetails) {
//        return notificationService.countNotification(userDetails);
//    }
//
//    // 알림 리딩 전체 확인
//    @PostMapping("/auth/notification")
//    public List<NotificationDto> readNotification(@AuthenticationPrincipal UserDetailsImpl userDetails) {
//        return notificationService.readNotification(userDetails);
//    }
//
//    // 알림 전체삭제
//    @DeleteMapping("/auth/notification")
//    public BasicResponseDto deleteAllNotification(@AuthenticationPrincipal UserDetailsImpl userDetails) {
//        return notificationService.deleteAllNotification(userDetails);
//    }



//    // 알림 삭제
//    @DeleteMapping("/auth/notification/{notificationId}")
//    public BasicResponseDto deleteNotification(@PathVariable Long notificationId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//
//        return notificationService.deleteNotification(notificationId, userDetails);
//    }

}
