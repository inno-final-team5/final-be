package com.sparta.innovationfinal.controller;

import com.sparta.innovationfinal.dto.requestDto.FavoriteRequestDto;
import com.sparta.innovationfinal.dto.responseDto.ResponseDto;
import com.sparta.innovationfinal.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping(value = "/auth/movie/favorite")
    public ResponseDto<?> favoritePush(@RequestBody FavoriteRequestDto favoriteRequestDto, HttpServletRequest request) {

        return favoriteService.checkFavorite(favoriteRequestDto, request);

    }

    @DeleteMapping(value = "/auth/movie/favorite/{id}")
    public ResponseDto<?> favoriteDelete(@PathVariable Long id, HttpServletRequest request) {
        return favoriteService.deleteFavorite(id, request);
    }

}
