package com.sparta.innovationfinal.websocket.event;

import com.sparta.innovationfinal.entity.Badge;
import com.sparta.innovationfinal.entity.Member;
import com.sparta.innovationfinal.websocket.NotificationType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BadgeEvent {

    Member receiver;
    Member sender;
    Badge badge;
    NotificationType notificationType;

    public BadgeEvent(Member receiver, Member sender, Badge badge, NotificationType notificationType) {

        this.receiver = receiver;
        this.sender = sender;
        this.badge = badge;
        this.notificationType = notificationType;
    }
}
