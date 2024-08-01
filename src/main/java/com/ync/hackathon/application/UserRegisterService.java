package com.ync.hackathon.application;

import com.ync.hackathon.domain.member.User;
import com.ync.hackathon.infrastructure.repository.UserRepository;
import com.ync.hackathon.presentation.dto.request.UserRegisterRequestDto;
import com.ync.hackathon.presentation.dto.response.UserRegisterResponseDto;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserRegisterService {
    private final UserRepository UserRepository;

    //회원가입
    public UserRegisterResponseDto register(UserRegisterRequestDto requestDto) {
        if (isEmailExist(requestDto.getUserId())) {
            return new UserRegisterResponseDto("이미 존재하는 이메일입니다.");
        }

        String hashedPwd = BCrypt.hashpw(requestDto.getPwd(), BCrypt.gensalt());

        User user = new User();
        user.setName(requestDto.getName());
        user.setBirth(requestDto.getBirth());
        user.setPwd(hashedPwd);
        user.setUserId(requestDto.getUserId());
        user.setCompanyId(requestDto.getCompanyId());
        user.setPhone(requestDto.getPhone());
        user.setGrade("General");
        user.setPoints(0);
        user.setSignupDate(LocalDateTime.now());
        user.setStreakCount(0);

        UserRepository.save(user);

        return new UserRegisterResponseDto("회원가입 성공", user.getUserId());
    }

    //이메일로 회원 찾기
    public boolean isEmailExist(String email) {
        return UserRepository.findByUserId(email).isPresent();
    }

}
