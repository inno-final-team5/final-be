package com.sparta.innovationfinal.websocket.event;

import com.sparta.innovationfinal.entity.Comment;
import com.sparta.innovationfinal.entity.Member;
import com.sparta.innovationfinal.entity.Post;
import com.sparta.innovationfinal.websocket.NotificationType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostCommentEvent {

    Member receiver;
    Member sender;
    Comment comment;
    NotificationType notificationType;

    public PostCommentEvent(Member receiver, Member sender, Comment comment, NotificationType notificationType) {

        this.receiver = receiver;
        this.sender = sender;
        this.comment = comment;
        this.notificationType = notificationType;
    }
}
