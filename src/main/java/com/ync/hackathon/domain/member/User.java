package com.ync.hackathon.domain.member;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column(name = "userId", length = 255, nullable = false)
    private String userId;

    @Column(name = "name", length = 10, nullable = false)
    private String name;

    @Column(name = "birth", nullable = false)
    private LocalDate birth;

    @Column(name = "pwd", length = 255, nullable = false)
    private String pwd;

    @Column(name = "company_id")
    private Integer companyId;

    @Column(name = "phone", unique = true, length = 15, nullable = false)
    private String phone;

    // 회원 등급
    @Column(name = "grade", length = 20, nullable = false)
    private String grade;

    @Column(name = "points", nullable = false)
    private Integer points = 0;

    @Column(name = "profile_image_path", length = 255)
    private String profileImagePath;

    @Column(name = "signup_date", nullable = false)
    private LocalDateTime signupDate = LocalDateTime.now();

    @Column(name = "streak_count", nullable = false)
    private Integer streakCount = 0;

    @Column(name = "last_record_date")
    private LocalDate lastRecordDate;

    @Enumerated(EnumType.STRING)
    private UserRole role; // ROLE_USER, ROLE_ADMIN, ROLE_MANAGER
}
