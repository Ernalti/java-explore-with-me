package ru.practicum.statserver.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ErrorResponse {

	private final String error;

	public ErrorResponse(String error) {
		this.error = error;
	}

	public String getError() {
		return error;
	}

}
