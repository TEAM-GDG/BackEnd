package login.moments.domain.user.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<ErrorResponseEntity> handleEmailException(EmailAlreadyExistException e) {
        return ErrorResponseEntity.toResponseEntity(e.getGlobalErrorCode());
    }

}
