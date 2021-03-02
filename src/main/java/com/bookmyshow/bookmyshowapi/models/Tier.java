package com.bookmyshow.bookmyshowapi.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tiers")
public class Tier extends Auditable{
	
	@ManyToOne
	private Screen screen;
	@Column(name = "Tiername", nullable = false, unique = true)

	private String name;
	
	@Column(nullable = false)
	private Double multiplier;
}
