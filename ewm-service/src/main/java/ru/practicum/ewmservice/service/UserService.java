package ru.practicum.ewmservice.service;

import ru.practicum.ewmservice.dto.NewUserRequest;
import ru.practicum.ewmservice.dto.UserDto;

import javax.validation.Valid;
import java.util.List;

public interface UserService {
	List<UserDto> getUsers(List<Long> ids, Integer from, Integer size);

	UserDto createUser(NewUserRequest newUserRequest);

	void deleteUser(Long userId);
}
