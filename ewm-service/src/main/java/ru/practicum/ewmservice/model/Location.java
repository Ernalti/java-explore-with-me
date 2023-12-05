package ru.practicum.ewmservice.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "location")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Float lat;
	private Float lon;

}
