package com.sparta.innovationfinal.websocket.event;

import com.sparta.innovationfinal.entity.Member;
import com.sparta.innovationfinal.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostLikeEvent {

    Member receiver;
    Member sender;
    Post post;

    public PostLikeEvent(Member receiver, Member sender, Post post) {
        this.receiver = receiver;
        this.sender = sender;
        this.post = post;
    }
}
