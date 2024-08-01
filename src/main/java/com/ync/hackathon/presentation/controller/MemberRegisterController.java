package com.ync.hackathon.presentation.controller;

import com.ync.hackathon.application.MemberRegisterService;
import com.ync.hackathon.presentation.dto.request.MemberRegisterRequestDto;
import com.ync.hackathon.presentation.dto.response.MemberRegisterResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberRegisterController {

    private final MemberRegisterService memberRegisterService;

    @PostMapping("/membersRegister")
    public MemberRegisterResponseDto  register(@Valid @RequestBody MemberRegisterRequestDto requestDto) {
        return memberRegisterService.register(requestDto);
    }
}
