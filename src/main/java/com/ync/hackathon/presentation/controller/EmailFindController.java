package com.ync.hackathon.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailFindController {

//    private final MemberRepository memberRepository;
//
//    @PostMapping("/membersFindEmail")
//    public ResponseEntity<?> findEmail(@Valid @RequestBody EmailFindForm form, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("입력 필드가 유효하지 않습니다.");
//        }
//
//        Optional<MemberForm> optionalMember = memberRepository.findByNameAndPhone(form.getName(), form.getPhone());
//        MemberForm member = optionalMember.orElseThrow(() -> new RuntimeException("사용자 정보를 찾을 수 없습니다."));
//
//        // 인증번호 확인 로직 (여기서는 예시로 "123456" 사용)
//        if (!"123456".equals(form.getVerificationCode())) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증번호가 일치하지 않습니다.");
//        }
//
//        // 성공적인 경우
//        return ResponseEntity.ok(new EmailFindResponse("이메일 찾기 성공", member.getEmail()));
//    }
}
