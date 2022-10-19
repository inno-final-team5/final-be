package com.sparta.innovationfinal.entity;

import com.sparta.innovationfinal.dto.requestDto.CommentRequestDto;
import com.sparta.innovationfinal.dto.requestDto.SubCommentModifyRequestDto;
import com.sparta.innovationfinal.dto.requestDto.SubCommentRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubComment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String subCommentContent;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "post_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @JoinColumn(name = "comment_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comment;

    public void update(SubCommentModifyRequestDto requestDto) {
        this.subCommentContent = requestDto.getSubCommentContent();
    }

}
