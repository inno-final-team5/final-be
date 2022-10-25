package com.sparta.innovationfinal.websocket;

import com.sparta.innovationfinal.entity.Comment;
import com.sparta.innovationfinal.entity.Member;
import com.sparta.innovationfinal.entity.OneLineReview;
import com.sparta.innovationfinal.entity.Post;
import com.sparta.innovationfinal.repository.CommentRepository;
import com.sparta.innovationfinal.repository.MemberRepository;
import com.sparta.innovationfinal.repository.OneLineReviewLikeRepository;
import com.sparta.innovationfinal.repository.PostRepository;
import com.sparta.innovationfinal.websocket.event.BadgeEvent;
import com.sparta.innovationfinal.websocket.event.OneLineReviewLikeEvent;
import com.sparta.innovationfinal.websocket.event.PostCommentEvent;
import com.sparta.innovationfinal.websocket.event.PostLikeEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Slf4j
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class EventHandler {

    private NotificationRepository notificationRepository;
    private NotificationService notificationService;
    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private OneLineReviewLikeRepository oneLineReviewLikeRepository;
    private MemberRepository memberRepository;


    // 커밋된 후에 작업
    // 게시글 댓글 이벤트
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void postCommentEvernt(PostCommentEvent postCommentEvent) {

        Member receiver = postCommentEvent.getReceiver();
        Member sender = postCommentEvent.getSender();
        Comment comment = postCommentEvent.getComment();
        NotificationType notificationType = postCommentEvent.getNotificationType();

        if (!receiver.getNickname().equals(sender.getNickname())) {
            commentNotification(receiver, sender, comment, notificationType);
        }
    }

    // 게시글 좋아요 이벤트
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void postLikeEvent(PostLikeEvent postLikeEvent) {

        Member receiver = postLikeEvent.getReceiver();
        Member sender = postLikeEvent.getSender();
        Post post = postLikeEvent.getPost();
        NotificationType notificationType = NotificationType.postLike;

        if(!receiver.getNickname().equals(sender.getNickname())) {
            postLikeNotification(receiver, sender, post, notificationType);
        }
    }

    // 한줄평 좋아요 이벤트
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void OneLineReviewLikeEvent(OneLineReviewLikeEvent oneLineReviewLikeEvent) {

        Member receiver = oneLineReviewLikeEvent.getReceiver();
        Member sender = oneLineReviewLikeEvent.getSender();
        OneLineReview oneLineReview = oneLineReviewLikeEvent.getOneLineReview();
        NotificationType notificationType = NotificationType.oneLineReviewLike;

        if(!receiver.getNickname().equals(sender.getNickname())) {
            oneLineReviewNotification(receiver, sender, oneLineReview, notificationType);
        }
    }

//    // 배지 획득 이벤트
//    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
//    public void BadgeEvent(BadgeEvent badgeEvent) {
//        Member findMember = badgeEvent.getReceiver();
//    }


    // 게시글 댓글
    private void commentNotification(Member receiver, Member sender, Comment object, NotificationType notificationType) {
        Notification saveNotification = notificationRepository
                .save(new Notification(receiver, sender.getNickname(), notificationType, object.getId(), object.getCommentContent()));
        notificationService.notificationByMessage(new NotificationResponseDto(saveNotification));
    }

    // 게시글 좋아요
    private void postLikeNotification(Member receiver, Member sender, Post object, NotificationType notificationType) {
        Notification saveNotification = notificationRepository
                .save(new Notification(receiver, sender.getNickname(), notificationType, object.getId(), object.getPostTitle()));
        notificationService.notificationByMessage(new NotificationResponseDto(saveNotification));
    }

    // 한줄평 좋아요
    private void oneLineReviewNotification(Member receiver, Member sender, OneLineReview object, NotificationType notificationType) {
        Notification saveNotification = notificationRepository
                .save(new Notification(receiver, sender.getNickname(), notificationType, object.getId(), object.getOneLineReviewContent()));
        notificationService.notificationByMessage(new NotificationResponseDto(saveNotification));
    }






}
