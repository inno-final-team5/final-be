package com.sparta.innovationfinal.websocket.event;

import com.sparta.innovationfinal.entity.Member;
import com.sparta.innovationfinal.entity.OneLineReview;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OneLineReviewLikeEvent {

    Member receiver;
    Member sender;
    OneLineReview oneLineReview;

    public OneLineReviewLikeEvent(Member receiver, Member sender, OneLineReview oneLineReview) {
        this.receiver = receiver;
        this.sender = sender;
        this.oneLineReview = oneLineReview;
    }
}
