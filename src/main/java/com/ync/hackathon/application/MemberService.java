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

    //회원가입
    public Member register(String name, LocalDate birth, String pwd, String email, Integer companyId, String phone) {
        String hashedPwd = BCrypt.hashpw(pwd, BCrypt.gensalt());

        Member member = new Member();
        member.setUserUuid(UUID.randomUUID().toString());
        member.setName(name);
        member.setBirth(birth);
        member.setPwd(hashedPwd);
        member.setEmail(email);
        member.setCompanyId(companyId);
        member.setPhone(phone);
        member.setGrade("General");
        member.setPoints(0);
        member.setSignupDate(LocalDateTime.now());
        member.setStreakCount(0);

        return memberRepository.save(member);
    }

    //이메일로 회원 찾기
    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    //로그인
    public Member login(String email, String password) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            if (BCrypt.checkpw(password, member.getPwd())) {
                return member;
            }
        }
        return null;
    }

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
