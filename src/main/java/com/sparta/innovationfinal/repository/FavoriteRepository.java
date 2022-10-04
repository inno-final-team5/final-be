package com.sparta.innovationfinal.repository;

import com.sparta.innovationfinal.entity.Favorite;
import com.sparta.innovationfinal.entity.Member;
import com.sparta.innovationfinal.movieApi.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    Favorite findMovieByMemberAndMovie(Member member, Movie movie);
    Favorite findFavoriteByMemberAndMovie(Member member, Movie movie);
    Favorite findFavoritById(Long id);
    List<Favorite> findFavoriteByMemberOrderByCreatedAtDesc(Member member);
}
