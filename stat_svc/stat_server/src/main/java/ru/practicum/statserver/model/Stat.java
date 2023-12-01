package ru.practicum.statserver.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "stats")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Stat {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false)
	private String app;

	@Column(nullable = false)
	private String uri;

	@Column(nullable = false)
	private String ip;

	@Column(nullable = false)
	private LocalDateTime created;
}
