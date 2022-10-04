package com.sparta.innovationfinal.movieApi.repository;

import com.sparta.innovationfinal.entity.Favorite;
import com.sparta.innovationfinal.entity.Member;
import com.sparta.innovationfinal.movieApi.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    Movie findMovieByMovieId(Long movieId);
    Movie findMovieByMovieIdAndTitle(Long movieId, String title);
    Movie findMovieById(Long Id);


}