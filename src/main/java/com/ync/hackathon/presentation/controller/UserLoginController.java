package com.ync.hackathon.presentation.controller;

import com.ync.hackathon.application.UserLoginService;
import com.ync.hackathon.application.UserService;
import com.ync.hackathon.presentation.dto.request.UserLoginRequestDto;
import com.ync.hackathon.presentation.dto.response.UserLoginResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserLoginController {

    private final UserService UserService;
    private final UserLoginService userLoginService;

    @PostMapping("/UsersLogin")
    public UserLoginResponseDto login(@Valid @RequestBody UserLoginRequestDto requestDto) {
         return userLoginService.login(requestDto);
    }

    @PostMapping("/UsersLogout")
    public UserLoginResponseDto logout() {
        return userLoginService.logout();
    }
}
