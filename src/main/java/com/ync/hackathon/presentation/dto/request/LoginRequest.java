package com.ync.hackathon.presentation.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @NotBlank(message = "이메일을 입력해 주세요")
    @Email(message = "유효한 이메일 주소를 입력해 주세요")
    private String email;

    @NotBlank(message = "비밀번호를 입력해 주세요")
    @Size(min = 8, max = 255, message = "비밀번호는 8자 이상이어야 합니다.")
    private String pwd;

}
