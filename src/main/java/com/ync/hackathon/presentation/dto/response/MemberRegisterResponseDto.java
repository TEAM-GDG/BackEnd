package com.ync.hackathon.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberRegisterResponseDto {
    private String message;
    private String userId;

    public MemberRegisterResponseDto(String message) {
        this.message = message;
    }
}


