package ru.practicum.ewmservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewmservice.model.enums.StateActionAdmin;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateEventAdminRequest extends UpdateEventRequest {

	private StateActionAdmin stateAction;

}
