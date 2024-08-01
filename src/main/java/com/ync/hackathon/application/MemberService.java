package com.ync.hackathon.application;

import com.ync.hackathon.domain.member.Member;
import com.ync.hackathon.infrastructure.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;



    //비밀번호 변경
    public boolean changePassword(String email, String newPassword) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
            member.setPwd(hashedPassword);
            memberRepository.save(member);
            return true;
        }
        return false;
    }

    //회원정보 유무 체크
    public boolean isMemberExists(String name, String email, String phone) {
        if (email != null && !email.isEmpty()) {
            return memberRepository.findByNameAndEmailAndPhone(name, email, phone).isPresent();
        } else {
            return memberRepository.findByNameAndPhone(name, phone).isPresent();
        }
    }

}
