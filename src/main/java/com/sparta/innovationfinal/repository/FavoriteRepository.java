package com.sparta.innovationfinal.repository;

import com.sparta.innovationfinal.entity.Favorite;
import com.sparta.innovationfinal.entity.Member;
import com.sparta.innovationfinal.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    Favorite findMovieByMemberAndMovie(Member member, Movie movie);
    Favorite findFavoriteByMemberAndMovieId(Member member, Long movieId);
    Favorite findFavoriteById(Long id);
    List<Favorite> findFavoriteByMemberOrderByCreatedAtDesc(Member member);
    List<Favorite> findFavoriteByMember(Member member);

    List<Favorite> findAllByMovie(Movie movie);
}
