package com.ync.hackathon.repository;

import com.ync.hackathon.domain.MemberForm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberForm, Long> {
    Optional<MemberForm> findByEmail(String email);
    Optional<MemberForm> findByNameAndPhone(String name, String phone);
    Optional<MemberForm> findByNameAndEmailAndPhone(String name, String email, String phone);
}
