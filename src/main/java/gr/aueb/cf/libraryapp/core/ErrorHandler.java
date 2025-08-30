package gr.aueb.cf.libraryapp.core;

import gr.aueb.cf.libraryapp.core.exceptions.NotAuthorizedException;
import gr.aueb.cf.libraryapp.core.exceptions.ResourceAlreadyExistsException;
import gr.aueb.cf.libraryapp.core.exceptions.ResourceNotFoundException;
import gr.aueb.cf.libraryapp.dto.ResponseMessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorHandler.class);

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {

        LOGGER.error(ex.getMessage());

        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ResponseMessageDto response = new ResponseMessageDto("VALIDATION_ERROR", message);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<ResponseMessageDto> handleResourceNotFoundException(ResourceNotFoundException e) {
        LOGGER.error(e.getMessage());
        return new ResponseEntity<>(new ResponseMessageDto(e.getCode(), e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ResourceAlreadyExistsException.class})
    public ResponseEntity<ResponseMessageDto> handleResourceAlreadyExistsException(ResourceAlreadyExistsException e) {
        LOGGER.error(e.getMessage());
        return new ResponseEntity<>(new ResponseMessageDto(e.getCode(), e.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({NotAuthorizedException.class})
    public ResponseEntity<ResponseMessageDto> handleNotAuthorizedException(NotAuthorizedException e) {
        LOGGER.error(e.getMessage());
        return new ResponseEntity<>(new ResponseMessageDto(e.getCode(), e.getMessage()), HttpStatus.UNAUTHORIZED);
    }
}
