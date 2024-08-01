package com.ync.hackathon.application;

import com.ync.hackathon.domain.member.RegisterForm;
import com.ync.hackathon.infrastructure.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Optional<RegisterForm> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public RegisterForm registerMember(String name, String birth, String pwd, String email, Integer companyId, String phone) {
        String hashedPwd = BCrypt.hashpw(pwd, BCrypt.gensalt());

        RegisterForm member = new RegisterForm();
        member.setUserUuid(UUID.randomUUID().toString());
        member.setName(name);
        member.setBirth(LocalDate.parse(birth));
        member.setPwd(hashedPwd);
        member.setEmail(email);
        member.setCompanyId(companyId);
        member.setPhone(phone);
        member.setGrade("General");
        member.setPoints(0);
        member.setSignupDate(LocalDate.now().atStartOfDay());
        member.setStreakCount(0);

        return memberRepository.save(member);
    }

    // 이메일로 사용자를 찾고 비밀번호 변경
    public boolean changePassword(String email, String newPassword) {
        Optional<RegisterForm> optionalMember = memberRepository.findByEmail(email);
        if (optionalMember.isPresent()) {
            RegisterForm member = optionalMember.get();
            String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
            member.setPwd(hashedPassword);
            memberRepository.save(member);
            return true;
        }
        return false;
    }
}
