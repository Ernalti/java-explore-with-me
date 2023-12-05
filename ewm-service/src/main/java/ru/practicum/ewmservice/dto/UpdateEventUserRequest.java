package ru.practicum.ewmservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.practicum.ewmservice.model.enums.StateActionUser;

@Data
@AllArgsConstructor
@Builder
public class UpdateEventUserRequest extends UpdateEventRequest {

	private StateActionUser stateAction;

}
