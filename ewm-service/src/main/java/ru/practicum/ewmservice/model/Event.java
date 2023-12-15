package ru.practicum.ewmservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.ewmservice.model.enums.EventState;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "events")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Event {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String annotation;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private Category category;

	@Column(name = "created_on")
	private LocalDateTime createdOn;

	private String description;

	@Column(nullable = false)
	private LocalDateTime eventDate;

	@OneToMany(mappedBy = "event", fetch = FetchType.LAZY)
	private List<Request> requests;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "initiator_id")
	private User initiator;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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
}
