package com.sparta.innovationfinal.controller;

import com.sparta.innovationfinal.dto.responseDto.ResponseDto;
import com.sparta.innovationfinal.service.OneLineReviewLikeService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@Api(tags = {"한줄평 좋아요 API"})
public class OneLineReviewLikeController {
    private final OneLineReviewLikeService oneLineReviewLikeService;

    @PostMapping("/auth/movie/{id}/like")
    public ResponseDto<?> reviewLike(@PathVariable Long id, HttpServletRequest request){
        return oneLineReviewLikeService.reviewLike(id, request);
    }

    @DeleteMapping(value = "/auth/movie/{id}/like")
    public ResponseDto<?> reviewLikeCancel(@PathVariable Long id, HttpServletRequest request){
        return oneLineReviewLikeService.reviewLikeCancel(id,request);
    }

}
