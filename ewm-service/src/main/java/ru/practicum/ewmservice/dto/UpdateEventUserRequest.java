package ru.practicum.ewmservice.dto;

import lombok.*;
import ru.practicum.ewmservice.model.enums.StateActionUser;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateEventUserRequest extends UpdateEventRequest {

	private StateActionUser stateAction;

}
