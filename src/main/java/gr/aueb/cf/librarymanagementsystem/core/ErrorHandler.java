package gr.aueb.cf.librarymanagementsystem.core;

import gr.aueb.cf.librarymanagementsystem.core.exceptions.NotAuthorizedException;
import gr.aueb.cf.librarymanagementsystem.core.exceptions.ResourceAlreadyExistsException;
import gr.aueb.cf.librarymanagementsystem.core.exceptions.ResourceNotFoundException;
import gr.aueb.cf.librarymanagementsystem.dto.ResponseMessageDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {

        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ResponseMessageDto response = new ResponseMessageDto("VALIDATION_ERROR", message);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<ResponseMessageDto> handleResourceNotFoundException(ResourceNotFoundException e) {
        return new ResponseEntity<>(new ResponseMessageDto(e.getCode(), e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ResourceAlreadyExistsException.class})
    public ResponseEntity<ResponseMessageDto> handleResourceAlreadyExistsException(ResourceAlreadyExistsException e) {
        return new ResponseEntity<>(new ResponseMessageDto(e.getCode(), e.getMessage()), HttpStatus.CONFLICT);
    }


    @ExceptionHandler({NotAuthorizedException.class})
    public ResponseEntity<ResponseMessageDto> handleNotAuthorizedException(NotAuthorizedException e) {
        return new ResponseEntity<>(new ResponseMessageDto(e.getCode(), e.getMessage()), HttpStatus.UNAUTHORIZED);
    }
}
