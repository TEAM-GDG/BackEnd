package com.ync.hackathon.presentation.controller;

import com.ync.hackathon.application.MemberService;
import com.ync.hackathon.presentation.dto.request.RegisterRequest;
import com.ync.hackathon.presentation.dto.response.MessageResponse;
import com.ync.hackathon.presentation.dto.response.RegisterResponse;
import com.ync.hackathon.domain.member.Member;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegisterController {

    private final MemberService memberService;

    @PostMapping("/membersRegister")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {

        if (memberService.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("이미 존재하는 이메일입니다."));
        }

        Member member = memberService.register(
                request.getName(),
                request.getBirth(),
                request.getPwd(),
                request.getEmail(),
                request.getCompanyId(),
                request.getPhone()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new RegisterResponse("회원가입 성공", member.getUserUuid())
        );
    }

}
