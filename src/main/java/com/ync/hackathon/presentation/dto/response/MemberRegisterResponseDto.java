package com.ync.hackathon.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberRegisterResponseDto {
    private String message;
    private String userUuid;

    public MemberRegisterResponseDto(String message) {
        this.message = message;
    }
}


