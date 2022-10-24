package com.sparta.innovationfinal.websocket.event;

import com.sparta.innovationfinal.entity.Member;
import com.sparta.innovationfinal.websocket.NotificationType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostCommentEvent {

    Member receiver;
    Member sender;
    Object object;
    NotificationType notificationType;

    public PostCommentEvent(Member receiver, Member sender, Object object, NotificationType notificationType) {

        this.receiver = receiver;
        this.sender = sender;
        this.object = object;
        this.notificationType = notificationType;
    }
}
