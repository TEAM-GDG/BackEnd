package com.ync.hackathon.presentation.controller;

import com.ync.hackathon.application.UserRegisterService;
import com.ync.hackathon.presentation.dto.request.UserRegisterRequestDto;
import com.ync.hackathon.presentation.dto.response.UserRegisterResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserRegisterController {

    private final UserRegisterService userRegisterService;

    @PostMapping("/UsersRegister")
    public UserRegisterResponseDto register(@Valid @RequestBody UserRegisterRequestDto requestDto) {
        return userRegisterService.register(requestDto);
    }
}
