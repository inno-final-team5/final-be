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
    private final MemberRepository memberRepository;
    private final PostLikeRepository postLikeRepository;
    private final TokenProvider tokenProvider;
    private final PostRepository postRepository;

    // 게시글 좋아요
    @Transactional
    public ResponseDto<?> pushPostLike(Long id, HttpServletRequest request) {

        // 로그인 예외처리
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

        // 해당하는 게시글 id가 없을 시 오류코드 반환
        Post post = postRepository.findPostById(id);
        if (post == null) {
            return ResponseDto.fail(ErrorCode.INVALID_POST);
        }

        // 이미 좋아요를 눌렀다면 오류코드 반환
        PostLike findPostLike = postLikeRepository.findPostByMemberAndPost(member, post);
        if (findPostLike != null) {
            return ResponseDto.fail(ErrorCode.DUPLICATE_LIKE);
        } else {
            PostLike postLike= PostLike.builder()
                    .member(member)
                    .post(post)
                    .build();

            postLikeRepository.save(postLike);

            // 해당 게시글의 좋아요 수도 업데이트
            List<PostLike> posts = postLikeRepository.findAllByPost(post);
            post.setLikeNum(posts.size());

        }

        return ResponseDto.success("like success");

    }

    @Transactional
    public ResponseDto<?> postLikeCancle(Long id, HttpServletRequest request) {

        // 로그인 예외처리
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


        // 해당하는 게시글 id가 없을 시 오류코드 반환
        Post post = postRepository.findPostById(id);
        if (post == null) {
            return ResponseDto.fail(ErrorCode.INVALID_POST);
        }

        // 좋아요 누른사람과 취소하려는 유저가 다름
//        PostLike postLike = postLikeRepository.findPostLikeByIdAndMember(id, member);
//        if (!postLike.getMember().validateMember(member)) {
//            return ResponseDto.fail(ErrorCode.NOT_AUTHOR);
//        }

        // 해당게시글에 해당 유저가 좋아요를 누르지 않았다면 오류코드 반환
        PostLike findPostLike = postLikeRepository.findPostLikeByMemberAndPost(member, post);
        if (findPostLike == null) {
            return ResponseDto.fail(ErrorCode.DUPLICATE_LIKE);
        } else {
//            PostLike postLike1 = PostLike.builder()
//                    .member(member)
//                    .post(post)
//                    .build();

            postLikeRepository.delete(findPostLike);

            // 해당 게시글의 좋아요 수도 업데이트
            List<PostLike> posts = postLikeRepository.findAllByPost(post);
            post.setLikeNum(posts.size());
        }
        return ResponseDto.success("success delete");
    }


    
//    @Transactional(readOnly = true)
//    public Post isPresentPost(Long id) {
//        Optional<Post> optionalPost = postRepository.findById(id);
//        return optionalPost.orElse(null);
//    }
//
//    // 게시물에 해당 사용자가 좋아요를 눌렀는지 확인
//    @Transactional(readOnly = true)
//    public PostLike isPresentPostlike(Post post, Member member) {
////        Optional<PostLike> optionalPostLike = postLikeRepository.findById(id);
//         return postLikeRepository.findByMemberAndPost(member, post);
//    }
//
//    // 게시물의 좋아요 개수 반환
//    public int getLikeCount(Post post) {
//        List<PostLike> likeList = postLikeRepository.findAllByPost(post);
//        return likeList.size();
//    }

    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }

}
