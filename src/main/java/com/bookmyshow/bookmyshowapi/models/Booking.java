package com.bookmyshow.bookmyshowapi.models;

import javax.persistence.Entity;
import javax.persistence.ExcludeDefaultListeners;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "bookings")
public class Booking extends Auditable{
	@ManyToOne
	private User user;
	
	@OneToOne
	private Ticket ticket;
}
