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
@Entity(name = "seats")
public class Seat extends Auditable{
	@ManyToOne
	private Tier tier;
	
	@Column(name = "SeatName", nullable = false)
	private String name;

	@Column(name = "isBooked", nullable = false)
	private boolean isBooked;
	
	@Column(nullable = false)
	private double seatPrice;
}
