package com.ync.hackathon.application;

import com.ync.hackathon.infrastructure.repository.MemberRepository;
import com.ync.hackathon.presentation.dto.request.MemberFindPwdByEmailRequestDto;
import com.ync.hackathon.presentation.dto.response.MessageResponseDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MailSendService {
    private final JavaMailSender mailSender;
    private final RedisTemplate<String, String> redisTemplate;
    private final MemberRepository memberRepository;

    // 비밀번호 재설정 링크 전송
    public MessageResponseDto sendPasswordResetEmail(MemberFindPwdByEmailRequestDto requestDto) {
        if (!memberRepository.findByNameAndEmailAndPhone(requestDto.getName(), requestDto.getEmail(), requestDto.getPhone()).isPresent()) {
            return new MessageResponseDto("해당 정보로 사용자를 찾을 수 없습니다.");
        }

        String resetToken = UUID.randomUUID().toString();
        String resetLink = "http://localhost:8080/membersResetPassword?token=" + resetToken;

        String subject = "[GDG} 비밀번호 재설정을 위한 메일입니다.";
        String content = String.format("비밀번호 재설정을 요청하셨습니다.<br>" +
                "아래 링크를 클릭하여 비밀번호를 재설정하세요.<br>" +
                "<a href=\"%s\">비밀번호 재설정</a>", resetLink);

        sendEmail(requestDto.getEmail(), subject, content);

        redisTemplate.opsForValue().set(resetToken, requestDto.getEmail(), 10, TimeUnit.MINUTES);

        return new MessageResponseDto("비밀번호 재설정 링크를 다음 메일로 전송하였습니다. : " + requestDto.getEmail());
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