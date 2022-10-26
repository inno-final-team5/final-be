package com.sparta.innovationfinal.controller;

import com.sparta.innovationfinal.dto.requestDto.NicknameCheckDto;
import com.sparta.innovationfinal.dto.requestDto.OneLineReviewRequestDto;
import com.sparta.innovationfinal.dto.responseDto.ResponseDto;
import com.sparta.innovationfinal.service.OneLineReviewService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@Api(tags = {"한줄평 CRUD API"})
public class OneLineReviewController {
    private final OneLineReviewService oneLineReviewService;

    @PostMapping(value = "/auth/movie/one-line-review")
    public ResponseDto<?> createReview( @RequestBody OneLineReviewRequestDto requestDto, HttpServletRequest request) throws Exception {
        return oneLineReviewService.createReview(requestDto,request);
    }

    @DeleteMapping(value ={ "/auth/movie/{Id}"})
    public ResponseDto<?> deleteReview(@PathVariable Long Id, HttpServletRequest request){
        return oneLineReviewService.deleteReview(Id,request);
    }

    @PutMapping(value = {"/auth/movie/{Id}"})
    public ResponseDto<?> updateReview(@PathVariable Long Id,@RequestBody OneLineReviewRequestDto requestDto, HttpServletRequest request){
        return oneLineReviewService.updateReview(Id,requestDto,request);
    }

    @GetMapping(value = "/movie/{movieId}/one-line-review")
    public ResponseDto<?> getAllReview(@PathVariable int movieId){
        return oneLineReviewService.getAllReview((long)movieId);
    }

    @GetMapping(value = "/auth/movie/one-line-review")
    public ResponseDto<?> getMyReview(HttpServletRequest request){
        return oneLineReviewService.getMyReview(request);
    }

    @GetMapping(value = "/one-line-review/{nickname}")
    public ResponseDto<?> getAllReviewByNickname(@PathVariable NicknameCheckDto nickname) {
        return oneLineReviewService.getAllReviewByNickname(nickname);
    }

    @GetMapping(value = "/main/best")
    public ResponseDto<?> getBestReview(){
        return oneLineReviewService.getBestReview();
    }
}

