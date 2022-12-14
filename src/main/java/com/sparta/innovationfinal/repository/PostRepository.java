package com.sparta.innovationfinal.repository;

import com.sparta.innovationfinal.entity.Member;
import com.sparta.innovationfinal.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Post findPostById(Long id);
    List<Post> findPostByMember(Member member);
    List<Post> findTop5ByOrderByCreatedAtDesc();
    List<Post> findAllByOrderByCreatedAtDesc();
    List<Post> findPostByMemberOrderByCreatedAtDesc(Member member);
    List<Post> findPostByPostCategoryOrderByCreatedAtDesc(String postCategory);
    List<Post> findByPostTitleContaining(String keyword);
    List<Post> findByPostContentContaining(String keyword);
}
