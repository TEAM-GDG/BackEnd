package com.ync.hackathon.controller;

import com.ync.hackathon.domain.MemberForm;
import com.ync.hackathon.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class RegisterController {

    private final MemberRepository memberRepository;

    @PostMapping("/membersRegister")
    public ResponseEntity<?> registerMember(@RequestParam("name") String name,
                                            @RequestParam("birth") String birth,
                                            @RequestParam("pwd") String pwd,
                                            @RequestParam("email") String email,
                                            @RequestParam("company") Integer companyId,
                                            @RequestParam("phone") String phone) {

        if (!isValidName(name) || !isValidBirth(birth) || !isValidPassword(pwd) || !isValidEmail(email) || !isValidPhone(phone)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("입력 필드가 유효하지 않습니다.");
        }

        if (memberRepository.findByEmail(email).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 이메일입니다.");
        }

        String hashedPwd = BCrypt.hashpw(pwd, BCrypt.gensalt());

        MemberForm member = new MemberForm();
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

        memberRepository.save(member);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new RegisterResponse("회원가입 성공", member.getUserUuid())
        );
    }

    private boolean isValidName(String name) {
        return name != null && name.length() >= 2 && name.length() <= 10;
    }

    private boolean isValidBirth(String birth) {
        try {
            LocalDate parsedDate = LocalDate.parse(birth);
            LocalDate minDate = LocalDate.of(1900, 1, 1);
            LocalDate maxDate = LocalDate.of(2024, 12, 31);
            return !parsedDate.isBefore(minDate) && !parsedDate.isAfter(maxDate);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isValidPassword(String pwd) {
        String passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$";
        return pwd != null && pwd.matches(passwordPattern);
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email != null && email.matches(emailPattern);
    }

    private boolean isValidPhone(String phone) {
        String phonePattern = "^\\d{3}-(\\d{3}|\\d{4})-\\d{4}$";
        return phone != null && phone.matches(phonePattern);
    }
}
