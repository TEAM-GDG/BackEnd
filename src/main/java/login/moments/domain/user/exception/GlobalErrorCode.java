package login.moments.domain.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum GlobalErrorCode {

    EMAIL_ALREADY(HttpStatus.CONFLICT, "이미 존재하는 이메일 입니다."); // 이메일 중복 시

    private final HttpStatus httpStatus;
    private final String message;
}
