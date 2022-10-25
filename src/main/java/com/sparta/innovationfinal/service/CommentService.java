package com.sparta.innovationfinal.service;

import com.sparta.innovationfinal.dto.requestDto.CommentRequestDto;
import com.sparta.innovationfinal.dto.responseDto.CommentResponseDto;
import com.sparta.innovationfinal.dto.responseDto.ResponseDto;
import com.sparta.innovationfinal.entity.Comment;
import com.sparta.innovationfinal.entity.Member;
import com.sparta.innovationfinal.entity.Post;
import com.sparta.innovationfinal.exception.ErrorCode;
import com.sparta.innovationfinal.jwt.TokenProvider;
import com.sparta.innovationfinal.repository.CommentRepository;
import com.sparta.innovationfinal.repository.PostRepository;
import com.sparta.innovationfinal.websocket.NotificationType;
import com.sparta.innovationfinal.websocket.event.PostCommentEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final PostRepository postRepository;
    private final TokenProvider tokenProvider;
    private final CommentRepository commentRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    // 댓글 작성
    @Transactional
    public ResponseDto<?> creatComment(Long id, CommentRequestDto requestDto, HttpServletRequest request) {
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND);
        }
        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND);
        }
        // 내용 미입력 예외처리
        if (null == requestDto.getCommentContent()) {
            return ResponseDto.fail(ErrorCode.INVALID_CONTENT);
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail(ErrorCode.INVALID_TOKEN);
        }

        // 게시글 없음 예외처리
        Post post = postRepository.findPostById(id);
        if (post == null) {
            return ResponseDto.fail(ErrorCode.INVALID_POST);
        }

        Comment comment = Comment.builder()
                .post(post)
                .member(member)
                .commentContent(requestDto.getCommentContent())
                .build();

        commentRepository.save(comment);
        applicationEventPublisher.publishEvent(new PostCommentEvent(post.getMember(), member, comment, NotificationType.postComment));

        return ResponseDto.success(
                CommentResponseDto.builder()
                        .commentId(comment.getId())
                        .commentContent(comment.getCommentContent())
                        .nickname(comment.getMember().getNickname())
                        .badgeId(comment.getMember().getMainBadge())
                        .createdAt(String.valueOf(comment.getCreatedAt()))
                        .modifiedAt(String.valueOf(comment.getModifiedAt()))
                        .build()
        );

    }

    //댓글 수정
    @Transactional
    public ResponseDto<?> updateComment(Long id, CommentRequestDto requestDto, HttpServletRequest request) {
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND);
        }
        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND);
        }
        // 내용 미입력 예외처리
        if (null == requestDto.getCommentContent()) {
            return ResponseDto.fail(ErrorCode.INVALID_CONTENT);
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail(ErrorCode.INVALID_TOKEN);
        }

        // 댓글 없음 예외처리
        Comment comment = commentRepository.findCommentById(id);
        if (comment == null) {
            return ResponseDto.fail(ErrorCode.INVALID_COMMENT);
        }
        if (!comment.getMember().validateMember(member)) {
            return ResponseDto.fail(ErrorCode.NOT_AUTHOR);
        }

        comment.update(requestDto);

        return ResponseDto.success(
                CommentResponseDto.builder()
                        .commentId(comment.getId())
                        .commentContent(comment.getCommentContent())
                        .nickname(comment.getMember().getNickname())
                        .badgeId(comment.getMember().getMainBadge())
                        .createdAt(String.valueOf(comment.getCreatedAt()))
                        .modifiedAt(String.valueOf(comment.getModifiedAt()))
                        .build()
        );

    }

    // 댓글 삭제
    @Transactional
    public ResponseDto<?> deleteComment(Long id, HttpServletRequest request) {
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND);
        }
        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND);
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail(ErrorCode.INVALID_TOKEN);
        }

        Comment comment = commentRepository.findCommentById(id);
        if (comment == null) {
            return ResponseDto.fail(ErrorCode.INVALID_COMMENT);
        }
        if (!comment.getMember().validateMember(member)) {
            return ResponseDto.fail(ErrorCode.NOT_AUTHOR);
        }
        commentRepository.delete(comment);
        return ResponseDto.success("success delete");

    }

    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }
}
