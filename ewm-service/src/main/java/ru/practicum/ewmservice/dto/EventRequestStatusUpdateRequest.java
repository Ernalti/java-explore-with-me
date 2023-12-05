package ru.practicum.ewmservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.practicum.ewmservice.model.enums.RequestStatus;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class EventRequestStatusUpdateRequest {

	private List<Long> requestIds;

	private RequestStatus status;

}
