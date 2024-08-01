package com.ync.hackathon.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRegisterResponseDto {
    private String message;
    private String userId;

    public UserRegisterResponseDto(String message) {
        this.message = message;
    }
}


