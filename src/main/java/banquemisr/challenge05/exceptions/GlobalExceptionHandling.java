package banquemisr.challenge05.exceptions;

import banquemisr.challenge05.model.dto.GenericResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandling {
    private final MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest requestrequest) {

        String errorMessage = messageSource.getMessage(
                "validation.error", null, "Validation failed", LocaleContextHolder.getLocale());

        log.error("Validation error: {}", ex.getMessage());
        return handleErrorResonse(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException ex) {

        String errorMessage = messageSource.getMessage(
                "server.error", null, "An unexpected error occurred. Please try again later.", LocaleContextHolder.getLocale());

        log.error("Runtime exception: {}", ex.getMessage());
        return handleErrorResonse(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<Object> handleAccessDeniedException(AuthorizationDeniedException ex) {

        String errorMessage = messageSource.getMessage(
                "access.denied", null, "You do not have permission to access this resource.", LocaleContextHolder.getLocale());

        return handleErrorResonse(errorMessage, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleGeneralException(
            Exception ex) {
        String errorMessage = messageSource.getMessage(
                "general.error", null, "An unexpected error occurred", LocaleContextHolder.getLocale());

        log.error("General exception: {}", ex.getMessage());
        return handleErrorResonse(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BanqueMisrException.class)
    public ResponseEntity<Object> handleBanqueMisrException(BanqueMisrException ex) {
        log.error("BanqueMisrException: ", ex);
        String errorMessage = messageSource.getMessage(
                ex.getMessage(), null, "An unexpected error occurred", LocaleContextHolder.getLocale());
        return handleErrorResonse(errorMessage, ex.getStatusCode());
    }

    private ResponseEntity<Object> handleErrorResonse(String errorMsg, HttpStatus status) {
        return new ResponseEntity<>(GenericResponse.error(status, errorMsg), status);
    }
}
