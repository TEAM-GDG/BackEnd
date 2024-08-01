package com.ync.hackathon.infrastructure.repository;

import com.ync.hackathon.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {
    Optional<Member> findByUserId(String userId);
    Optional<Member> findByNameAndPhone(String name, String phone);
    Optional<Member> findByNameAndUserIdAndPhone(String name, String userId, String phone);
}
