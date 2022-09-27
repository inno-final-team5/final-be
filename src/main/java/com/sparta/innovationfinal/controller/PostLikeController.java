package com.sparta.innovationfinal.controller;

import com.sparta.innovationfinal.dto.responseDto.ResponseDto;
import com.sparta.innovationfinal.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping(value = "/api/auth/post/like/{id}")
    public ResponseDto<?> postLike(@PathVariable Long id, HttpServletRequest request) {
        return postLikeService.pushPostLike(id, request);
    }

    @DeleteMapping(value = "/api/auth/post/like/{id}")
    public ResponseDto<?> postLikeCancel(@PathVariable Long id, HttpServletRequest request) {
        return postLikeService.postLikeCancel(id, request);
    }

}
