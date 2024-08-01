package com.ync.hackathon.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLoginResponseDto {
    private String message;
    private String sessionId;

    public UserLoginResponseDto(String message) {
        this.message = message;
    }
}
