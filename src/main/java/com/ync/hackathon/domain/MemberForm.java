package com.ync.hackathon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "member")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberForm {

    @Id
    @Column(name = "user_uuid", length = 36, nullable = false)
    private String userUuid;

    @Column(name = "name", length = 10, nullable = false)
    private String name;

    @Column(name = "birth", nullable = false)
    private LocalDate birth;

    @Column(name = "pwd", length = 255, nullable = false)
    private String pwd;

    @Column(name = "email", length = 255, nullable = false)
    private String email;

    @Column(name = "company_id")
    private Integer companyId;

    @Column(name = "phone", length = 15, nullable = false)
    private String phone;

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

}
