package com.sparta.innovationfinal.websocket;

import com.sparta.innovationfinal.entity.Comment;
import com.sparta.innovationfinal.entity.Member;
import com.sparta.innovationfinal.entity.Post;
import com.sparta.innovationfinal.repository.CommentRepository;
import com.sparta.innovationfinal.repository.OneLineReviewLikeRepository;
import com.sparta.innovationfinal.repository.PostRepository;
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


//    // 커밋된 후에 작업
//    // 게시글 댓글 이벤트
//    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
//    public void postCommentEvernt(PostCommentEvent postCommentEvent) {
//
//        Member receiver = postCommentEvent.getReceiver();
//        Member sender = postCommentEvent.getSender();
//        Object object = postCommentEvent.getObject();
//        NotificationType notificationType = postCommentEvent.getNotificationType();
//
//        if(!receiver.getNickname().equals(sender.getNickname())) {
//            if (notificationType.equals(NotificationType.postComment)) {
//
//            }
//
//            if (!receiver.getNickName().equals(sender.getNickName())) {
//                if (alarmType.equals(AlarmType.comment)) {
//                    answerAlarm(receiver, sender, (Answer) object, alarmType);
//                } else if (alarmType.equals(AlarmType.child)) {
//                    Comment comment = (Comment) object;
//                    Long parentCommentId = comment.getParentComment().getId();
//                    Optional<Comment> parent = commentRepository.findById(parentCommentId);
//                    Comment comment1 = parent.get();
//
//                    commentAlarm(receiver, sender, comment1, alarmType);
//                }
//            }
//            User findUser = ComfortMethods.getUser(sender.getId());
//            if (findUser.getAchievement().getAchievement6() == 0) {
//                findUser.getAchievement().setAchievement6(1);
//                achieveAlarm(findUser, "리액션HAMA");
//            }
//
//        }
//
//    }

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

    // 게시글 댓글
    private void commentAlarm(Member receiver, Member sender, Comment object, NotificationType notificationType) {
        Notification saveNotification = notificationRepository
                .save(new Notification(receiver, sender.getNickname(), notificationType, object.getId(), object.getCommentContent()));
//        notificationService.notificationByMessage(new NotificationResponseDto(saveNotification));     // 레디스 있어야함
    }

    // 게시글 좋아요
    private void postLikeNotification(Member receiver, Member sender, Post object, NotificationType notificationType) {
        Notification saveNotification = notificationRepository
                .save(new Notification(receiver, sender.getNickname(), notificationType, object.getId(), object.getPostTitle()));
//        NotificationService.alarmByMessage(new NotificationResponseDto(saveNotification));
    }






}
