package com.sparta.innovationfinal.movieApi.service;

import com.sparta.innovationfinal.movieApi.dto.MovieGenreDto;
import org.springframework.stereotype.Service;

@Service
public interface MovieService {

    public MovieGenreDto findmoviegenre();
}
