package com.ync.hackathon.presentation.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MemberRegisterRequestDto {

    @NotBlank(message = "이름을 입력해 주세요")
    @Size(min = 2, max = 10, message = "이름은 2자 이상 10자 이하이어야 합니다.")
    private String name;

    @NotNull(message = "생년월일을 입력해 주세요")
    @Past(message = "생년월일은 과거 날짜여야 합니다.")
    private LocalDate birth;

    @NotBlank(message = "비밀번호를 입력해 주세요")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$",
            message = "비밀번호는 8자 이상 20자 이하의 영문자, 숫자, 특수문자를 포함해야 합니다.")
    private String pwd;

    @NotBlank(message = "이메일을 입력해 주세요")
    @Email(message = "유효한 이메일 주소를 입력해 주세요")
    private String email;

    private Integer companyId;

    @NotBlank(message = "전화번호를 입력해 주세요")
    @Pattern(regexp = "^\\d{3}-(\\d{3}|\\d{4})-\\d{4}$", message = "전화번호는 000-000-0000 또는 000-0000-0000 형식이어야 합니다.")
    private String phone;
}
