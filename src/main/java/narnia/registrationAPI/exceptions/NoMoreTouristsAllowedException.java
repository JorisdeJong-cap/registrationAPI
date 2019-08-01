package narnia.registrationAPI.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class NoMoreTouristsAllowedException extends RuntimeException {
    public NoMoreTouristsAllowedException() {
        super();
    }
    public NoMoreTouristsAllowedException(String msg) {
        super(msg);
    }
}
