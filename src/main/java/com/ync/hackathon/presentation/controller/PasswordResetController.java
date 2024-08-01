package com.ync.hackathon.presentation.controller;

import com.ync.hackathon.presentation.dto.request.FindPwdByEmailRequest;
import com.ync.hackathon.application.MailService;
import com.ync.hackathon.application.MemberService;
import com.ync.hackathon.application.VerificationService;
import com.ync.hackathon.presentation.dto.response.MessageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PasswordResetController {

    private final MemberService memberService;
    private final MailService mailService;
    private final VerificationService verificationService;

    @PostMapping("/sendMailForPasswordReset")
    public ResponseEntity<MessageResponse> sendMailForPasswordReset(@Valid @RequestBody FindPwdByEmailRequest request) {
        boolean memberExists = memberService.isMemberExists(request.getName(), request.getEmail(), request.getPhone());

        if (!memberExists) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse("해당 정보로 사용자를 찾을 수 없습니다."));
        }

        mailService.sendPasswordResetEmail(request.getEmail());
        return ResponseEntity.ok(new MessageResponse("비밀번호 재설정 링크를 다음 메일로 전송하였습니다. : " + request.getEmail()));
    }

    @GetMapping("/membersResetPassword")
    public ResponseEntity<MessageResponse> verifyResetToken(@RequestParam String token) {
        try {
            String email = verificationService.getEmailByToken(token);
            if (email == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("유효하지 않거나 만료된 토큰입니다."));
            }
            return ResponseEntity.ok(new MessageResponse("토큰이 유효합니다. 비밀번호를 재설정할 수 있습니다."));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("서버 내부 오류가 발생했습니다."));
        }
    }

    @PostMapping("/membersResetPassword")
    public ResponseEntity<MessageResponse> resetPassword(@RequestParam String token,
                                                @RequestParam String newPassword) {
        if (!isValidPassword(newPassword)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("비밀번호가 정책에 맞지 않습니다. (8자 이상, 영문자, 숫자, 특수문자 포함)"));
        }

        String email = verificationService.getEmailByToken(token);
        if (email == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("유효하지 않은 토큰입니다."));
        }

        memberService.changePassword(email, newPassword);
        verificationService.deleteToken(token);

        return ResponseEntity.ok(new MessageResponse("비밀번호가 성공적으로 변경되었습니다."));
    }

    private boolean isValidPassword(String password) {
        String passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$";
        return password != null && password.matches(passwordPattern);
    }

}
