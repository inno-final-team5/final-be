package com.sparta.innovationfinal.service;

import com.sparta.innovationfinal.dto.requestDto.PostRequestDto;
import com.sparta.innovationfinal.dto.responseDto.AllPostResponseDto;
import com.sparta.innovationfinal.dto.responseDto.PostResponseDto;
import com.sparta.innovationfinal.dto.responseDto.ResponseDto;
import com.sparta.innovationfinal.entity.Member;
import com.sparta.innovationfinal.entity.Post;
import com.sparta.innovationfinal.exception.ErrorCode;
import com.sparta.innovationfinal.jwt.TokenProvider;
import com.sparta.innovationfinal.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final TokenProvider tokenProvider;

    // 게시글 생성
    @Transactional
    public ResponseDto<?> createPost(PostRequestDto requestDto, HttpServletRequest request) {
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND);
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND);
        }

        if (null == requestDto.getPostCategory()) {
            return ResponseDto.fail(ErrorCode.INVALID_CATEGORY);
        }

        if (null == requestDto.getPostContent()) {
            return ResponseDto.fail(ErrorCode.INVALID_CONTENT);
        }

        if (null == requestDto.getPostTitle()) {
            return ResponseDto.fail(ErrorCode.INVALD_TITLE);
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail(ErrorCode.INVALID_TOKEN);
        }

        Post post = Post.builder()
                .member(member)
                .postTitle(requestDto.getPostTitle())
                .postContent(requestDto.getPostContent())
                .postCategory(requestDto.getPostCategory())
                // 좋아요 개수 추가
                .build();
        postRepository.save(post);
        return ResponseDto.success(
                PostResponseDto.builder()
                        .postId(post.getId())
                        .nickname(post.getMember().getNickname())
                        .postTitle(post.getPostTitle())
                        .postContent(post.getPostContent())
                        .postCategory(post.getPostCategory())
                        .createdAt(String.valueOf(post.getCreatedAt()))
                        .modifiedAt(String.valueOf(post.getModifiedAt()))
                        // 좋아요 개수 추가
                        .build()
        );
    }

    // 게시글 전체 조회
    @Transactional
    public ResponseDto<?> getAllPost() {
        List<Post> postList = postRepository.findAllByOrderByCreatedAtDesc();
        List<AllPostResponseDto> allPostResponseDtos = new ArrayList<>();
        for (Post post : postList) {
            allPostResponseDtos.add(
                    AllPostResponseDto.builder()
                            .postId(post.getId())
                            .nickname(post.getMember().getNickname())
                            .postTitle(post.getPostTitle())
                            .postCategory(post.getPostCategory())
                            .createdAt(String.valueOf(post.getCreatedAt()))
                            .build()
            );
        }
        return ResponseDto.success(allPostResponseDtos);
    }

    // 최신 게시글 10개 조회
    @Transactional
    public ResponseDto<?> getRecentPost() {
        List<Post> postList = postRepository.findTop10ByOrderByCreatedAtDesc();
        List<AllPostResponseDto> allPostResponseDtos = new ArrayList<>();
        for (Post post : postList) {
            allPostResponseDtos.add(
                    AllPostResponseDto.builder()
                            .postId(post.getId())
                            .nickname(post.getMember().getNickname())
                            .postTitle(post.getPostTitle())
                            .postCategory(post.getPostCategory())
                            .createdAt(String.valueOf(post.getCreatedAt()))
                            .build()
            );
        }
        return ResponseDto.success(allPostResponseDtos);
    }

    // 게시글 개별 조회
    @Transactional(readOnly = true)
    public ResponseDto<?> getPost(Long id) {
        Post post = isPresentPost(id);
        if (null == post) {
            return ResponseDto.fail(ErrorCode.INVALID_POST);
        }
        return ResponseDto.success(
                PostResponseDto.builder()
                        .postId(post.getId())
                        .nickname(post.getMember().getNickname())
                        .postTitle(post.getPostTitle())
                        .postCategory(post.getPostCategory())
                        .postContent(post.getPostContent())
                        .LikeNum(post.getLikeNum())
                        .createdAt(String.valueOf(post.getCreatedAt()))
                        .modifiedAt(String.valueOf(post.getModifiedAt()))
                        .build()
                );
    }

    // 게시글 수정
    @Transactional
    public ResponseDto<?> updatePost(Long id, PostRequestDto requestDto, HttpServletRequest request) {
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND);
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND);
        }

        if (null == requestDto.getPostCategory()) {
            return ResponseDto.fail(ErrorCode.INVALID_CATEGORY);
        }

        if (null == requestDto.getPostContent()) {
            return ResponseDto.fail(ErrorCode.INVALID_CONTENT);
        }

        if (null == requestDto.getPostTitle()) {
            return ResponseDto.fail(ErrorCode.INVALD_TITLE);
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail(ErrorCode.INVALID_TOKEN);
        }

        Post post = isPresentPost(id);
        if (null == post) {
            return ResponseDto.fail(ErrorCode.INVALID_POST);
        }
        if (!post.getMember().validateMember(member)) {
            return ResponseDto.fail(ErrorCode.NOT_AUTHOR);
        }
        post.update(requestDto);
        return ResponseDto.success(
                PostResponseDto.builder()
                        .postId(post.getId())
                        .nickname(post.getMember().getNickname())
                        .postTitle(post.getPostTitle())
                        .postCategory(post.getPostCategory())
                        .postContent(post.getPostContent())
                        .LikeNum(post.getLikeNum())
                        .createdAt(String.valueOf(post.getCreatedAt()))
                        .modifiedAt(String.valueOf(post.getModifiedAt()))
                        .build());
    }

    // 게시글 삭제
    public ResponseDto<?> deletePost(Long id, HttpServletRequest request) {
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND);
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND);
        }
        Post post = isPresentPost(id);
        if (null == post) {
            return ResponseDto.fail(ErrorCode.INVALID_POST);
        }
        Member member = validateMember(request);
        if (!post.getMember().validateMember(member)) {
            return ResponseDto.fail(ErrorCode.NOT_AUTHOR);
        }
        postRepository.delete(post);
        return ResponseDto.success("success delete");

    }

    public ResponseDto<?> getMyPost(HttpServletRequest request) {
        Member member = validateMember(request);
        List<PostResponseDto> responseDtoList = new ArrayList<>();
        List<Post> posts = postRepository.findPostByMember(member);

        for (Post post : posts) {
            responseDtoList.add(PostResponseDto.builder()
                    .postId(post.getId())
                    .nickname(post.getMember().getNickname())
                    .postTitle(post.getPostTitle())
                    .postCategory(post.getPostCategory())
                    .postContent(post.getPostContent())
                    .LikeNum(post.getLikeNum())
                    .createdAt(String.valueOf(post.getCreatedAt()))
                    .modifiedAt(String.valueOf(post.getModifiedAt()))
                    .build());
        }
        return ResponseDto.success(responseDtoList);
    }

    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }

    @Transactional(readOnly = true)
    public Post isPresentPost(Long id){
        Optional<Post> optionalPost = postRepository.findById(id);
        return optionalPost.orElse(null);
    }
}
