package uz.ilmnajot.school.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseException extends RuntimeException {

    private HttpStatus httpStatus;

    public BaseException(String message){
        super(message);
    }
    public BaseException(String message, HttpStatus httpStatus){
        super(message);
        this.httpStatus = httpStatus;
    }
    public BaseException(HttpStatus httpStatus){
        this.httpStatus=httpStatus;
    }

}
