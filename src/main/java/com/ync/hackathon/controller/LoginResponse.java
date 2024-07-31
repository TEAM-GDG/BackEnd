package com.ync.hackathon.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String message;
    private String sessionId;

    public LoginResponse(String message) {
        this.message = message;
    }
}
