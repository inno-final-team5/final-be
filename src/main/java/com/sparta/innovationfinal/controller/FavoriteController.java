package com.sparta.innovationfinal.controller;

import com.sparta.innovationfinal.dto.requestDto.FavoriteRequestDto;
import com.sparta.innovationfinal.dto.responseDto.ResponseDto;
import com.sparta.innovationfinal.service.FavoriteService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@Api(tags = {"즐겨찾기 API"})
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

    @GetMapping(value = "/auth/movie/favorites")
    public ResponseDto<?> favoriteGetAll(HttpServletRequest request) {
        return favoriteService.getAllFavorite(request);
    }

}
