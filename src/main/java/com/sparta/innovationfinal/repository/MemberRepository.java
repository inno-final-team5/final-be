package com.sparta.innovationfinal.repository;

import com.sparta.innovationfinal.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    Optional<Member> findByNickname(String nickname);
    Member findMemberByEmail(String email);
    Member findMemberByNickname(String nickname);
}
