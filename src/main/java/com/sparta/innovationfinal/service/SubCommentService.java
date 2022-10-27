package com.sparta.innovationfinal.service;

import com.sparta.innovationfinal.dto.requestDto.SubCommentModifyRequestDto;
import com.sparta.innovationfinal.dto.requestDto.SubCommentRequestDto;
import com.sparta.innovationfinal.dto.responseDto.ResponseDto;
import com.sparta.innovationfinal.dto.responseDto.SubCommentResponseDto;
import com.sparta.innovationfinal.entity.Comment;
import com.sparta.innovationfinal.entity.Member;
import com.sparta.innovationfinal.entity.Post;
import com.sparta.innovationfinal.entity.SubComment;
import com.sparta.innovationfinal.dto.ErrorCode;
import com.sparta.innovationfinal.config.jwt.TokenProvider;
import com.sparta.innovationfinal.repository.CommentRepository;
import com.sparta.innovationfinal.repository.PostRepository;
import com.sparta.innovationfinal.repository.SubCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class SubCommentService {
    private final PostRepository postRepository;
    private final TokenProvider tokenProvider;
    private final CommentRepository commentRepository;
    private final SubCommentRepository subCommentRepository;

    //대댓글작성
    @Transactional
    public ResponseDto<?> creatSubComment(SubCommentRequestDto requestDto, HttpServletRequest request) {
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND);
        }
        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND);
        }
        // 내용 미입력 예외처리
        if (null == requestDto.getSubCommentContent()) {
            return ResponseDto.fail(ErrorCode.INVALID_CONTENT);
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail(ErrorCode.INVALID_TOKEN);
        }

        // 게시글 없음 예외처리
        Post post = postRepository.findPostById(requestDto.getPostId());
        if (post == null) {
            return ResponseDto.fail(ErrorCode.INVALID_POST);
        }

        // 댓글 없음 예외처리
        Comment comment = commentRepository.findCommentById(requestDto.getCommentId());
        if (comment == null) {
            return ResponseDto.fail(ErrorCode.INVALID_COMMENT);
        }

        SubComment subComment = SubComment.builder()
                .post(post)
                .comment(comment)
                .member(member)
                .subCommentContent(requestDto.getSubCommentContent())
                .build();

        subCommentRepository.save(subComment);

        return ResponseDto.success(
                SubCommentResponseDto.builder()
                .subCommentId(subComment.getId())
                .subCommentContent(subComment.getSubCommentContent())
                .nickname(subComment.getMember().getNickname())
                .badgeId(subComment.getMember().getMainBadge())
                .createdAt(String.valueOf(subComment.getCreatedAt()))
                .modifiedAt(String.valueOf(subComment.getModifiedAt()))
                .build()
        );

    }

    // 댓글 수정
    @Transactional
    public ResponseDto<?> updateSubComment(Long id, SubCommentModifyRequestDto requestDto, HttpServletRequest request) {
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND);
        }
        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND);
        }
        // 내용 미입력 예외처리
        if (null == requestDto.getSubCommentContent()) {
            return ResponseDto.fail(ErrorCode.INVALID_CONTENT);
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail(ErrorCode.INVALID_TOKEN);
        }

        // 대댓글 없음 예외처리
        SubComment subComment = subCommentRepository.findSubCommentById(id);
        if (subComment == null) {
            return ResponseDto.fail(ErrorCode.INVALID_SUBCOMMENT);
        }
        if (!subComment.getMember().validateMember(member)) {
            return ResponseDto.fail(ErrorCode.NOT_AUTHOR);
        }

        subComment.update(requestDto);

        return ResponseDto.success(
                SubCommentResponseDto.builder()
                        .subCommentId(subComment.getId())
                        .subCommentContent(subComment.getSubCommentContent())
                        .nickname(subComment.getMember().getNickname())
                        .badgeId(subComment.getMember().getMainBadge())
                        .createdAt(String.valueOf(subComment.getCreatedAt()))
                        .modifiedAt(String.valueOf(subComment.getModifiedAt()))
                        .build()
        );
    }

    // 대댓글 삭제
    @Transactional
    public ResponseDto<?> deleteSubComment(Long id, HttpServletRequest request) {
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

        // 대댓글 없음 예외처리
        SubComment subComment = subCommentRepository.findSubCommentById(id);
        if (subComment == null) {
            return ResponseDto.fail(ErrorCode.INVALID_SUBCOMMENT);
        }
        if (!subComment.getMember().validateMember(member)) {
            return ResponseDto.fail(ErrorCode.NOT_AUTHOR);
        }

        subCommentRepository.delete(subComment);
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
