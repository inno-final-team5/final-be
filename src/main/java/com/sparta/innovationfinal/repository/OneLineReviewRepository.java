package com.sparta.innovationfinal.repository;

import com.sparta.innovationfinal.entity.Member;
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

    List<OneLineReview> findOneLineReviewByMember(Member member);

    OneLineReview findOneLineReviewById(Long id);

    List<OneLineReview> findTop5ByOrderByLikeNumDesc();
}
