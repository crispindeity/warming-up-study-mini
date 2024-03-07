package inflearn.mini.workHub.global;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
@Getter
@Builder
public class ErrorResponse {
    private int status;
    private String code;
    private String message;

    public static ResponseEntity<ErrorResponse> toResponse(ErrorCode e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(ErrorResponse.builder()
                        .status(e.getStatus().value())
                        .code(e.name())
                        .message(e.getMessage())
                        .build()
                );
    }
}
