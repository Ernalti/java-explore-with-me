package ru.practicum.statserver.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
public class ErrorHandler {

	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse statusError(final IllegalTimeException e) {
		log.debug("Get status 400 {}", e.getMessage(), e);
		return new ErrorResponse(e.getMessage());
	}

}
