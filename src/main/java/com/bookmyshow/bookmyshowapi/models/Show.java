package com.bookmyshow.bookmyshowapi.models;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

import com.bookmyshow.bookmyshowapi.models.Auditable;
import com.bookmyshow.bookmyshowapi.models.Movie;
import com.bookmyshow.bookmyshowapi.models.Screen;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "shows")
public class Show extends Auditable{
	@ManyToOne
	private Movie movie;
	
	@ManyToOne
	private Screen screen;
	
	@DateTimeFormat(pattern = "yyyy-mm-dd, hh-mm-ss")
	private Date showTime;
	
	@Column(nullable = false)
	private boolean isAvailableForBooking;
}
