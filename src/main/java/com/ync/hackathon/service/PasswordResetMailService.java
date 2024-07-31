package com.ync.hackathon.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class PasswordResetMailService {
    private final JavaMailSender mailSender;
    private final RedisTemplate redisTemplate;

    public PasswordResetMailService(JavaMailSender mailSender, @Qualifier("redisTemplate") RedisTemplate redisTemplate) {
        this.mailSender = mailSender;
        this.redisTemplate = redisTemplate;
    }

    // 비밀번호 재설정 링크 전송
    public void sendPasswordResetEmail(String email) {
        String resetToken = UUID.randomUUID().toString(); // 랜덤한 토큰 생성
        String resetLink = "http://localhost:8080/membersResetPassword?token=" + resetToken;

        String subject = "[GDG} 비밀번호 재설정을 위한 메일입니다.";
        String content = String.format("비밀번호 재설정을 요청하셨습니다.<br>" +
                "아래 링크를 클릭하여 비밀번호를 재설정하세요.<br>" +
                "<a href=\"%s\">비밀번호 재설정</a>", resetLink);

        sendEmail(email, subject, content);

        redisTemplate.opsForValue().set(resetToken, email, 10, TimeUnit.MINUTES);
    }

    private void sendEmail(String to, String subject, String content) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            helper.setFrom("no-reply@ync.ac.kr");
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("이메일 전송 중 오류가 발생했습니다.", e);
        }
    }
}