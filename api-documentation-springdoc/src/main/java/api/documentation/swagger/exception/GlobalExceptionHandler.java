package api.documentation.swagger.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public static class ErrorResponse {
        private int code;
        private String message;

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exception(Exception e) {
        String message = e.getMessage();
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.code = 400;
        errorResponse.message = message;
        return ResponseEntity.badRequest().body(errorResponse);

    }
}
