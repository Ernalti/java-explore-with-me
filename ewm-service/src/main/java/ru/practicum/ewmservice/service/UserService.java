package ru.practicum.ewmservice.service;

import ru.practicum.ewmservice.dto.NewUserRequest;
import ru.practicum.ewmservice.dto.UserDto;

import java.util.List;

public interface UserService {
	List<UserDto> getUsers(List<Long> ids, int from, int size);

	UserDto createUser(NewUserRequest newUserRequest);

	void deleteUser(long userId);

}
