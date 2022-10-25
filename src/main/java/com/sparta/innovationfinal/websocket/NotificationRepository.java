package com.sparta.innovationfinal.websocket;

import com.sparta.innovationfinal.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository <Notification, Long> {

    // 알림 전체 조회
    List<Notification> findAllNotificationsByReceiver(Long MemberId);

    // 알림 리딩 전체 확인
    List<Notification> findNotificationByReceiver(Long MemberId);

    // 리딩 안된 알림 개수 조회
    Long countByReadingStatusAndReceiver(ReadingStatus N, Member member);

//    // 알림 삭제
//    void deleteByNotificationIdAndReceiver(Long notificationId, Member member);

    // 알림 전체삭제
    void deleteByReceiver(Member member);
}
