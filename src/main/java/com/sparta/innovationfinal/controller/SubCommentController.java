package com.sparta.innovationfinal.controller;

import com.sparta.innovationfinal.dto.requestDto.SubCommentModifyRequestDto;
import com.sparta.innovationfinal.dto.requestDto.SubCommentRequestDto;
import com.sparta.innovationfinal.dto.responseDto.ResponseDto;
import com.sparta.innovationfinal.service.SubCommentService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@Api(tags = {"대댓글 CRUD API"})
public class SubCommentController {
    private final SubCommentService subCommentService;

    @PostMapping(value = "/auth/post/subComment")
    public ResponseDto<?> creatSubComment(@RequestBody SubCommentRequestDto requestDto, HttpServletRequest request) {
        return subCommentService.creatSubComment(requestDto, request);
    }

    @PutMapping(value = "/auth/post/subComment/{subCommentId}")
    public ResponseDto<?> updateSubComment(@PathVariable Long subCommentId, @RequestBody SubCommentModifyRequestDto requestDto,
                                           HttpServletRequest request) {
        return subCommentService.updateSubComment(subCommentId, requestDto, request);
    }

    @DeleteMapping(value = "/auth/post/subComment/{subCommentId}")
    public ResponseDto<?> deleteSubComment(@PathVariable Long subCommentId, HttpServletRequest request) {
        return subCommentService.deleteSubComment(subCommentId, request);
    }

}
