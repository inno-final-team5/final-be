package com.sparta.innovationfinal.service;

import com.sparta.innovationfinal.dto.requestDto.OneLineReviewRequestDto;
import com.sparta.innovationfinal.dto.responseDto.AllOneLineReviewResponseDto;
import com.sparta.innovationfinal.dto.responseDto.OneLineReviewResponseDto;
import com.sparta.innovationfinal.dto.responseDto.ResponseDto;
import com.sparta.innovationfinal.entity.Member;
import com.sparta.innovationfinal.entity.Movie;
import com.sparta.innovationfinal.entity.OneLineReview;
import com.sparta.innovationfinal.exception.ErrorCode;
import com.sparta.innovationfinal.jwt.TokenProvider;
import com.sparta.innovationfinal.repository.MovieRepository;
import com.sparta.innovationfinal.repository.OneLineReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OneLineReviewService {
    private final OneLineReviewRepository oneLineReviewRepository;
    private final MovieRepository movieRepository;
    private final TokenProvider tokenProvider;
    @Transactional
    // 1.한줄평 작성 -- 한줄평 이중 작성 예외처리 필요
    public ResponseDto<?> createReview(OneLineReviewRequestDto requestDto, HttpServletRequest request) {
        // 로그인 예외처리
        if (null == request.getHeader(("Refresh-Token"))) {
            return ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND);
        }

        if (null == request.getHeader(("Authorization"))) {
            return ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND);
        }
        // 예외처리(한줄평)
        Movie movie = Movie.builder()
                .movieId(requestDto.getMovieId())
                .title(requestDto.getTitle())
                .posterPath(requestDto.getPosterPath())
                .build();
        movieRepository.save(movie);

        if (movie.getMovieId() == 0) {
            return ResponseDto.fail(ErrorCode.INVAILD_MOVIE);
        }

        if (requestDto.getOneLineReviewStar() == 0) {
            return ResponseDto.fail(ErrorCode.INVALID_STAR);
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail(ErrorCode.INVALID_TOKEN);
        }
        // 한줄평 작성 로직
        if(requestDto.getMovieId() == null){
            return ResponseDto.fail(ErrorCode.INVALID_REVIEW);//movie Id가 없으면 상세정보가 없는 것이니 해당 한줄평 없음으로 변경
        }
        OneLineReview oneLineReview = OneLineReview.builder()
                .member(member)
                .movie(movie)
                .oneLineReviewStar(requestDto.getOneLineReviewStar())
                .oneLineReviewContent(requestDto.getOneLineReviewContent())
                .build();

            oneLineReviewRepository.save(oneLineReview);
            return ResponseDto.success(OneLineReviewResponseDto.builder()
                    .movieId(oneLineReview.getMovie().getMovieId())
                    .title(oneLineReview.getMovie().getTitle())
                    .posterPath(oneLineReview.getMovie().getPosterPath())
                    .nickname(oneLineReview.getMember().getNickname())
                    .oneLineReviewId(oneLineReview.getId())
                    .oneLineReviewStar(oneLineReview.getOneLineReviewStar())
                    .oneLineReviewContent(oneLineReview.getOneLineReviewContent())
                    .createdAt(String.valueOf(oneLineReview.getCreatedAt()))
                    .modifiedAt(String.valueOf(oneLineReview.getModifiedAt()))
                    .build()
            );

        }

    @Transactional
    // 2.한줄평 삭제
    public ResponseDto<?> deleteReview(Long Id, HttpServletRequest request){
        OneLineReview oneLineReview = isPresentOneLineReview(Id);
        if(oneLineReview == null){
            return ResponseDto.fail(ErrorCode.INVALID_REVIEW);
        }

        // 예외처리(작성자가 아닌 경우)
        Member member = validateMember(request);
        if (!oneLineReview.getMember().validateMember(member)) {
            return ResponseDto.fail(ErrorCode.NOT_AUTHOR);
        }

        // 한줄평 삭제 로직
        oneLineReviewRepository.delete(oneLineReview);
        return ResponseDto.success("success delete");
    }

    @Transactional
    // 3.한줄평 수정
    public ResponseDto<?> updateReview(Long movieId, OneLineReviewRequestDto requestDto, HttpServletRequest request){
        // 예외처리(한줄평)
        if (movieId == 0){
            return ResponseDto.fail(ErrorCode.INVAILD_MOVIE);
        }
        if (requestDto.getOneLineReviewContent() == null){
            return ResponseDto.fail(ErrorCode.INVALID_REVIEW);
        }
        if (requestDto.getOneLineReviewStar() == 0){
            return ResponseDto.fail(ErrorCode.INVALID_STAR);
        }

        // 예외처리(작성자가 아닌 경우)
        Member member = validateMember(request);
        if (member == null) {
            return ResponseDto.fail(ErrorCode.NOT_AUTHOR);
        }
        OneLineReview oneLineReview = isPresentOneLineReview(movieId);
        if(!oneLineReview.getMember().validateMember(member)){
            return ResponseDto.fail(ErrorCode.NOT_AUTHOR);
        }

        // 한줄평 수정 로직
        oneLineReview.update(requestDto);
        return ResponseDto.success(OneLineReviewResponseDto.builder()
                .movieId(oneLineReview.getMovie().getMovieId())
                .title(oneLineReview.getMovie().getTitle())
                .posterPath(oneLineReview.getMovie().getPosterPath())
                .nickname(oneLineReview.getMember().getNickname())
                .oneLineReviewStar(oneLineReview.getOneLineReviewStar())
                .oneLineReviewContent(oneLineReview.getOneLineReviewContent())
                .createdAt(String.valueOf(oneLineReview.getCreatedAt()))
                .modifiedAt(String.valueOf(oneLineReview.getModifiedAt()))
                .build()
        );
    }

    // 4.한줄평 전체 조회
    public ResponseDto<?> getAllReview(Long movieId) {
        // 예외처리
        if (movieId == null){
            return ResponseDto.fail(ErrorCode.INVAILD_MOVIE);
        }
        // 한줄평 전체 조회 로직
        List<OneLineReview> oneLineReviewList = oneLineReviewRepository.findAllByOrderByCreatedAtDesc();
        List<AllOneLineReviewResponseDto> allOneLineReviewResponseDtos = new ArrayList<>();
        for(OneLineReview oneLineReview : oneLineReviewList){
            allOneLineReviewResponseDtos.add(AllOneLineReviewResponseDto.builder()
                    .movieId(oneLineReview.getMovie().getMovieId())
                    .title(oneLineReview.getMovie().getTitle())
                    .posterPath(oneLineReview.getMovie().getPosterPath())
                    .reviewId(oneLineReview.getId())
                    .nickname(oneLineReview.getMember().getNickname())
                    .oneLineReviewStar(oneLineReview.getOneLineReviewStar())
                    .likeNum(oneLineReview.getLikeNum())
                    .oneLineReviewContent(oneLineReview.getOneLineReviewContent())
                    .createdAt(String.valueOf(oneLineReview.getCreatedAt()))
                    .build()
            );
        }
        return ResponseDto.success(allOneLineReviewResponseDtos);
    }

    // 5.즐겨찾기 나의 한줄평 전체 조회
    public ResponseDto<?> getMyReview(HttpServletRequest request){
        Member member = validateMember(request);
        List<OneLineReviewResponseDto> oneLineReviewResponseDtoList = new ArrayList<>();
        List<OneLineReview> oneLineReviewList = oneLineReviewRepository.findOneLineReviewByMember(member);

        for(OneLineReview oneLineReview : oneLineReviewList){
            oneLineReviewResponseDtoList.add(OneLineReviewResponseDto.builder()
                    .movieId(oneLineReview.getMovie().getMovieId())
                    .title(oneLineReview.getMovie().getTitle())
                    .posterPath(oneLineReview.getMovie().getPosterPath())
                    .nickname(oneLineReview.getMember().getNickname())
                    .oneLineReviewId(oneLineReview.getId())
                    .oneLineReviewStar(oneLineReview.getOneLineReviewStar())
                    .oneLineReviewContent(oneLineReview.getOneLineReviewContent())
                    .build());

        }
        return ResponseDto.success(oneLineReviewResponseDtoList);

    }

    @Transactional
    // 6. 베스트 나의 한줄평 조회
    public ResponseDto<?> getBestReview() {
        List<OneLineReview> oneLineReviewList = oneLineReviewRepository.findTop10ByOrderByLikeNumDesc();
        List<AllOneLineReviewResponseDto> allOneLineReviewResponseDtos = new ArrayList<>();
        for (OneLineReview oneLineReview : oneLineReviewList) {
            allOneLineReviewResponseDtos.add(
                    AllOneLineReviewResponseDto.builder()
                            .movieId(oneLineReview.getMovie().getMovieId())
                            .title(oneLineReview.getMovie().getTitle())
                            .posterPath(oneLineReview.getMovie().getPosterPath())
                            .reviewId(oneLineReview.getId())
                            .nickname(oneLineReview.getMember().getNickname())
                            .oneLineReviewStar(oneLineReview.getOneLineReviewStar())
                            .likeNum(oneLineReview.getLikeNum())
                            .oneLineReviewContent(oneLineReview.getOneLineReviewContent())
                            .createdAt(String.valueOf(oneLineReview.getCreatedAt()))
                            .build()
            );
        }
        return ResponseDto.success(allOneLineReviewResponseDtos);
    }

    public Member validateMember(HttpServletRequest request){
        if(!tokenProvider.validateToken(request.getHeader("Refresh-Token"))){
        return null;
        }return tokenProvider.getMemberFromAuthentication() ;
    }

    public OneLineReview isPresentOneLineReview(long Id) {
        Optional<OneLineReview> oneLineReviewList = oneLineReviewRepository.findById(Id);
        return oneLineReviewList.orElse(null);
    }

}
