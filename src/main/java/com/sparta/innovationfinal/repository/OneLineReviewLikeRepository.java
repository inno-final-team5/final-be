package com.sparta.innovationfinal.repository;

import com.sparta.innovationfinal.entity.Member;
import com.sparta.innovationfinal.entity.OneLineReview;
import com.sparta.innovationfinal.entity.OneLineReviewLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OneLineReviewLikeRepository extends JpaRepository<OneLineReviewLike,Long> {
    OneLineReviewLike findOneLineReviewLikeByMemberAndOneLineReview(Member member, OneLineReview oneLineReview);

    List<OneLineReviewLike> findAllByOneLineReview(OneLineReview oneLineReview);

    OneLineReviewLike findOneLineReviewByMemberAndOneLineReview(Member member, OneLineReview oneLineReview);
    List<OneLineReviewLike> findOneLineReviewLikeByMember(Member member);
}
