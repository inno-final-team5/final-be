package com.sparta.innovationfinal.service;

import com.sparta.innovationfinal.dto.responseDto.ResponseDto;
import com.sparta.innovationfinal.entity.Movie;
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
//        List<Movie> movieList = movieRepository.findTop10ByOrderByFavoriteNumDese();
        List<Movie> movieList = movieRepository.findTop10ByOrderByFavoriteNumDesc();
        List<MovieResultResponseDto> movieResultResponseDtos = new ArrayList<>();
        for (Movie movie : movieList){
            movieResultResponseDtos.add(
                    MovieResultResponseDto.builder()
                            .movieId(movie.getMovieId())
                            .id(movie.getId())
                            .poster_path(movie.getPosterPath())
                            .title(movie.getTitle())
                            .favoriteNum(movie.getFavoriteNum())
                            .build()
            );
        }
        return ResponseDto.success(movieResultResponseDtos);
    }

}
