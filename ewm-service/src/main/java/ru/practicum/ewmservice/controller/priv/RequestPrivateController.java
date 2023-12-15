package ru.practicum.ewmservice.controller.priv;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewmservice.dto.ParticipationRequestDto;
import ru.practicum.ewmservice.service.RequestService;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/users/{userId}/requests")
@Validated
public class RequestPrivateController {

	private final RequestService requestService;

	@Autowired
	public RequestPrivateController(RequestService requestService) {
		this.requestService = requestService;
	}

	@GetMapping
	public List<ParticipationRequestDto> findRequestsForUser(@PathVariable @Min(1) long userId) {
		log.info("Find requests for user");
		return requestService.findRequestsForUser(userId);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ParticipationRequestDto createRequest(@PathVariable @Min(1) long userId,
	                          @Positive @RequestParam @Min(1) long eventId) {
		log.info("Create request for user");
		return requestService.createRequest(userId, eventId);
	}

	@PatchMapping("/{requestId}/cancel")
	public ParticipationRequestDto cancelRequest(@PathVariable @Min(1) long userId,
	                                             @PathVariable @Min(1) long requestId) {
		log.info("Cancel request");
		return requestService.cancelRequest(userId, requestId);
	}

}
