package ru.practicum.ewmservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@Builder
public class NewUserRequest {

	@NotBlank
	@Email
	@Size(min = 6, max = 254)
	private String email;
	@NotBlank
	@Size(min = 2, max = 250)
	private String name;

}
