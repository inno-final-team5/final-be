package com.sparta.innovationfinal.repository;

import com.sparta.innovationfinal.entity.Member;
import com.sparta.innovationfinal.entity.Movie;
import com.sparta.innovationfinal.entity.OneLineReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OneLineReviewRepository extends JpaRepository <OneLineReview,Long> {
    static List<OneLineReview> findTop10ByOrderBylikeNumDesc() {
        return null;
    }

    List<OneLineReview> findAllByOrderByCreatedAtDesc();
    List<OneLineReview> findAllByMovie_MovieIdOrderByLikeNumDesc(Long movieId);

    List<OneLineReview> findOneLineReviewByMember(Member member);
    List<OneLineReview> findOneLineReviewByMemberOrderByCreatedAtDesc(Member member);
    OneLineReview findOneLineReviewById(Long id);
    OneLineReview findOneLineReviewByMemberAndMovie(Member member, Movie movie);
    List<OneLineReview> findTop5ByOrderByLikeNumDesc();
    List<OneLineReview> findOneLineReviewByMemberAndOneLineReviewStar(Member member, int star);
}
