package com.ync.hackathon.presentation.controller;

import com.ync.hackathon.application.*;
import com.ync.hackathon.presentation.dto.request.MemberChangePwdRequestDto;
import com.ync.hackathon.presentation.dto.request.MemberFindPwdByEmailRequestDto;
import com.ync.hackathon.presentation.dto.response.MessageResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PasswordResetController {

    private final MemberChangePwdService memberChangePwdService;
    private final MailCheckService mailCheckService;
    private final MailSendService mailSendService;
    private final VerificationService verificationService;

    @PostMapping("/sendMailForPasswordReset")
    public MessageResponseDto sendMailForPasswordReset(@Valid @RequestBody MemberFindPwdByEmailRequestDto requestDto) {
        return mailSendService.sendPasswordResetEmail(requestDto);
    }

    @GetMapping("/membersResetPassword")
    public MessageResponseDto verifyResetToken(@RequestParam String token) {
        return mailCheckService.getEmailByToken(token);
    }

    @PostMapping("/membersResetPassword")
    public MessageResponseDto resetPassword(@Valid @RequestBody MemberChangePwdRequestDto requestDto) {
        return memberChangePwdService.changePassword(requestDto);
    }

}
