package pe.edu.upc.equipovirtual1.exceptions;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidation(MethodArgumentNotValidException e2_exception) {
        Map<String, String> e2_errors = new LinkedHashMap<>();
        e2_exception.getBindingResult().getFieldErrors()
                .forEach(e2_error -> e2_errors.put(e2_error.getField(), e2_error.getDefaultMessage()));
        return e2_errors;
    }
}