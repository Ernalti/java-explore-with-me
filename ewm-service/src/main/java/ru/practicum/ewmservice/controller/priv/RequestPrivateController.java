package ru.practicum.ewmservice.controller.priv;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.dto.ParticipationRequestDto;
import ru.practicum.ewmservice.service.RequestService;

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
	public List<ParticipationRequestDto> findRequestsForUser(@PathVariable Long userId) {
		log.info("Find requests for user");
		return requestService.findRequestsForUser(userId);
	}

	@PostMapping
	public ParticipationRequestDto createRequest(@PathVariable Long userId,
	                          @Positive @RequestParam(required = true) Long eventId) {
		log.info("Create request for user");
		return requestService.createRequest(userId, eventId);
	}

	@PatchMapping("/{requestId}/cancel")
	public ParticipationRequestDto cancelRequest(@PathVariable Long userId,
	                                             @PathVariable Long requestId) {
		log.info("Cancel request");
		return requestService.cancelRequest(userId, requestId);
	}

}
