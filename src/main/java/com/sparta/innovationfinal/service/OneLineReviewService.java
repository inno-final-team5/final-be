package com.sparta.innovationfinal.service;

import com.sparta.innovationfinal.badge.Badge;
import com.sparta.innovationfinal.badge.BadgeRepository;
import com.sparta.innovationfinal.badge.MemberBadge;
import com.sparta.innovationfinal.badge.MemberBadgeRepository;
import com.sparta.innovationfinal.dto.requestDto.OneLineReviewRequestDto;
import com.sparta.innovationfinal.dto.responseDto.AllOneLineReviewResponseDto;
import com.sparta.innovationfinal.dto.responseDto.OneLineReviewResponseDto;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OneLineReviewService {
    private final OneLineReviewRepository oneLineReviewRepository;
    private final OneLineReviewLikeRepository oneLineReviewLikeRepository;
    private final MovieRepository movieRepository;
    private final MemberBadgeRepository memberBadgeRepository;
    private final BadgeRepository badgeRepository;
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
        // 해당영화 없음
        Movie findMovie = movieRepository.findMovieByMovieId(requestDto.getMovieId());
        if (findMovie == null) {
            Movie movie = Movie.builder()
                    .movieId(requestDto.getMovieId())
                    .title(requestDto.getTitle())
                    .posterPath(requestDto.getPosterPath())
                    .build();
            movieRepository.save(movie);
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail(ErrorCode.INVALID_TOKEN);
        }
        // 한줄평 작성 로직
//        if (requestDto.getMovieId() == null) {
//            return ResponseDto.fail(ErrorCode.INVALID_REVIEW);//movie Id가 없으면 상세정보가 없는 것이니 해당 한줄평 없음으로 변경
//        }
        Movie movie = movieRepository.findMovieByMovieIdAndTitle(requestDto.getMovieId(), requestDto.getTitle());
        OneLineReview findOneLineReview = oneLineReviewRepository.findOneLineReviewByMemberAndMovie(member, movie);
        OneLineReview oneLineReview;
        if (findOneLineReview != null) {
            return ResponseDto.fail(ErrorCode.DUPLICATE_REVIEW);
        } else {
            oneLineReview = OneLineReview.builder()
                    .member(member)
                    .movie(movie)
                    .oneLineReviewStar(requestDto.getOneLineReviewStar())
                    .oneLineReviewContent(requestDto.getOneLineReviewContent())
                    .build();

            oneLineReviewRepository.save(oneLineReview);
        }

        // 별점 5점 준 영화 5개일 시 배지 부여(6번 배지)
        List<OneLineReview> findReviewByFiveStar = oneLineReviewRepository.findOneLineReviewByMemberAndOneLineReviewStar(member, 5);
        Badge badge = badgeRepository.findBadgeByBadgeName("후한 평론가");
        MemberBadge findMemberBadge = memberBadgeRepository.findMemberBadgeByMemberAndBadge(member, badge);
        if (findReviewByFiveStar.size() > 4 && findMemberBadge == null) {
            // 맴버배지 테이블에 저장
            MemberBadge memberBadge = MemberBadge.builder()
                    .member(member)
                    .badge(badge)
                    .build();

            memberBadgeRepository.save(memberBadge);

        }

        // 별점 1점 준 영화 5개일 시 배지 부여(7번 배지)
        List<OneLineReview> findReviewByOneStar = oneLineReviewRepository.findOneLineReviewByMemberAndOneLineReviewStar(member, 1);
        Badge badge1 = badgeRepository.findBadgeByBadgeName("야박한 평론가");
        MemberBadge findMemberBadge1 = memberBadgeRepository.findMemberBadgeByMemberAndBadge(member, badge1);
        if (findReviewByOneStar.size() > 4 && findMemberBadge1 == null) {
            // 맴버배지 테이블에 저장
            MemberBadge memberBadge1 = MemberBadge.builder()
                    .member(member)
                    .badge(badge1)
                    .build();

            memberBadgeRepository.save(memberBadge1);

        }

        // 작성한 한줄평 수 5개 이상일 시 배지 부여(2번배지)
        List<OneLineReview> findReview = oneLineReviewRepository.findOneLineReviewByMember(member);
        Badge badge2 = badgeRepository.findBadgeByBadgeName("어엿한 평론가");
        MemberBadge findMemberBadge2 = memberBadgeRepository.findMemberBadgeByMemberAndBadge(member, badge2);
        if (findReview.size() > 4 && findMemberBadge2 == null) {
            // 맴버배지 테이블에 저장
            MemberBadge memberBadge2 = MemberBadge.builder()
                    .member(member)
                    .badge(badge2)
                    .build();

            memberBadgeRepository.save(memberBadge2);

        }

        return ResponseDto.success(OneLineReviewResponseDto.builder()
                .movieId(oneLineReview.getMovie().getMovieId())
                .title(oneLineReview.getMovie().getTitle())
                .posterPath(oneLineReview.getMovie().getPosterPath())
                .nickname(oneLineReview.getMember().getNickname())
                .badgeId(oneLineReview.getMember().getMainBadge())
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
    public ResponseDto<?> deleteReview(Long id, HttpServletRequest request){
        OneLineReview oneLineReview = isPresentOneLineReview(id);
        if(oneLineReview == null){
            return ResponseDto.fail(ErrorCode.INVALID_REVIEW);
        }

        // 예외처리(작성자가 아닌 경우)
        Member member = validateMember(request);
        if (!oneLineReview.getMember().validateMember(member)) {
            return ResponseDto.fail(ErrorCode.NOT_AUTHOR);
        }

        // 한줄평 삭제 로직
        // 한줄평에 딸린 좋아요먼저 삭제
        List<OneLineReviewLike> findOneLineReviewLike = oneLineReviewLikeRepository.findOneLineReviewLikeByOneLineReviewId(id);
        oneLineReviewLikeRepository.deleteAll(findOneLineReviewLike);
        oneLineReviewRepository.delete(oneLineReview);
        return ResponseDto.success("success delete");
    }

    @Transactional
    // 3.한줄평 수정
    public ResponseDto<?> updateReview(Long movieId, OneLineReviewRequestDto requestDto, HttpServletRequest request){
        // 예외처리(한줄평)
        if (movieId == 0){
            return ResponseDto.fail(ErrorCode.INVALID_MOVIE);
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
                .badgeId(oneLineReview.getMember().getMainBadge())
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
            return ResponseDto.fail(ErrorCode.INVALID_MOVIE);
        }
        // 한줄평 전체 조회 로직
        List<OneLineReview> oneLineReviewList = oneLineReviewRepository.findAllByMovie_MovieIdOrderByCreatedAtDesc(movieId);
        List<AllOneLineReviewResponseDto> allOneLineReviewResponseDtos = new ArrayList<>();
        for(OneLineReview oneLineReview : oneLineReviewList){
            allOneLineReviewResponseDtos.add(AllOneLineReviewResponseDto.builder()
                    .movieId(oneLineReview.getMovie().getMovieId())
                    .title(oneLineReview.getMovie().getTitle())
                    .posterPath(oneLineReview.getMovie().getPosterPath())
                    .reviewId(oneLineReview.getId())
                    .nickname(oneLineReview.getMember().getNickname())
                    .badgeId(oneLineReview.getMember().getMainBadge())
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
                    .badgeId(oneLineReview.getMember().getMainBadge())
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
        List<OneLineReview> oneLineReviewList = oneLineReviewRepository.findTop5ByOrderByLikeNumDesc();
        List<AllOneLineReviewResponseDto> allOneLineReviewResponseDtos = new ArrayList<>();
        for (OneLineReview oneLineReview : oneLineReviewList) {
            allOneLineReviewResponseDtos.add(
                    AllOneLineReviewResponseDto.builder()
                            .movieId(oneLineReview.getMovie().getMovieId())
                            .title(oneLineReview.getMovie().getTitle())
                            .posterPath(oneLineReview.getMovie().getPosterPath())
                            .reviewId(oneLineReview.getId())
                            .nickname(oneLineReview.getMember().getNickname())
                            .badgeId(oneLineReview.getMember().getMainBadge())
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
