package com.sparta.innovationfinal.repository;

import com.sparta.innovationfinal.entity.Comment;
import com.sparta.innovationfinal.entity.SubComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubCommentRepository extends JpaRepository<SubComment,Long> {
    SubComment findSubCommentById(Long id);
    List<SubComment> findAllByComment(Comment comment);
}
