package com.sparta.innovationfinal.movieApi.service;

import com.sparta.innovationfinal.movieApi.MovieGenre;
import com.sparta.innovationfinal.movieApi.dto.MovieGenreDto;
import com.sparta.innovationfinal.movieApi.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    private final String movie = "movie";

    @Override
    public MovieGenreDto findmoviegenre() {
        List<String> moivegenre = new ArrayList<>();

        for (MovieGenre genre : MovieGenre.values()){
            moivegenre.add(genre.getGenreName());
        }
        return new MovieGenreDto(moivegenre);
    }
}
