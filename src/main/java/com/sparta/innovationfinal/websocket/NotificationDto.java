package com.sparta.innovationfinal.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDto implements Serializable {

    private Long notificationId;
    private String senderNickName;
    private NotificationType notificationType;
    private Long id;
    private String title;
    private LocalDateTime modifiedAt;
    private ReadingStatus readingStatus;

    public NotificationDto(Notification notification) {

        notification.changReadingStatus(ReadingStatus.Y);
        this.notificationId = notification.getNotificationId();
        this.senderNickName = notification.getSenderNickName();
        this.notificationType = notification.getNotificationType();
        this.id = notification.getId();
        this.title = notification.getTitle();
        this.modifiedAt = notification.getModifiedAt();
        this.readingStatus = notification.getReadingStatus();
    }

    public static NotificationDto convertMessageToDto(Notification notification) {

        return new NotificationDto(
                notification.getNotificationId(),
                notification.getSenderNickName(),
                notification.getNotificationType(),
                notification.getId(),
                notification.getTitle(),
                notification.getModifiedAt(),
                notification.getReadingStatus()
        );
    }
}
