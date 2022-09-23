package com.sparta.innovationfinal.repository;

import com.sparta.innovationfinal.entity.Member;
import com.sparta.innovationfinal.entity.Post;
import com.sparta.innovationfinal.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Long countAllByPostId(Long postId);
    PostLike findByPostIdAndMemberId(Member member, Post post);
    List<PostLike> findByMember(Member member);
}
