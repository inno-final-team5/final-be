package com.sparta.innovationfinal.controller;

import com.sparta.innovationfinal.dto.requestDto.PostLikeRequestDto;
import com.sparta.innovationfinal.dto.responseDto.ResponseDto;
import com.sparta.innovationfinal.service.PostLikeService;
import com.sparta.innovationfinal.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping(value = "/api/auth/post/like/{id}")
    public ResponseDto<?> likePost(@PathVariable Long id, HttpServletRequest request) {
        return postLikeService.pushPostLike(id, request);
    }

}
