package com.sparta.innovationfinal.websocket;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sparta.innovationfinal.entity.Member;
import com.sparta.innovationfinal.entity.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Notification extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long notificationId;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private Member receiver;

    @Column(name = "sender_nickName")
    private String senderNickName;

    @Column
    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @Column
    private Long id;

    @Column
    private  String title;

    @Enumerated(EnumType.STRING)
    private ReadingStatus readingStatus;

    public Notification(Member receiver, String senderNickName, NotificationType notificationType, Long id, String title) {

        this.receiver = receiver;
        this.senderNickName = senderNickName;
        this.notificationType = notificationType;
        this.id = id;
        this.title = title;
        this.readingStatus = readingStatus.N;
    }

    public void changReadingStatus(ReadingStatus readingStatus) {

        this.readingStatus = readingStatus;
    }

    public LocalDateTime modifieadAt() {

        return  null;
    }



}
