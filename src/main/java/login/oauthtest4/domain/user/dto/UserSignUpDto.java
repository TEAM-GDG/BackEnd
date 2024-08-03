package login.oauthtest4.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
public class UserSignUpDto {

    @Email
    @NotBlank
    private String email;

    @Max(20)
    @Min(8)
    private String password;

    @Min(2)
    @Max(15)
    private String nickname;

    @Max(100)
    @Min(14)
    private int age;
}
