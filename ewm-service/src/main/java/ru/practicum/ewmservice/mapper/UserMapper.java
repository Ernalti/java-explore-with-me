package ru.practicum.ewmservice.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.ewmservice.dto.NewUserRequest;
import ru.practicum.ewmservice.dto.UserDto;
import ru.practicum.ewmservice.dto.UserShortDto;
import ru.practicum.ewmservice.model.User;

@UtilityClass
public class UserMapper {
	public UserDto userToUserDto(User user) {
		return UserDto.builder()
				.id(user.getId())
				.name(user.getName())
				.email(user.getEmail())
				.build();
	}

	public User userDtoToUser(NewUserRequest newUserRequest) {
		return User.builder()
				.name(newUserRequest.getName())
				.email(newUserRequest.getEmail())
				.build();
	}

	public UserShortDto userToUserShortDto(User user) {
		return UserShortDto.builder()
				.id(user.getId())
				.name(user.getName())
				.build();
	}
}
