package filmorate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ValidationOfUserFound extends RuntimeException {
    private final String parameter;

    public ValidationOfUserFound (String parameter) {
        this.parameter = parameter;
    }

    public String getParameter() {
        return parameter;
    }
}
