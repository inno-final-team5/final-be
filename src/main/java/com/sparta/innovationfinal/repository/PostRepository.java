package com.sparta.innovationfinal.repository;

import com.sparta.innovationfinal.entity.Member;
import com.sparta.innovationfinal.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Post findPostById(Long id);
    List<Post> findTop10ByOrderByCreatedAtDesc();
    List<Post> findAllByOrderByCreatedAtDesc();
    List<Post> findPostByMemberOrderByCreatedAtDesc(Member member);
    List<Post> findPostByPostCategory(String postCategory);
}
