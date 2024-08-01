package com.ync.hackathon.application;

import com.ync.hackathon.domain.member.Member;
import com.ync.hackathon.infrastructure.repository.MemberRepository;
import com.ync.hackathon.presentation.dto.request.MemberRegisterRequestDto;
import com.ync.hackathon.presentation.dto.response.MemberRegisterResponseDto;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberRegisterService {
    private final MemberRepository memberRepository;

    //회원가입
    public MemberRegisterResponseDto register(MemberRegisterRequestDto requestDto) {
        if (isEmailExist(requestDto.getEmail())) {
            return new MemberRegisterResponseDto("이미 존재하는 이메일입니다.");
        }

        String hashedPwd = BCrypt.hashpw(requestDto.getPwd(), BCrypt.gensalt());

        Member member = new Member();
        member.setUserUuid(UUID.randomUUID().toString());
        member.setName(requestDto.getName());
        member.setBirth(requestDto.getBirth());
        member.setPwd(hashedPwd);
        member.setEmail(requestDto.getEmail());
        member.setCompanyId(requestDto.getCompanyId());
        member.setPhone(requestDto.getPhone());
        member.setGrade("General");
        member.setPoints(0);
        member.setSignupDate(LocalDateTime.now());
        member.setStreakCount(0);

        memberRepository.save(member);

        return new MemberRegisterResponseDto("회원가입 성공", member.getUserUuid());
    }

    //이메일로 회원 찾기
    public boolean isEmailExist(String email) {
        return memberRepository.findByEmail(email).isPresent();
    }

}
