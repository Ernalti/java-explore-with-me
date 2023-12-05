package ru.practicum.ewmservice.model;

import lombok.*;
import ru.practicum.ewmservice.model.enums.RequestStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "requests")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Request {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private LocalDateTime created;

	@ManyToOne
	@JoinColumn(name = "event_id")
	private Event event;

	@ManyToOne
	@JoinColumn(name = "requester_id")
	private User requester;

	@Enumerated(EnumType.STRING)
	@Column( nullable = false)
	private RequestStatus status;
}
