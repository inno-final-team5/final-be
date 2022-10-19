package com.sparta.innovationfinal.controller;

import com.sparta.innovationfinal.dto.requestDto.CommentRequestDto;
import com.sparta.innovationfinal.dto.responseDto.ResponseDto;
import com.sparta.innovationfinal.service.CommentService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@Api(tags = {"댓글 CRUD API"})
public class CommentController {
    private final CommentService commentService;

    @PostMapping(value = "/auth/post/{postId}/comment")
    public ResponseDto<?> creatComment(@PathVariable Long postId, @RequestBody CommentRequestDto requestDto,
                                       HttpServletRequest request) {
        return commentService.creatComment(postId, requestDto, request);
    }

    @PutMapping(value = "/auth/post/comment/{commentId}")
    public ResponseDto<?> updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto requestDto,
                                        HttpServletRequest request) {
        return commentService.updateComment(commentId, requestDto, request);
    }

    @DeleteMapping(value = "/auth/post/comment/{commentId}")
    public ResponseDto<?> deleteComment(@PathVariable Long commentId, HttpServletRequest request) {
        return commentService.deleteComment(commentId, request);
    }

}
