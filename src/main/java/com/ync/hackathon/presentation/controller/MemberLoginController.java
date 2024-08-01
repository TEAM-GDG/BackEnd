package com.ync.hackathon.presentation.controller;

import com.ync.hackathon.application.MemberLoginService;
import com.ync.hackathon.presentation.dto.request.MemberLoginRequestDto;
import com.ync.hackathon.presentation.dto.response.MemberLoginResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberLoginController {

    private final MemberLoginService memberLoginService;

    @PostMapping("/membersLogin")
    public MemberLoginResponseDto login(@Valid @RequestBody MemberLoginRequestDto requestDto) {
         return memberLoginService.login(requestDto);
    }

    @PostMapping("/membersLogout")
    public MemberLoginResponseDto logout() {
        return memberLoginService.logout();
    }
}
