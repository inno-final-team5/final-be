package com.sparta.innovationfinal.badge;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BadgeRepository extends JpaRepository<Badge,Long> {
    Badge findBadgeByBadgeName(String name);
    Badge findBadgeById(Long id);
}
