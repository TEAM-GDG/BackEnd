package login.moments.domain.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmailAlreadyExistException extends RuntimeException {
    GlobalErrorCode globalErrorCode;
}
