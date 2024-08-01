package com.ync.hackathon.application;

import com.ync.hackathon.domain.member.Member;
import com.ync.hackathon.infrastructure.repository.MemberRepository;
import com.ync.hackathon.infrastructure.web.SessionConst;
import com.ync.hackathon.presentation.dto.request.MemberLoginRequestDto;
import com.ync.hackathon.presentation.dto.response.MemberLoginResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberLoginService {

    private final MemberRepository memberRepository;
    private final HttpServletRequest httpServletRequest;

    //로그인
    public MemberLoginResponseDto login(MemberLoginRequestDto requestDto) {
        Optional<Member> optionalMember = memberRepository.findByUserId(requestDto.getEmail());
        Member member = null;

        if (optionalMember.isPresent()) {
            member = optionalMember.get();

            // 비밀번호 확인
            if (!BCrypt.checkpw(requestDto.getPwd(), member.getPwd())) {
                member = null; // 비밀번호가 일치하지 않으면 null로 설정
            }
        }

        if (member == null) {
            return new MemberLoginResponseDto("유효하지 않은 이메일 또는 비밀번호입니다.");
        }

        HttpSession httpSession = httpServletRequest.getSession();
        httpSession.setAttribute(SessionConst.LOGIN_MEMBER, member);

        return new MemberLoginResponseDto("로그인 성공", httpSession.getId());
    }

    //로그아웃
    public MemberLoginResponseDto logout() {
        HttpSession httpSession = httpServletRequest.getSession(false);
        if (httpSession != null) {
            httpSession.invalidate();
            return new MemberLoginResponseDto("로그아웃 성공");
        }
        else {
            return new MemberLoginResponseDto("로그아웃 실패");
        }
    }
}
