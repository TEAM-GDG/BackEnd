package com.ync.hackathon.presentation.controller;

import com.ync.hackathon.presentation.dto.response.LoginResponse;
import com.ync.hackathon.domain.member.LoginForm;
import com.ync.hackathon.domain.member.RegisterForm;
import com.ync.hackathon.infrastructure.repository.MemberRepository;
import com.ync.hackathon.infrastructure.web.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final MemberRepository memberRepository;

    @PostMapping("/membersLogin")
    public ResponseEntity<?> login(@RequestBody LoginForm form, HttpServletRequest request) {

        RegisterForm loginMember = memberRepository.findByEmail(form.getEmail())
                .filter(member -> BCrypt.checkpw(form.getPwd(), member.getPwd()))
                .orElse(null);

        if (loginMember == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse("유효하지 않은 이메일 또는 비밀번호입니다."));
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        return ResponseEntity.ok(new LoginResponse("로그인 성공", session.getId()));
    }

    @PostMapping("/membersLogout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok(new LoginResponse("로그아웃 성공"));
    }
}
