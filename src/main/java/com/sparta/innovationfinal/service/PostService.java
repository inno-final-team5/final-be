package com.sparta.innovationfinal.service;

import com.sparta.innovationfinal.dto.responseDto.*;
import com.sparta.innovationfinal.entity.*;
import com.sparta.innovationfinal.repository.*;
import com.sparta.innovationfinal.dto.requestDto.PostRequestDto;
import com.sparta.innovationfinal.exception.ErrorCode;
import com.sparta.innovationfinal.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final BadgeRepository badgeRepository;
    private final MemberBadgeRepository memberBadgeRepository;
    private final CommentRepository commentRepository;
    private final SubCommentRepository subCommentRepository;
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
            return ResponseDto.fail(ErrorCode.INVALID_TITLE);
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

        // 작성한 게시글 수 5개 이상일 시 배지 부여(1번배지)
        List<Post> findPost = postRepository.findPostByMember(member);
        Badge badge = badgeRepository.findBadgeByBadgeName("커뮤니티 인싸");
        MemberBadge findMemberBadge = memberBadgeRepository.findMemberBadgeByMemberAndBadge(member, badge);
        if (findPost.size() > 4 && findMemberBadge == null) {
            // 맴버배지 테이블에 저장
            MemberBadge memberBadge = MemberBadge.builder()
                    .member(member)
                    .badge(badge)
                    .build();

            memberBadgeRepository.save(memberBadge);

        }

        return ResponseDto.success(
                PostResponseDto.builder()
                        .postId(post.getId())
                        .nickname(post.getMember().getNickname())
                        .badgeId(post.getMember().getMainBadge())
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
//        Post post = postRepository.
//        List<Comment> commentList = commentRepository.findAllByPost(postList);
        List<AllPostResponseDto> allPostResponseDtos = new ArrayList<>();
        for (Post post : postList) {
            allPostResponseDtos.add(
                    AllPostResponseDto.builder()
                            .postId(post.getId())
                            .nickname(post.getMember().getNickname())
                            .badgeId(post.getMember().getMainBadge())
                            .postTitle(post.getPostTitle())
                            .postCategory(post.getPostCategory())
                            .commentNum(post.getCommentList().size())
                            .likeNum(post.getLikeNum())
                            .createdAt(String.valueOf(post.getCreatedAt()))
                            .build()
            );
        }
        return ResponseDto.success(allPostResponseDtos);
    }

    // 커뮤니티게시판 영화 카테고리 전체 조회
    @Transactional
    public ResponseDto<?> getAllMoviePost() {
        List<Post> postList = postRepository.findPostByPostCategoryOrderByCreatedAtDesc("영화");
        List<AllPostResponseDto> allPostResponseDtos = new ArrayList<>();
        for (Post post : postList) {
            allPostResponseDtos.add(
                    AllPostResponseDto.builder()
                            .postId(post.getId())
                            .nickname(post.getMember().getNickname())
                            .badgeId(post.getMember().getMainBadge())
                            .postTitle(post.getPostTitle())
                            .postCategory(post.getPostCategory())
                            .commentNum(post.getCommentList().size())
                            .likeNum(post.getLikeNum())
                            .createdAt(String.valueOf(post.getCreatedAt()))
                            .build()
            );
        }
        return ResponseDto.success(allPostResponseDtos);
    }

    // 커뮤니티게시판 영화관 카테고리 전체 조회
    @Transactional
    public ResponseDto<?> getAllCinemasPost() {
        List<Post> postList = postRepository.findPostByPostCategoryOrderByCreatedAtDesc("영화관");
        List<AllPostResponseDto> allPostResponseDtos = new ArrayList<>();
        for (Post post : postList) {
            allPostResponseDtos.add(
                    AllPostResponseDto.builder()
                            .postId(post.getId())
                            .nickname(post.getMember().getNickname())
                            .badgeId(post.getMember().getMainBadge())
                            .postTitle(post.getPostTitle())
                            .postCategory(post.getPostCategory())
                            .commentNum(post.getCommentList().size())
                            .likeNum(post.getLikeNum())
                            .createdAt(String.valueOf(post.getCreatedAt()))
                            .build()
            );
        }
        return ResponseDto.success(allPostResponseDtos);
    }


    // 메인페이지 최신 게시글 10개 조회
    @Transactional
    public ResponseDto<?> getRecentPost() {
        List<Post> postList = postRepository.findTop5ByOrderByCreatedAtDesc();
        List<AllPostResponseDto> allPostResponseDtos = new ArrayList<>();
        for (Post post : postList) {
            allPostResponseDtos.add(
                    AllPostResponseDto.builder()
                            .postId(post.getId())
                            .nickname(post.getMember().getNickname())
                            .badgeId(post.getMember().getMainBadge())
                            .postTitle(post.getPostTitle())
                            .postCategory(post.getPostCategory())
                            .commentNum(post.getCommentList().size())
                            .likeNum(post.getLikeNum())
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

        // 댓글 리스트
        List<Comment> commentList = commentRepository.findAllByPost(post);
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();

        for (Comment comment : commentList) {
            List<SubComment> subCommentList = subCommentRepository.findAllByComment(comment);
            List<SubCommentResponseDto> subCommentResponseDtoList = new ArrayList<>();

            // 댓글 안에 대댓글 리스트
            for (SubComment subComment : subCommentList) {
                subCommentResponseDtoList.add(
                        SubCommentResponseDto.builder()
                                .subCommentId(subComment.getId())
                                .subCommentContent(subComment.getSubCommentContent())
                                .nickname(subComment.getMember().getNickname())
                                .badgeId(subComment.getMember().getMainBadge())
                                .createdAt(String.valueOf(subComment.getCreatedAt()))
                                .modifiedAt(String.valueOf(subComment.getModifiedAt()))
                                .build()
                );
            }

            commentResponseDtoList.add(
                    CommentResponseDto.builder()
                            .commentId(comment.getId())
                            .nickname(comment.getMember().getNickname())
                            .badgeId(comment.getMember().getMainBadge())
                            .commentContent(comment.getCommentContent())
                            .subCommentResponseDtoList(subCommentResponseDtoList)
                            .createdAt(String.valueOf(comment.getCreatedAt()))
                            .modifiedAt(String.valueOf(comment.getModifiedAt()))
                            .build()
            );
        }


        return ResponseDto.success(
                PostResponseDto.builder()
                        .postId(post.getId())
                        .nickname(post.getMember().getNickname())
                        .badgeId(post.getMember().getMainBadge())
                        .postTitle(post.getPostTitle())
                        .postCategory(post.getPostCategory())
                        .postContent(post.getPostContent())
                        .LikeNum(post.getLikeNum())
                        .commentResponseDtoList(commentResponseDtoList)
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
            return ResponseDto.fail(ErrorCode.INVALID_TITLE);
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
                        .badgeId(post.getMember().getMainBadge())
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

    // 마이페이지 나의게시글 조회
    public ResponseDto<?> getMyPost(HttpServletRequest request) {
        Member member = validateMember(request);
        List<PostResponseDto> responseDtoList = new ArrayList<>();
        List<Post> posts = postRepository.findPostByMemberOrderByCreatedAtDesc(member);

        for (Post post : posts) {
            responseDtoList.add(PostResponseDto.builder()
                    .postId(post.getId())
                    .nickname(post.getMember().getNickname())
                    .badgeId(post.getMember().getMainBadge())
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

      public ResponseDto<?> PostSearch(String type, String keyword) {
          if (type.equals("postTitle")) {
              List<Post> postList = postRepository.findByPostTitleContaining(keyword);
              List<AllPostResponseDto> allPostResponseDtos = new ArrayList<>();
              for (Post post : postList) {
                  allPostResponseDtos.add(
                          AllPostResponseDto.builder()
                                  .postId(post.getId())
                                  .nickname(post.getMember().getNickname())
                                  .badgeId(post.getMember().getMainBadge())
                                  .postTitle(post.getPostTitle())
                                  .postContent(post.getPostContent())
                                  .postCategory(post.getPostCategory())
                                  .commentNum(post.getCommentList().size())
                                  .likeNum(post.getLikeNum())
                                  .createdAt(String.valueOf(post.getCreatedAt()))
                                  .build());
              } return ResponseDto.success(allPostResponseDtos);
          } else if (type.equals("postContent")) {
              List<Post> postList = postRepository.findByPostContentContaining(keyword);
              List<AllPostResponseDto> allPostResponseDtos = new ArrayList<>();
              for (Post post : postList) {
                  allPostResponseDtos.add(
                          AllPostResponseDto.builder()
                                  .postId(post.getId())
                                  .nickname(post.getMember().getNickname())
                                  .badgeId(post.getMember().getMainBadge())
                                  .postTitle(post.getPostTitle())
                                  .postContent(post.getPostContent())
                                  .postCategory(post.getPostCategory())
                                  .commentNum(post.getCommentList().size())
                                  .likeNum(post.getLikeNum())
                                  .createdAt(String.valueOf(post.getCreatedAt()))
                                  .build());
              } return ResponseDto.success(allPostResponseDtos);
          } else {
              return ResponseDto.fail(ErrorCode.BAD_RERUEST);
          }
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
