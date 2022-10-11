package com.sparta.innovationfinal.badge;

import com.sparta.innovationfinal.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberBadgeRepository extends JpaRepository<MemberBadge,Long> {
    MemberBadge findMemberBadgeByMemberAndBadge(Member member, Badge badge);
}
