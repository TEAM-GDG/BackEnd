package com.ync.hackathon.application;

import com.ync.hackathon.domain.member.User;
import com.ync.hackathon.infrastructure.repository.UserRepository;
import com.ync.hackathon.infrastructure.web.SessionConst;
import com.ync.hackathon.presentation.dto.request.UserLoginRequestDto;
import com.ync.hackathon.presentation.dto.response.UserLoginResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserLoginService {

    private final UserRepository userRepository;
    private final HttpServletRequest httpServletRequest;

    //로그인
    public UserLoginResponseDto login(UserLoginRequestDto requestDto) {
        Optional<User> optionalMember = userRepository.findByUserId(requestDto.getUserId());
        User user = null;

        if (optionalMember.isPresent()) {
            user = optionalMember.get();

            // 비밀번호 확인
            if (!BCrypt.checkpw(requestDto.getPwd(), user.getPwd())) {
                user = null; // 비밀번호가 일치하지 않으면 null로 설정
            }
        }

        if (user == null) {
            return new UserLoginResponseDto("유효하지 않은 이메일 또는 비밀번호입니다.");
        }

        HttpSession httpSession = httpServletRequest.getSession();
        httpSession.setAttribute(SessionConst.LOGIN_MEMBER, user);

        return new UserLoginResponseDto("로그인 성공", httpSession.getId());
    }

    //로그아웃
    public UserLoginResponseDto logout() {
        HttpSession httpSession = httpServletRequest.getSession(false);
        if (httpSession != null) {
            httpSession.invalidate();
            return new UserLoginResponseDto("로그아웃 성공");
        }
        else {
            return new UserLoginResponseDto("로그아웃 실패");
        }
    }
}
