package com.sparta.innovationfinal.controller;

import com.sparta.innovationfinal.dto.requestDto.PostRequestDto;
import com.sparta.innovationfinal.dto.responseDto.ResponseDto;
import com.sparta.innovationfinal.service.PostService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@Api(tags = {"게시물 CRUD API"})
public class PostController {
    private final PostService postService;

    @PostMapping(value = "/auth/post")
    public ResponseDto<?> creatPost(@RequestBody PostRequestDto requestDto,
                                    HttpServletRequest request) {
        return postService.createPost(requestDto, request);
    }

    @GetMapping(value = "/post/movies")
    public ResponseDto<?> getAllMoviePost() {
        return postService.getAllMoviePost();
    }

    @GetMapping(value = "/post/cinemas")
    public ResponseDto<?> getAllCinemasPost() {
        return postService.getAllCinemasPost();
    }

    @GetMapping(value = "/post/{id}")
    public ResponseDto<?> getPost(@PathVariable Long id){
        return postService.getPost(id);
    }

    @PutMapping(value = "/auth/post/{id}")
    public ResponseDto<?> updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto,
                                     HttpServletRequest request) {
        return postService.updatePost(id, requestDto, request);
    }

    @DeleteMapping(value = "/auth/post/{id}")
    public ResponseDto<?> deletePost(@PathVariable Long id, HttpServletRequest request) {
        return postService.deletePost(id, request);
    }

    @GetMapping(value = "/main/post")
    public ResponseDto<?> getRecentPost() {
        return postService.getRecentPost();
    }

    @GetMapping(value = "/auth/post")
    public ResponseDto<?> getMyPost(HttpServletRequest request) {
        return postService.getMyPost(request);
    }

    @GetMapping(value = "/post/search/{type}")
    public ResponseDto<?> PostSearch(@PathVariable String type, @RequestParam(value = "keyword", required = false) String keyword) {
        return postService.PostSearch(type, keyword);
    }
}
