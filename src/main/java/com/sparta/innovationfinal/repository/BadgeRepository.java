package com.sparta.innovationfinal.repository;

import com.sparta.innovationfinal.entity.Badge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BadgeRepository extends JpaRepository<Badge,Long> {
    Badge findBadgeByBadgeName(String name);
    Badge findBadgeById(Long id);



}
