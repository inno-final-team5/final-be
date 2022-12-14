package com.sparta.innovationfinal.repository;

import com.sparta.innovationfinal.entity.Member;
import com.sparta.innovationfinal.entity.Post;
import com.sparta.innovationfinal.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    List<PostLike> findAllByPost(Post post);
    PostLike findPostByMemberAndPost(Member member, Post post);
    PostLike findPostLikeByMemberAndPost(Member member, Post post);
    List<PostLike> findPostLikeByMember(Member member);
}
