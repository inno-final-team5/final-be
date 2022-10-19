package com.sparta.innovationfinal.repository;

import com.sparta.innovationfinal.entity.SubComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubCommentRepository extends JpaRepository<SubComment,Long> {
    SubComment findSubCommentById(Long id);
}
