package com.ync.hackathon.application;

import com.ync.hackathon.domain.member.User;
import com.ync.hackathon.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    //비밀번호 변경
    public boolean changePassword(String email, String newPassword) {
        Optional<User> optionalMember = userRepository.findByUserId(email);
        if (optionalMember.isPresent()) {
            User user = optionalMember.get();
            String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
            user.setPwd(hashedPassword);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    //회원정보 유무 체크
    public boolean isMemberExists(String name, String userId, String phone) {
        if (userId != null && !userId.isEmpty()) {
            return userRepository.findByNameAndUserIdAndPhone(name, userId, phone).isPresent();
        } else {
            return userRepository.findByNameAndPhone(name, phone).isPresent();
        }
    }

}
