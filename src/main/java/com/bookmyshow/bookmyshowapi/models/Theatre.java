package com.bookmyshow.bookmyshowapi.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "theatres")
public class Theatre extends Auditable{
	@ManyToOne
	private City city;
	
	@ManyToOne
	private Location location;
	
	@Column(nullable = false)
	private String locationName;
	
	@Column(name = "theatreName", nullable = false)
	private String name;

	@Column(nullable = false)
	private Double basePrice;
	
	
}
