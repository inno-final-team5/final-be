package com.sparta.innovationfinal.repository;

//import com.sparta.innovationfinal.movieApi.entity.Movie;
import com.sparta.innovationfinal.entity.Movie;
import com.sparta.innovationfinal.entity.OneLineReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    Movie findMovieByMovieId(Long movieId);
    Movie findMovieByMovieIdAndTitle(Long movieId, String title);
    Movie findMovieById(Long movieId);

    List<Movie> findTop10ByOrderByFavoriteNumDesc();


}

