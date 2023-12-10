package ru.practicum.ewmservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.ewmservice.exception.exceptions.ConditionException;
import ru.practicum.ewmservice.exception.exceptions.ConflictException;
import ru.practicum.ewmservice.exception.exceptions.RequestApplicationException;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;
import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

	@ExceptionHandler
	@ResponseStatus(HttpStatus.CONFLICT)
	public ErrorResponse conflictError(final ConflictException e) {
		log.debug("Get status 409  {}", e.getMessage(), e);
		return new ErrorResponse(e.getMessage());
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.CONFLICT)
	public ErrorResponse conditionError(final ConditionException e) {
		log.debug("Get status 409 {}", e.getMessage(), e);
		return new ErrorResponse(e.getMessage());
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse validationError(final ValidationException e) {
		log.debug("Get status 400 {}", e.getMessage(), e);
		return new ErrorResponse(e.getMessage());
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorResponse entityNotFoundError(final EntityNotFoundException e) {
		log.debug("Get status 404 {}", e.getMessage(), e);
		return new ErrorResponse(e.getMessage());
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.CONFLICT)
	public ErrorResponse constraintViolationException(final RequestApplicationException e) {
		log.debug("Get status 409 {}", e.getMessage(), e);
		return new ErrorResponse(e.getMessage());
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorResponse noSuchElementError(final NoSuchElementException e) {
		log.debug("Get status 404 {}", e.getMessage(), e);
		return new ErrorResponse(e.getMessage());
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.CONFLICT)
	public ErrorResponse dataIntegrityViolationError(final DataIntegrityViolationException e) {
		log.debug("Get status 409 {}", e.getMessage(), e);
		return new ErrorResponse(e.getMessage());
	}

}
