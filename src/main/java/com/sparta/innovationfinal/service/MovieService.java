package com.sparta.innovationfinal.service;

import com.sparta.innovationfinal.dto.responseDto.ResponseDto;
import com.sparta.innovationfinal.entity.Movie;
import com.sparta.innovationfinal.dto.responseDto.MovieFavoriteRankingDto;
import com.sparta.innovationfinal.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;

    // 메인페이지 - 즐겨찾기 순 베스트 영화 10개 조회
    public ResponseDto<?> getBestFavorite() {
        List<Movie> movieList = movieRepository.findTop10ByOrderByFavoriteNumDesc();
        List<MovieFavoriteRankingDto> movieFavoriteRankingDto = new ArrayList<>();
        for (Movie movie : movieList){
            movieFavoriteRankingDto.add(
                    MovieFavoriteRankingDto.builder()
                            .ranking(movieFavoriteRankingDto.size()+1)
                            .movieId(movie.getMovieId())
                            .id(movie.getId())
                            .poster_path(movie.getPosterPath())
                            .title(movie.getTitle())
                            .favoriteNum(movie.getFavoriteNum())
                            .build()
            );
        }
        return ResponseDto.success(movieFavoriteRankingDto);
    }

}
