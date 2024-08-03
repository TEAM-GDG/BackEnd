package login.moments.domain.mail.dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class MailSendDto {

    @NotBlank(message = "이메일을 입력해 주세요")
    @Email(message = "유효한 이메일 주소를 입력해 주세요")
    private String email;
}
