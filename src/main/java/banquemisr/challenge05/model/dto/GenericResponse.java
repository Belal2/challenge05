package banquemisr.challenge05.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class GenericResponse<T> {
    private boolean isOk;
    private int status;
    private T data;
    private String errorMsg;

    public static <T> GenericResponse<T> ok(T payload) {
        return status(HttpStatus.OK, payload, null);
    }

    private static <T> GenericResponse<T> status(HttpStatus status, T payload, String errorMsg) {
        return new GenericResponse<>(errorMsg == null, status.value(), payload, errorMsg);
    }

    public static <T> GenericResponse<T> error(HttpStatus status, String errorMsg) {
        return status(status, null, errorMsg);
    }
}
