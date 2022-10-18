package com.sparta.innovationfinal.service;

import com.sparta.innovationfinal.dto.responseDto.ResponseDto;
import com.sparta.innovationfinal.entity.Movie;
import com.sparta.innovationfinal.movieApi.dto.MovieFavoriteRankingDto;
import com.sparta.innovationfinal.movieApi.dto.MovieResultResponseDto;
import com.sparta.innovationfinal.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieServiece {

    private final MovieRepository movieRepository;


    @Transactional
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
