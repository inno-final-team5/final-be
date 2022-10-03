package com.sparta.innovationfinal.controller;

import com.sparta.innovationfinal.dto.requestDto.FavoriteRequestDto;
import com.sparta.innovationfinal.dto.responseDto.ResponseDto;
import com.sparta.innovationfinal.movieApi.MovieSearchApi;
import com.sparta.innovationfinal.movieApi.dto.MovieDetailResponseDto;
import com.sparta.innovationfinal.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

//    @PostMapping(value = "/auth/movie/favorite")
//    public ResponseDto<?> favoritePush(@RequestBody FavoriteRequestDto favoriteRequestDto, HttpServletRequest request) {
//
//        return favoriteService.checkFavorite(favoriteRequestDto, request);

    }

//}
