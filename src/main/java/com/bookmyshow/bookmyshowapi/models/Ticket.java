package com.bookmyshow.bookmyshowapi.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.ArrayList;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tickets")
public class Ticket extends Auditable{
	@ManyToOne
	private Show show;
	
	@Column(nullable = false)
	private double amount;
	
	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date bookedAt;
	
	@ElementCollection
	@Column(name = "seats")
	private List<String> seats = new ArrayList<>();
}
