package com.ync.hackathon.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberLoginResponseDto {
    private String message;
    private String sessionId;

    public MemberLoginResponseDto(String message) {
        this.message = message;
    }
}
