package com.sparta.innovationfinal.service;

import com.sparta.innovationfinal.entity.Badge;
import com.sparta.innovationfinal.repository.BadgeRepository;
import com.sparta.innovationfinal.entity.MemberBadge;
import com.sparta.innovationfinal.repository.MemberBadgeRepository;
import com.sparta.innovationfinal.dto.responseDto.ResponseDto;
import com.sparta.innovationfinal.entity.Member;
import com.sparta.innovationfinal.entity.Post;
import com.sparta.innovationfinal.entity.PostLike;
import com.sparta.innovationfinal.dto.ErrorCode;
import com.sparta.innovationfinal.config.jwt.TokenProvider;
import com.sparta.innovationfinal.repository.PostLikeRepository;
import com.sparta.innovationfinal.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;
    private final TokenProvider tokenProvider;
    private final PostRepository postRepository;
    private final BadgeRepository badgeRepository;
    private final MemberBadgeRepository memberBadgeRepository;

    private static final int MIN_BADGE_SIZE = 4;

    // 게시글 좋아요
    public ResponseDto<?> pushPostLike(Long id, HttpServletRequest request) {
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

        // 게시글 좋아요 수가 총 5개 이상일 시 배지 부여(4번배지)
        List<PostLike> findPostLikeByMember = postLikeRepository.findPostLikeByMember(member);
        Badge badge = badgeRepository.findBadgeByBadgeName("공감의 달인");
        MemberBadge findMemberBadge = memberBadgeRepository.findMemberBadgeByMemberAndBadge(member, badge);
        if (findPostLikeByMember.size() > MIN_BADGE_SIZE && findMemberBadge == null) {
            // 맴버배지 테이블에 저장
            MemberBadge memberBadge = MemberBadge.builder()
                    .member(member)
                    .badge(badge)
                    .build();

            memberBadgeRepository.save(memberBadge);

        }

        return ResponseDto.success("like success");

    }

    // 게시글 좋아요 취소
    public ResponseDto<?> postLikeCancel(Long id, HttpServletRequest request) {
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

        // 해당게시글에 해당 유저가 좋아요를 누르지 않았다면 오류코드 반환
        PostLike findPostLike = postLikeRepository.findPostLikeByMemberAndPost(member, post);
        if (findPostLike == null) {
            return ResponseDto.fail(ErrorCode.INVALID_LIKE);
        } else {
            postLikeRepository.delete(findPostLike);

            // 해당 게시글의 좋아요 수도 업데이트
            List<PostLike> posts = postLikeRepository.findAllByPost(post);
            post.setLikeNum(posts.size());
        }
        return ResponseDto.success("success delete");
    }

    // 게시글 좋아요 조회
    public ResponseDto<?> getPostLike(Long id, HttpServletRequest request) {
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
        Post post = postRepository.findPostById(id);
        if (post == null) {
            return ResponseDto.fail(ErrorCode.INVALID_POST);
        }

        PostLike findPostLike = postLikeRepository.findPostLikeByMemberAndPost(member, post);
        if (findPostLike == null) {
            return ResponseDto.success("false");
        }
        return ResponseDto.success("true");
    }


    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }

}
