package com.bookmyshow.bookmyshowapi.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.aspectj.internal.lang.annotation.ajcPrivileged;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "prebookings")
public class PreBooking extends Auditable{
	@ManyToOne
	private User user;
	
	@ManyToOne
	private Show show;
	
	@OneToOne
	private Seat seat;
}
