package ru.practicum.ewmservice.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "compilations")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Compilation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	private Boolean pinned;

	@ManyToMany
	@JoinTable(
			name = "events_compilation",
			joinColumns = @JoinColumn(name = "compilation_id"),
			inverseJoinColumns = @JoinColumn(name = "event_id"))
	private Set<Event> events;

}
