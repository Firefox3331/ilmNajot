package uz.ilmnajot.school.exception;

import org.springframework.http.HttpStatus;

public class UserException extends BaseException {
    public UserException(String message) {
        super(message);
    }

    public UserException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

    public UserException(HttpStatus httpStatus) {
        super(httpStatus);
    }
}
