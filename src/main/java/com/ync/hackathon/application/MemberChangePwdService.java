package com.ync.hackathon.application;

import com.ync.hackathon.domain.member.Member;
import com.ync.hackathon.infrastructure.repository.MemberRepository;
import com.ync.hackathon.presentation.dto.request.MemberChangePwdRequestDto;
import com.ync.hackathon.presentation.dto.response.MessageResponseDto;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberChangePwdService {

    private final MemberRepository memberRepository;
    private final VerificationService verificationService;

    //비밀번호 변경
    public MessageResponseDto changePassword(MemberChangePwdRequestDto requestDto) {

        String email = verificationService.getEmailByToken(requestDto.getToken());
        if (email == null) {
            return new MessageResponseDto("유효하지 않은 토큰입니다.");
        }

        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            String hashedPassword = BCrypt.hashpw(requestDto.getNewPassword(), BCrypt.gensalt());
            member.setPwd(hashedPassword);
            memberRepository.save(member);
        }

        verificationService.deleteToken(requestDto.getToken());

        return new MessageResponseDto("비밀번호 변경이 완료되었습니다.");
    }
}
