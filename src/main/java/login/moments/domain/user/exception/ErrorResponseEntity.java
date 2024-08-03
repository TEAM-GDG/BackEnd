package login.moments.domain.user.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
@Builder
public class ErrorResponseEntity {
    private int status;
    private String message;

    public static ResponseEntity<ErrorResponseEntity> toResponseEntity(GlobalErrorCode e) {
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(ErrorResponseEntity.builder()
                .status(e.getHttpStatus().value())
                .message(e.getMessage())
                .build());

    }

}
