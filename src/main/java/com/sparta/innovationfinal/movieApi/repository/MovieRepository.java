package com.sparta.innovationfinal.movieApi.repository;

import com.sparta.innovationfinal.entity.Movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    Movie findMovieById(Long movieId);
}
