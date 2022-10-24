package com.sparta.innovationfinal.websocket;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NotificationResponseDto {

    private Long notificationId;
    private String senderNickName;

    private Long receiverId;
    private NotificationType notificationType;
    private Long id;
    private String title;
    private String modifiedAt;
    private ReadingStatus readingStatus;

    public NotificationResponseDto(Notification notification) {
        this.notificationId = notification.getNotificationId();
        this.receiverId = notification.getReceiver().getId();
        this.senderNickName = notification.getSenderNickName();
        this.notificationType = notification.getNotificationType();
        this.id = notification.getId();
        this.title = notification.getTitle();
        this.modifiedAt = TimeHandler.setModifiedAtComment(notification.getModifiedAt());
        this.readingStatus = notification.getReadingStatus();
    }
}
