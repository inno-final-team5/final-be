package com.sparta.innovationfinal.service;

import com.sparta.innovationfinal.dto.requestDto.PostLikeRequestDto;
import com.sparta.innovationfinal.dto.responseDto.ResponseDto;
import com.sparta.innovationfinal.entity.Member;
import com.sparta.innovationfinal.entity.Post;
import com.sparta.innovationfinal.entity.PostLike;
import com.sparta.innovationfinal.exception.ErrorCode;
import com.sparta.innovationfinal.jwt.TokenProvider;
import com.sparta.innovationfinal.repository.MemberRepository;
import com.sparta.innovationfinal.repository.PostLikeRepository;
import com.sparta.innovationfinal.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostLikeService {
    private MemberRepository memberRepository;
    private PostLikeRepository postLikeRepository;
    private TokenProvider tokenProvider;
    private PostRepository postRepository;

    // 게시글 좋아요
    @Transactional
    public ResponseDto<?> pushPostLike(PostLikeRequestDto requestDto, HttpServletRequest request) {


        if (null == request.getHeader(("Refresh-Token"))) {
            return ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND);
        }

        if (null == request.getHeader(("Authorization"))) {
            return ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND);
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail(ErrorCode.INVALID_TOKEN);
        }

        Post post = postRepository.findPostById(requestDto.getPostId());
        if (post == null) {
            return ResponseDto.fail(ErrorCode.INVALID_POST);
        }

        PostLike findPost = postLikeRepository.findByPostIdAndMemberId(member,post);

        if (findPost != null) {
            return ResponseDto.fail(ErrorCode.DUPLICATE_LIKE);
        } else {
            PostLike postLike = new PostLike(post, member);
            postLikeRepository.save(postLike);

        } return ResponseDto.success("success post");
//        Post post = isPresentPost(postId);
//        if (null == post) {
//            return ResponseDto.fail(ErrorCode.INVALID_POST);
//        }
//
//        PostLike postLikes = postLikeRepository.findByPostIdAndMemberId(post.getId(), member.getId());
//        if (postLikes == null) {
//            post.pushLike();
//            PostLike postLike = PostLike.builder()
//                    .member(member)
//                    .post(post)
//                    .build();
//            postLikeRepository.save(postLike);
//
//            return ResponseDto.success("success post");
//        } else {
//            postLikeRepository.deleteById(postLikes.getId());
//        }
//
//        return ResponseDto.success("success post");
    }



    
    @Transactional(readOnly = true)
    public Post isPresentPost(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        return optionalPost.orElse(null);
    }

    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }

}
