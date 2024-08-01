package com.ync.hackathon.presentation.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberLoginRequestDto {

    @NotBlank(message = "이메일을 입력해 주세요")
    @Email(message = "유효한 이메일 주소를 입력해 주세요")
    private String email;

    @NotBlank(message = "비밀번호를 입력해 주세요")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$",
            message = "비밀번호는 8자 이상 20자 이하의 영문자, 숫자, 특수문자를 포함해야 합니다.")
    private String pwd;

}
