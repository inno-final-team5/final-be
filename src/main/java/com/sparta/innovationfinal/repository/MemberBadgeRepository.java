package com.sparta.innovationfinal.repository;

import com.sparta.innovationfinal.entity.MemberBadge;
import com.sparta.innovationfinal.entity.Badge;
import com.sparta.innovationfinal.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberBadgeRepository extends JpaRepository<MemberBadge,Long> {
    MemberBadge findMemberBadgeByMemberAndBadge(Member member, Badge badge);
    List<MemberBadge> findMemberBadgeByMember(Member member);
    MemberBadge findMemberBadgeByBadgeAndMember(Badge badge, Member member);
    List<MemberBadge> findAllByBadge(Badge badge);
}
