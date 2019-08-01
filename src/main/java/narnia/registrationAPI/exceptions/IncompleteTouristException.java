package narnia.registrationAPI.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class IncompleteTouristException extends RuntimeException {
    public IncompleteTouristException() {
        super();
    }
    public IncompleteTouristException(String message) {
        super(message);
    }
}
