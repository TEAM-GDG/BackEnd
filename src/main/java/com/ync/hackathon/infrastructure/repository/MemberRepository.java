package com.ync.hackathon.infrastructure.repository;

import com.ync.hackathon.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    Optional<Member> findByNameAndPhone(String name, String phone);
    Optional<Member> findByNameAndEmailAndPhone(String name, String email, String phone);
}
