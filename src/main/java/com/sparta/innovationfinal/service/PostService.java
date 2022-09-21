package com.sparta.innovationfinal.service;

import com.sparta.innovationfinal.dto.requestDto.PostRequestDto;
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

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final TokenProvider tokenProvider;

    @Transactional
    public ResponseDto<?> createPost(PostRequestDto requestDto, HttpServletRequest request) {
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
                        .memberId(post.getMember().getId())
                        .postTitle(post.getPostTitle())
                        .postContent(post.getPostContent())
                        .postCategory(post.getPostCategory())
                        .createdAt(String.valueOf(post.getCreatedAt()))
                        .modifiedAt(String.valueOf(post.getModifiedAt()))
                        // 좋아요 개수 추가
                        .build()
        );
    }


    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }
}
