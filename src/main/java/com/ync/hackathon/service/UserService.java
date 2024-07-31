package com.ync.hackathon.service;

import com.ync.hackathon.domain.MemberForm;
import com.ync.hackathon.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final MemberRepository memberRepository;

    // 이메일로 사용자를 찾고 비밀번호 변경
    public boolean changePassword(String email, String newPassword) {
        Optional<MemberForm> optionalMember = memberRepository.findByEmail(email);
        if (optionalMember.isPresent()) {
            MemberForm member = optionalMember.get();
            String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
            member.setPwd(hashedPassword);
            memberRepository.save(member);
            return true;
        }
        return false;
    }
}
