package ru.practicum.ewmservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.practicum.ewmservice.model.enums.StateActionAdmin;

@Data
@AllArgsConstructor
@Builder
public class UpdateEventAdminRequest extends UpdateEventRequest {

	private StateActionAdmin stateAction;

}
