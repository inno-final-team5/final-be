package com.sparta.innovationfinal.movieApi.controller;

import com.sparta.innovationfinal.movieApi.MovieGenre;
import com.sparta.innovationfinal.movieApi.MovieSearchApi;
import com.sparta.innovationfinal.movieApi.dto.*;
import com.sparta.innovationfinal.movieApi.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;
    private final String successMsg = "성공";
    private final String failMsg = "실패";

    MovieSearchApi movieSearchApi = new MovieSearchApi();

    // 전체 영화 조회
    @GetMapping("/api/movie/{pageNum}")
    public MovieResponseDto allMovie(@PathVariable int pageNum) throws Exception {

        MovieAllResponseDto movieAllResponseDto = movieSearchApi.movieAllSearch(pageNum);

        return new MovieResponseDto(200L, successMsg, movieAllResponseDto);
    }
    // 상세 조회
    @GetMapping("/api/movie/detail{movieId}")
    public MovieResponseDto detailMovie(@PathVariable int movieId) throws Exception {

        MovieDetailResponseDto movieDetailResponseDto = movieSearchApi.MovieDetailSearch(movieId);

        return new MovieResponseDto(200L, successMsg, movieDetailResponseDto);
    }

    // 장르 조회
    @GetMapping(value = {"/api/main/search/{genre}/{pageNum}"})
    public MovieResponseDto genreMovie(@PathVariable MovieGenre genre, @PathVariable int pageNum) throws Exception {

        MovieGenreResponseDto movieGenreResponseDto = movieSearchApi.MovieGenreSearch(genre, pageNum);

        return new MovieResponseDto(200L, successMsg, movieGenreResponseDto);
    }

    // 제목 검색 조회
    @GetMapping("/api/main/search/title/{movieTitle}/{pageNum}")
    public MovieResponseDto titleSearchMovie(@PathVariable String movieTitle, @PathVariable int pageNum) throws Exception {

        MovieTitleSearchResponseDto movieTitleSearchResponseDto = movieSearchApi.MovieTitleSearch(movieTitle, pageNum);

        return new MovieResponseDto(200L, successMsg, movieTitleSearchResponseDto);
    }

}