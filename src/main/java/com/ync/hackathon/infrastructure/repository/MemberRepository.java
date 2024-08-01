package com.ync.hackathon.infrastructure.repository;

import com.ync.hackathon.domain.member.RegisterForm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<RegisterForm, Long> {
    Optional<RegisterForm> findByEmail(String email);
    Optional<RegisterForm> findByNameAndPhone(String name, String phone);
    Optional<RegisterForm> findByNameAndEmailAndPhone(String name, String email, String phone);
}
