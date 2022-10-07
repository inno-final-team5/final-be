package com.sparta.innovationfinal.service;

import com.sparta.innovationfinal.dto.responseDto.OneLineReviewLikeResponseDto;
import com.sparta.innovationfinal.dto.responseDto.ResponseDto;
import com.sparta.innovationfinal.entity.Member;
import com.sparta.innovationfinal.entity.Movie;
import com.sparta.innovationfinal.entity.OneLineReview;
import com.sparta.innovationfinal.entity.OneLineReviewLike;
import com.sparta.innovationfinal.exception.ErrorCode;
import com.sparta.innovationfinal.jwt.TokenProvider;
import com.sparta.innovationfinal.repository.MovieRepository;
import com.sparta.innovationfinal.repository.OneLineReviewLikeRepository;
import com.sparta.innovationfinal.repository.OneLineReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class OneLineReviewLikeService {
    private final OneLineReviewLikeRepository oneLineReviewLikeRepository;
    private final TokenProvider tokenProvider;
    private final OneLineReviewRepository oneLineReviewRepository;
    private final MovieRepository movieRepository;

    @Transactional
    // 1.한줄평 좋아요
    public ResponseDto<?> reviewLike(Long id, HttpServletRequest request) {
    // 로그인 예외처리
        if(null == request.getHeader(("Refresh-Token"))) {
            return ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND);
        }

        if (null == request.getHeader(("Authorization"))) {
            return ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND);
        }

        Member member = validateMember(request);
         // 예외처리(유저를 찾을 수 없는 경우)
        if (member == null){
            return ResponseDto.fail(ErrorCode.INVALID_MEMBER);
        }
//        // 예외처리(영화가 삭제되었을 경우)
//        Movie movie = movieRepository.findMovieById(id);
//        if (movie == null){
//            return ResponseDto.fail(ErrorCode.INVAILD_MOVIE);
//        }
        // 예외처리(한줄평이 없을 경우)
        OneLineReview oneLineReview = oneLineReviewRepository.findOneLineReviewById(id);
        if (oneLineReview == null){
            return ResponseDto.fail(ErrorCode.INVALID_REVIEW);
        }

        // 좋아요를 이미 눌렀을 경우 오류 코드 반환 -> 안눌렀을 경우 좋아요 저장
        OneLineReviewLike findReviewLike = oneLineReviewLikeRepository.findOneLineReviewByMemberAndOneLineReview(member, oneLineReview);
        if (findReviewLike != null) {
            return ResponseDto.fail(ErrorCode.DUPLICATE_LIKE);

        } else {
            OneLineReviewLike oneLineReviewLike = OneLineReviewLike.builder()
                    .member(member)
                    .oneLineReview(oneLineReview)
                    .build();
            oneLineReviewLikeRepository.save(oneLineReviewLike);

            //좋아요 수 카운트 ++
            List<OneLineReviewLike> oneLineReviewLikes = oneLineReviewLikeRepository.findAllByOneLineReview(oneLineReview);
            oneLineReview.setLikeNum(oneLineReviewLikes.size());
        }
        return ResponseDto.success(OneLineReviewLikeResponseDto.builder()
                .oneLineReviewId(oneLineReview.getId())
                .oneLineReviewContent(oneLineReview.getOneLineReviewContent())
                .build());
    }
    @Transactional
    // 2.한줄평 좋아요 취소
    public ResponseDto<?> reviewLikeCancel(Long id, HttpServletRequest request) {
        // 로그인 예외처리
        if (null == request.getHeader(("Refresh-Token"))) {
            return ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND);
        }

        if (null == request.getHeader(("Authorization"))) {
            return ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND);
        }
        Member member = validateMember(request);
        // 예외처리(유저를 찾을 수 없는 경우)
        if (member == null){
            return ResponseDto.fail(ErrorCode.INVALID_MEMBER);
        }
        // 예외처리(영화가 삭제되었을 경우)
        Movie movie = movieRepository.findMovieById(id);
//        if (movie.getMovieId() == 0) {
//            return ResponseDto.fail(ErrorCode.INVALID_MOVIE);
//        }
        // 예외처리(한줄평이 없을 경우)
        OneLineReview oneLineReview = oneLineReviewRepository.findOneLineReviewById(id);
//        if (oneLineReview == null){
//            return ResponseDto.fail(ErrorCode.INVALID_REVIEW);
//        }


        // 좋아요를 누르지 않았을 경우 오류 코드 반환 -> 눌렀을 경우 좋아요 삭제
        OneLineReviewLike findReviewLike = oneLineReviewLikeRepository.findOneLineReviewLikeByMemberAndOneLineReview(member, oneLineReview);
        if (findReviewLike == null) {
            return ResponseDto.fail(ErrorCode.INVALID_LIKE);
        } else {
            oneLineReviewLikeRepository.delete(findReviewLike);

            //좋아요 수 카운트 --
            List<OneLineReviewLike> oneLineReviewLikes = oneLineReviewLikeRepository.findAllByOneLineReview(oneLineReview);
            oneLineReview.setLikeNum(oneLineReviewLikes.size());
        }
        return ResponseDto.success("success delete");
        }

        // 나의 한줄평좋아요 전체 조회
        @Transactional
        public ResponseDto<?> getAllReviewLike(HttpServletRequest request) {

            Member member = validateMember(request);

            List<OneLineReviewLikeResponseDto> responseDtoList = new ArrayList<>();
            List<OneLineReviewLike> oneLineReviewLikes = oneLineReviewLikeRepository.findOneLineReviewLikeByMember(member);

            for (OneLineReviewLike oneLineReviewLike : oneLineReviewLikes) {
                responseDtoList.add(OneLineReviewLikeResponseDto.builder()
                        .oneLineReviewId(oneLineReviewLike.getOneLineReview().getId())
                        .oneLineReviewContent(oneLineReviewLike.getOneLineReview().getOneLineReviewContent())
                        .build());
            }
            return ResponseDto.success(responseDtoList);
        }

    @Transactional
    public Member validateMember(HttpServletRequest request){
        if(!tokenProvider.validateToken(request.getHeader("Refresh-Token"))){
    return null;
        }return tokenProvider.getMemberFromAuthentication() ;
    }

}


