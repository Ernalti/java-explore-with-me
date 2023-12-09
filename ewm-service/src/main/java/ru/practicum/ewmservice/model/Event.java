package ru.practicum.ewmservice.model;

import lombok.*;
import ru.practicum.ewmservice.model.enums.EventState;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "events")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String annotation;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@Column(name = "created_on")
	private LocalDateTime createdOn;

	private String description;

	@Column(nullable = false)
	private LocalDateTime eventDate;

	@OneToMany(mappedBy = "event")
	private List<Request> requests;

	@ManyToOne
	@JoinColumn(name = "initiator_id")
	private User initiator;

	@ManyToOne
	@JoinColumn(name = "location_id")
	private Location location;

	private boolean paid;

	@Column(name = "participant_limit")
	private int participantLimit;

	@Column(name = "published_date")
	private LocalDateTime publishedDate;

	@Column(name = "request_moderation")
	private boolean requestModeration;

	@Enumerated(EnumType.STRING)
	private EventState state;

	@Column(nullable = false)
	private String title;

//	private Integer views;
}
