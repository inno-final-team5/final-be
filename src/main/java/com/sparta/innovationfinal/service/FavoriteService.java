package com.sparta.innovationfinal.service;

import com.sparta.innovationfinal.entity.Badge;
import com.sparta.innovationfinal.repository.BadgeRepository;
import com.sparta.innovationfinal.entity.MemberBadge;
import com.sparta.innovationfinal.repository.MemberBadgeRepository;
import com.sparta.innovationfinal.dto.requestDto.FavoriteRequestDto;
import com.sparta.innovationfinal.dto.responseDto.AllFavoriteResponseDto;
import com.sparta.innovationfinal.dto.responseDto.ResponseDto;
import com.sparta.innovationfinal.entity.Favorite;
import com.sparta.innovationfinal.entity.Member;
import com.sparta.innovationfinal.entity.Movie;
import com.sparta.innovationfinal.dto.ErrorCode;
import com.sparta.innovationfinal.config.jwt.TokenProvider;
import com.sparta.innovationfinal.repository.MovieRepository;
import com.sparta.innovationfinal.repository.FavoriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class FavoriteService{
    private final TokenProvider tokenProvider;
    private final FavoriteRepository favoriteRepository;
    private final MovieRepository movieRepository;
    private final BadgeRepository badgeRepository;
    private final MemberBadgeRepository memberBadgeRepository;

    private static final int MIN_BADGE_SIZE = 4;

    // 즐겨찾기 추가
    public ResponseDto<?> checkFavorite(FavoriteRequestDto favoriteRequestDto, HttpServletRequest request) {
        if (null == request.getHeader(("Refresh-Token"))) {
            return ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND);
        }
        if (null == request.getHeader(("Authorization"))) {
            return ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND);
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail(ErrorCode.INVALID_MEMBER);
        }

        // 해당 영화 없음
        Movie findMovie = movieRepository.findMovieByMovieId(favoriteRequestDto.getMovieId());
        if (findMovie == null) {
            Movie saveMovie = Movie.builder()
                    .movieId(favoriteRequestDto.getMovieId())
                    .title(favoriteRequestDto.getTitle())
                    .posterPath(favoriteRequestDto.getPosterPath())
                    .build();

            movieRepository.save(saveMovie);
        }

        Movie movie = movieRepository.findMovieByMovieIdAndTitle(favoriteRequestDto.getMovieId(), favoriteRequestDto.getTitle());
        Favorite findFavorite = favoriteRepository.findMovieByMemberAndMovie(member, movie);
        System.out.println("findFavorite = " + findFavorite);
        if(findFavorite != null) {
            return ResponseDto.fail(ErrorCode.DUPLICATE_FAVORITE_MOVIE);
        } else {
            Favorite favorite = Favorite.builder()
                    .member(member)
                    .movie(movie)
                    .build();

            favoriteRepository.save(favorite);

            Movie updateMovie = movieRepository.findMovieById(movie.getId());
            List<Favorite> favorites = favoriteRepository.findAllByMovie(movie);

            updateMovie.update(favorites.size());
        }

        // 즐겨찾기 한 영화 5개 이상일 시 배지 부여(5번배지)
        List<Favorite> findFavoriteByMember = favoriteRepository.findFavoriteByMember(member);
        Badge badge = badgeRepository.findBadgeByBadgeName("영화 수집가");
        MemberBadge findMemberBadge = memberBadgeRepository.findMemberBadgeByMemberAndBadge(member, badge);

        if (findFavoriteByMember.size() > MIN_BADGE_SIZE && findMemberBadge == null) {
            // 맴버배지 테이블에 저장
            MemberBadge memberBadge = MemberBadge.builder()
                    .member(member)
                    .badge(badge)
                    .build();

            memberBadgeRepository.save(memberBadge);

        }

        return ResponseDto.success("favorite success");
    }

    // 즐겨찾기 삭제
    public ResponseDto<?> deleteFavorite(Long id, HttpServletRequest request) {

        if (null == request.getHeader(("Refresh-Token"))) {
            return ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND);
        }
        if (null == request.getHeader(("Authorization"))) {
            return ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND);
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail(ErrorCode.INVALID_MEMBER);
        }

        // 해당 즐겨찾기 없음
        Favorite favorite = favoriteRepository.findFavoriteById(id);
        if (favorite == null) {
            return ResponseDto.fail(ErrorCode.INVALID_MOVIE);
        }
//        if (!favorite.getMember().validateMember(member)) {
//            return ResponseDto.fail(ErrorCode.NOT_AUTHOR);
//        }
        favoriteRepository.delete(favorite);

        //즐겨찾기 수 카운트 --
        List<Favorite> favorites = favoriteRepository.findAllByMovie(favorite.getMovie());

        favorite.getMovie().update(favorites.size());
        return ResponseDto.success("delete success");
    }

    // 즐겨찾기 전체조회
    public ResponseDto<?> getAllFavorite(HttpServletRequest request) {
        Member member = validateMember(request);
        List<Favorite> favoriteList = favoriteRepository.findFavoriteByMemberOrderByCreatedAtDesc(member);
        List<AllFavoriteResponseDto> allFavoriteResponseDtos = new ArrayList<>();
        for (Favorite favorite : favoriteList) {
            allFavoriteResponseDtos.add(
                    AllFavoriteResponseDto.builder()
                            .Id(favorite.getId())
                            .movieId(favorite.getMovie().getMovieId())
                            .posterPath(favorite.getMovie().getPosterPath())
                            .title(favorite.getMovie().getTitle())
                            .build()
            );
        }
        return ResponseDto.success(allFavoriteResponseDtos);
    }

    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }

}
