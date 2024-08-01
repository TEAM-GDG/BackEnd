package com.ync.hackathon.infrastructure.repository;

import com.ync.hackathon.domain.member.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUserId(String userId);
    Optional<User> findByNameAndPhone(String name, String phone);
    Optional<User> findByNameAndUserIdAndPhone(String name, String userId, String phone);
}
