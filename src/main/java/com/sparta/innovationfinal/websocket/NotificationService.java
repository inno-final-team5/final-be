package com.sparta.innovationfinal.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.innovationfinal.entity.Member;
import com.sparta.innovationfinal.jwt.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationService {

    private final NotificationRepository notificationRepository;

    private final ObjectMapper objectMapper;
    private final SimpMessageSendingOperations messagingTemplate;


    public void sendMessage(String publishMessage) {
        log.info("데이터가 잘 들어오나요? publishMessage={}", publishMessage);
        try {
            NotificationResponseDto notification = objectMapper.readValue(publishMessage, NotificationResponseDto.class);

            log.info("notification.getReceiverId() = {}", notification.getReceiverId());
            log.info("/notification/{}", notification.getReceiverId());
            messagingTemplate.convertAndSend("/sub/notification/user/" + notification.getReceiverId(), notification);
        } catch (Exception e) {
            log.error("Exception {}", e);
        }
    }

    // 알림 전체 조회
    public List <NotificationResponseDto> getNotification(UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        List <Notification> notifications = notificationRepository.findAllByReceiver(member);
        List <NotificationResponseDto> response = new ArrayList<>();

        for (Notification notification : notifications) {
            NotificationResponseDto notificationResponseDto = new NotificationResponseDto(notification);
            response.add(notificationResponseDto);
        }
        return response;
    }

    // 리딩 안된 알람 개수 count
    public CountNotificationDto countNotification(UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        Long notificationCount = notificationRepository.countByReadingStatusAndReceiver(ReadingStatus.N, member);

        CountNotificationDto countNotificationDto = new CountNotificationDto(notificationCount);

        return countNotificationDto;
    }

    // 리딩 전체 확인
    @Transactional
    public List <NotificationDto> readNotification(UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        List <NotificationDto> notificationDtos = new ArrayList<>();
        List <Notification> notifications = notificationRepository.findAllByReceiver(member);

        for (Notification notification : notifications) {
            NotificationDto notificationDto = new NotificationDto(notification);
            notification.changReadingStatus(ReadingStatus.Y);
            notificationDtos.add(notificationDto);
        }
        return notificationDtos;
    }

    // 알림 전체 삭제
    @Transactional
    public ResponseEntity<?> deleteAllNotification(UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        notificationRepository.deleteByReceiver(member);
        return ResponseEntity.ok().body(new BasicResponseDto("true"));
    }

//    // 알림 삭제
//    public BasicResponseDto deleteNotification(Long notificationId, UserDetailsImpl userDetails) {
//        Member member = userDetails.getMember();
//        notificationRepository.deleteByNotificationIdAndReceiver(notificationId, member);
//        return new BasicResponseDto("success");
//    }


}
