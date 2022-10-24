package com.sparta.innovationfinal.websocket;

import com.sparta.innovationfinal.entity.Member;
import com.sparta.innovationfinal.jwt.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springfox.documentation.service.ResponseMessage;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationService {

    private final NotificationRepository notificationRepository;

    // 알림 전체 조회
    public List<NotificationResponseDto> getNotification(UserDetailsImpl userDetails) {
        return notificationRepository.findAllNotificationsByReceiver(userDetails.getMember().getId()).stream()
                .map(NotificationResponseDto::new)
                .collect(Collectors.toList());
    }

    // 리딩 안된 알람 개수 조회
    public CountNotificationDto countNotification(UserDetailsImpl userDetails) {
        Long notificationCount = notificationRepository.countByReadingStatusAndReceiver(ReadingStatus.N, userDetails.getMember());
        return new CountNotificationDto(notificationCount);
    }

    // 리딩 전체 확인
    @Transactional
    public List<NotificationDto> readNotification(UserDetailsImpl userDetails) {
        return notificationRepository.findNotificationByReceiver(userDetails.getMember().getId())
                .stream()
                .map(NotificationDto::new)
                .collect(Collectors.toList());
    }

//    // 알림 삭제
//    public BasicResponseDto deleteNotification(Long notificationId, UserDetailsImpl userDetails) {
//        Member member = userDetails.getMember();
//        notificationRepository.deleteByNotificationIdAndReceiver(notificationId, member);
//        return new BasicResponseDto("success");
//    }

    // 알림 전체 삭제
    @Transactional
    public BasicResponseDto deleteAllNotification(UserDetailsImpl userDetails) {
        notificationRepository.deleteByReceiver(userDetails.getMember());
        return new BasicResponseDto("success");
    }
}
