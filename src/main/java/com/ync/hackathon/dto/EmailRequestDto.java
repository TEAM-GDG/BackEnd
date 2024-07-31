package com.ync.hackathon.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailRequestDto {

    @NotEmpty(message = "이름을 입력해 주세요")
    private String name;

    @Email(message = "유효한 이메일을 입력해 주세요")
    @NotEmpty(message = "이메일을 입력해 주세요")
    private String email;

    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "전화번호는 000-000-0000 또는 000-0000-0000 형식이어야 합니다.")
    private String phone;
}
