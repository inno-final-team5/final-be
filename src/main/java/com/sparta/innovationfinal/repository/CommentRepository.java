package com.sparta.innovationfinal.repository;

import com.sparta.innovationfinal.entity.Comment;
import com.sparta.innovationfinal.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    Comment findCommentById(Long id);
    Comment findCommentByPost(Post post);
    List<Comment> findAllByPost(Post post);
}
