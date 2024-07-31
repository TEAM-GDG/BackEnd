package com.ync.hackathon.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterResponse {
    private String message;
    private String userUuid;
}
