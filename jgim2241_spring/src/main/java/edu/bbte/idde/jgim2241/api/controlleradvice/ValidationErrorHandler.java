package edu.bbte.idde.jgim2241.api.controlleradvice;

import edu.bbte.idde.jgim2241.api.dto.outgoing.ErrorResponse;
import edu.bbte.idde.jgim2241.api.exception.BadRequestException;
import edu.bbte.idde.jgim2241.api.exception.EntityNotFoundException;
import edu.bbte.idde.jgim2241.service.ServiceException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class ValidationErrorHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public final ErrorResponse handleConstraintViolation(ConstraintViolationException e) {
        log.debug("ConstraintViolationException occurred", e);
        List<String> errors = e.getConstraintViolations().stream()
                .map(it -> it.getPropertyPath() + " not valid ")
                .collect(Collectors.toList());
        return new ErrorResponse(String.join(", ", errors), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public final ErrorResponse handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        log.debug("MethodArgumentNotValidException occurred", e);
        List<String> errors = e.getBindingResult().getFieldErrors().stream()
                .map(it -> it.getField() + " is not valid").collect(Collectors.toList());
        return new ErrorResponse(String.join(", ", errors), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public final ErrorResponse handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e) {
        log.debug("MethodArgumentTypeMismatchException occurred", e);
        String error = "Invalid value for parameter '" + e.getName() + "': " + e.getValue();
        return new ErrorResponse(error, HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public final ErrorResponse handleEntityNotFound(EntityNotFoundException e) {
        log.debug("EntityNotFoundException occurred", e);
        return new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public final ErrorResponse handleBadRequest(BadRequestException e) {
        log.debug("BadRequestException occurred", e);
        return new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public final ErrorResponse handleServiceException(ServiceException e) {
        log.debug("ServiceException occurred", e);
        return new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
