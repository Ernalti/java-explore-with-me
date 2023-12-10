package ru.practicum.ewmservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmservice.dto.NewUserRequest;
import ru.practicum.ewmservice.dto.UserDto;
import ru.practicum.ewmservice.mapper.UserMapper;
import ru.practicum.ewmservice.model.User;
import ru.practicum.ewmservice.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public List<UserDto> getUsers(List<Long> ids, Integer from, Integer size) {
		Sort sort = Sort.by("id");
		Pageable page = PageRequest.of(from / size, size, sort);
		List<User> users;
		if (ids == null || ids.isEmpty()) {
			users = userRepository.findAll(page).toList();
		} else {
			users = userRepository.findByIdIn(ids, page).toList();
		}

		List<UserDto> usersDto = users.stream().map(UserMapper::userToUserDto).collect(Collectors.toList());
		return usersDto;
	}

	@Override
	@Transactional
	public UserDto createUser(NewUserRequest newUserRequest) {
		User user = UserMapper.userDtoToUser(newUserRequest);
		return UserMapper.userToUserDto(userRepository.save(user));
	}

	@Override
	@Transactional
	public void deleteUser(Long userId) {
		userRepository.deleteById(userId);
	}
}
