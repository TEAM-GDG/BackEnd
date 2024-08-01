package com.ync.hackathon.presentation.controller;

import com.ync.hackathon.application.MemberService;
import com.ync.hackathon.presentation.dto.request.LoginRequest;
import com.ync.hackathon.presentation.dto.response.LoginResponse;
import com.ync.hackathon.domain.member.Member;
import com.ync.hackathon.infrastructure.web.SessionConst;
import com.ync.hackathon.presentation.dto.response.MessageResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;

    @PostMapping("/membersLogin")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request, HttpServletRequest httpServletRequest) {
        Member loginMember = memberService.login(request.getEmail(), request.getPwd());

        if (loginMember == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new MessageResponse("유효하지 않은 이메일 또는 비밀번호입니다."));
        }

        HttpSession session = httpServletRequest.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        return ResponseEntity.ok(new LoginResponse("로그인 성공", session.getId()));
    }

    @PostMapping("/membersLogout")
    public ResponseEntity<MessageResponse> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok(new MessageResponse("로그아웃 성공"));
    }
}
