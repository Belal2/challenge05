package banquemisr.challenge05.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BanqueMisrException extends RuntimeException {
    private final HttpStatus statusCode;

    public BanqueMisrException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

}
