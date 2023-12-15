package ru.practicum.ewmservice.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewmservice.dto.NewUserRequest;
import ru.practicum.ewmservice.dto.UserDto;
import ru.practicum.ewmservice.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/admin/users")
@Validated
public class UserAdminController {

	private final UserService userService;

	@Autowired
	public UserAdminController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public List<UserDto> getUsers(@RequestParam(required = false) List<Long> ids,
	                              @RequestParam(defaultValue = "0") @PositiveOrZero int from,
	                              @RequestParam(defaultValue = "10") @Positive int size) {
		log.info("get users");
		return userService.getUsers(ids, from, size);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UserDto createUser(@RequestBody @Valid NewUserRequest newUserRequest) {
		log.info("Create User");
		return userService.createUser(newUserRequest);
	}

	@DeleteMapping("/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable @Min(1) long userId) {
		log.info("Delete User");
		userService.deleteUser(userId);
	}

}
