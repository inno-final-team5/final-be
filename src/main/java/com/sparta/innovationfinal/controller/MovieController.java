package com.sparta.innovationfinal.controller;

import com.sparta.innovationfinal.dto.responseDto.*;
import com.sparta.innovationfinal.dto.MovieGenre;
import com.sparta.innovationfinal.service.MovieSearchApiService;

import com.sparta.innovationfinal.service.MovieService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Api(tags = {"영화 API"})
public class MovieController {

    private final String successMsg = "성공";
    private final String failMsg = "실패";
    private final MovieService movieService;

    MovieSearchApiService movieSearchApiService = new MovieSearchApiService();

    // 전체 영화 조회
    @GetMapping("/movie/{pageNum}")
    public MovieResponseDto allMovie(@PathVariable int pageNum) throws Exception {

        MovieAllResponseDto movieAllResponseDto = movieSearchApiService.movieAllSearch(pageNum);

        return new MovieResponseDto(200L, successMsg, movieAllResponseDto);
    }
    // 상세 조회
    @GetMapping("/movie/detail/{movieId}")
    public MovieResponseDto detailMovie(@PathVariable int movieId) throws Exception {

        MovieDetailResponseDto movieDetailResponseDto = movieSearchApiService.movieDetailSearch(movieId);

        return new MovieResponseDto(200L, successMsg, movieDetailResponseDto);
    }

    // 장르 조회
    @GetMapping(value = {"/main/search/{genre}/{pageNum}"})
    public MovieResponseDto genreMovie(@PathVariable MovieGenre genre, @PathVariable int pageNum) throws Exception {

        MovieGenreResponseDto movieGenreResponseDto = movieSearchApiService.MovieGenreSearch(genre, pageNum);

        return new MovieResponseDto(200L, successMsg, movieGenreResponseDto);
    }

    // 제목 검색 조회
    @GetMapping("/main/search/title/{movieTitle}/{pageNum}")
    public MovieResponseDto titleSearchMovie(@PathVariable String movieTitle, @PathVariable int pageNum) throws Exception {

        MovieTitleSearchResponseDto movieTitleSearchResponseDto = movieSearchApiService.MovieTitleSearch(movieTitle, pageNum);

        return new MovieResponseDto(200L, successMsg, movieTitleSearchResponseDto);
    }

    // 제목 검색 조회
    @GetMapping("/main/bestfavorite")
    public ResponseDto<?> getBestFavorite() {
        return movieService.getBestFavorite();
    }

}