package com.ync.hackathon.controller;

import com.ync.hackathon.domain.MemberForm;
import com.ync.hackathon.dto.EmailRequestDto;
import com.ync.hackathon.repository.MemberRepository;
import com.ync.hackathon.service.PasswordResetMailService;
import com.ync.hackathon.service.UserService;
import com.ync.hackathon.service.VerificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class PasswordResetController {

    private final VerificationService verificationService;
    private final UserService userService;
    private final PasswordResetMailService mailService;
    private final MemberRepository memberRepository;

    @PostMapping("/sendPasswordReset")
    public ResponseEntity<String> sendPasswordResetEmail(@Valid @RequestBody EmailRequestDto emailDto) {
        Optional<MemberForm> optionalMember = memberRepository.findByNameAndEmailAndPhone(emailDto.getName(), emailDto.getEmail(), emailDto.getPhone());

        if (optionalMember.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 정보로 사용자를 찾을 수 없습니다.");
        }

        mailService.sendPasswordResetEmail(emailDto.getEmail());
        return ResponseEntity.ok("비밀번호 재설정 링크를 다음 메일로 전송하였습니다. : " + emailDto.getEmail());
    }

    @GetMapping("/membersResetPassword")
    public ResponseEntity<String> verifyResetToken(@RequestParam String token) {
        String email = verificationService.getEmailByToken(token);
        if (email == null) {
            return ResponseEntity.status(401).body("유효하지 않거나 만료된 토큰입니다.");
        }

        return ResponseEntity.ok("토큰이 유효합니다. 비밀번호를 재설정할 수 있습니다.");
    }

    @PostMapping("/membersResetPassword")
    public ResponseEntity<String> resetPassword(@RequestParam String token,
                                                @RequestParam String newPassword) {
        if (!isValidPassword(newPassword)) {
            return ResponseEntity.status(400).body("비밀번호가 정책에 맞지 않습니다. (8자 이상, 영문자, 숫자, 특수문자 포함)");
        }

        String email = verificationService.getEmailByToken(token);
        if (email == null) {
            return ResponseEntity.status(401).body("유효하지 않은 토큰입니다.");
        }

        userService.changePassword(email, newPassword);
        verificationService.deleteToken(token);

        return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
    }

    private boolean isValidPassword(String password) {
        String passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$";
        return password != null && password.matches(passwordPattern);
    }

}
