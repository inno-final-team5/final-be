package com.sparta.innovationfinal.service;

import com.sparta.innovationfinal.dto.requestDto.FavoriteRequestDto;
import com.sparta.innovationfinal.dto.responseDto.AllFavoriteResponseDto;
import com.sparta.innovationfinal.dto.responseDto.ResponseDto;
import com.sparta.innovationfinal.entity.Favorite;
import com.sparta.innovationfinal.entity.Member;
import com.sparta.innovationfinal.exception.ErrorCode;
import com.sparta.innovationfinal.jwt.TokenProvider;
import com.sparta.innovationfinal.movieApi.entity.Movie;
import com.sparta.innovationfinal.movieApi.repository.MovieRepository;
import com.sparta.innovationfinal.repository.FavoriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class FavoriteService{

    private final TokenProvider tokenProvider;
    private final FavoriteRepository favoriteRepository;
    private final MovieRepository movieRepository;

    // 즐겨찾기 추가
    @Transactional
    public ResponseDto<?> checkFavorite(FavoriteRequestDto favoriteRequestDto, HttpServletRequest request) {

        if (null == request.getHeader(("Refresh-Token"))) {
            return ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND);
        }

        if (null == request.getHeader(("Authorization"))) {
            return ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND);
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail(ErrorCode.INVALD_MEMBER);
        }

//        try{
//            String txt = favoriteRequestDto.getTitle();
//            System.out.println(URLDecoder.decode(txt, "UTF-8"));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

        // 해당 영화 없음
        Movie findMovie = movieRepository.findMovieByMovieId(favoriteRequestDto.getMovieId());
        if (findMovie == null) {
//            return ResponseDto.fail(ErrorCode.INVAILD_MOVIE);
//        } else {
            Movie movie1 = Movie.builder()
                    .movieId(favoriteRequestDto.getMovieId())
                    .title(favoriteRequestDto.getTitle())
                    .posterPath(favoriteRequestDto.getPosterPath())
                    .build();

            movieRepository.save(movie1);
        }

        Movie movie = movieRepository.findMovieByMovieIdAndTitle(favoriteRequestDto.getMovieId(), favoriteRequestDto.getTitle());
        Favorite findFavorite = favoriteRepository.findMovieByMemberAndMovie(member, movie);
        System.out.println("findFavorite = " + findFavorite);
        if(findFavorite != null) {
            return ResponseDto.fail(ErrorCode.DUPLICATE_FAVORITE_MOVIE);    //
        } else {
            Favorite favorite = Favorite.builder()
                    .member(member)
                    .movie(movie)
                    .build();

            favoriteRepository.save(favorite);
        }

        return ResponseDto.success("favorite success");
    }

    // 즐겨찾기 삭제
    @Transactional
    public ResponseDto<?> deleteFavorite(Long id, HttpServletRequest request) {

        if (null == request.getHeader(("Refresh-Token"))) {
            return ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND);
        }

        if (null == request.getHeader(("Authorization"))) {
            return ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND);
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail(ErrorCode.INVALD_MEMBER);
        }

        // 해당 영화 없음
        Favorite favorite = favoriteRepository.findFavoriteById(id);
        if (favorite == null) {
            return ResponseDto.fail(ErrorCode.INVAILD_MOVIE);
//        }
//
//        Favorite findFavorite = favoriteRepository.findFavoriteByMemberAndMovie(member, movie);
//        if(findFavorite == null) {
//            return ResponseDto.fail(ErrorCode.NON_FAVORITE_MOVIE);
        } else {
            favoriteRepository.delete(favorite);
        }
        return ResponseDto.success("delete success");
    }

    // 즐겨찾기 전체조회
    @Transactional
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


    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }

}
